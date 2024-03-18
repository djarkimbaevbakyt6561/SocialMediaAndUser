package peaksoft.services.impls;

import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import peaksoft.config.jwt.JwtService;
import peaksoft.dto.requests.SignInRequest;
import peaksoft.dto.requests.SignUpRequest;
import peaksoft.dto.requests.UpdateRequest;
import peaksoft.dto.responses.*;
import peaksoft.entities.SocialMedia;
import peaksoft.entities.User;
import peaksoft.enums.Gender;
import peaksoft.enums.Role;
import peaksoft.exceptions.AuthorizeException;
import peaksoft.exceptions.ForbiddenException;
import peaksoft.exceptions.NotFoundException;
import peaksoft.repositories.UserRepository;
import peaksoft.services.UserService;

import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @PostConstruct
    private void saveAdmin() {
        userRepository.save(
                User.builder()
                        .firstName("Admin")
                        .lastName("Adminov")
                        .phoneNumber("+996700000000")
                        .email("admin@gmail.com")
                        .role(Role.ADMIN)
                        .password(passwordEncoder.encode("admin123"))
                        .build()
        );

    }

    @PostConstruct
    private void saveClient() {
        userRepository.save(
                User.builder()
                        .firstName("Ars")
                        .lastName("Dzharkymbaev")
                        .gender(Gender.MALE)
                        .dateOfBirth(LocalDate.of(2000, 1, 1))
                        .phoneNumber("+996702300000")
                        .email("user@gmail.com")
                        .password(passwordEncoder.encode("user123"))
                        .role(Role.USER)
                        .build()
        );

    }

    @Override
    public UserResponse findById(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User with id " + userId + " not found!"));
        List<SocialMediaResponse> socialMediaResponses = new ArrayList<>();
        for (SocialMedia socialMedia : user.getSocialMedias()) {
            socialMediaResponses.add(SocialMediaResponse.builder()
                    .logo(socialMedia.getLogo())
                    .name(socialMedia.getName())
                    .publishedDate(socialMedia.getPublishedDate())
                    .build()
            );
        }
        return UserResponse
                .builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .dateOfBirth(user.getDateOfBirth())
                .phoneNumber(user.getPhoneNumber())
                .email(user.getEmail())
                .role(user.getRole())
                .gender(user.getGender())
                .socialMedias(socialMediaResponses)
                .build();
    }

    @Override
    public UserPaginationResponse getAllUsers(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<User> userPages = userRepository.findAll(pageable);
        List<UserResponse> userResponses = new ArrayList<>();
        for (User user : userPages.getContent()) {
            List<SocialMediaResponse> socialMediaResponses = new ArrayList<>();
            for (SocialMedia socialMedia : user.getSocialMedias()) {
                socialMediaResponses.add(SocialMediaResponse.builder()
                        .logo(socialMedia.getLogo())
                        .name(socialMedia.getName())
                        .publishedDate(socialMedia.getPublishedDate())
                        .build()
                );
            }
            userResponses.add(UserResponse.builder()
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .dateOfBirth(user.getDateOfBirth())
                    .phoneNumber(user.getPhoneNumber())
                    .email(user.getEmail())
                    .role(user.getRole())
                    .gender(user.getGender())
                    .socialMedias(socialMediaResponses)
                    .build()
            );
        }
        return UserPaginationResponse.builder()
                .page(userPages.getNumber() + 1)
                .size(userPages.getTotalPages())
                .users(userResponses)
                .build();

    }

    @Override
    public RegisterResponse signUp(SignUpRequest signUpRequest) {
        boolean exists = userRepository.existsByEmail(signUpRequest.email());
        if (exists) throw new AuthorizeException("Email : " + signUpRequest.email() + " already exist");

        User user = new User();
        user.setPhoneNumber(signUpRequest.phoneNumber());
        user.setFirstName(signUpRequest.phoneNumber());
        user.setLastName(signUpRequest.lastName());
        user.setEmail(signUpRequest.email());
        user.setGender(Gender.valueOf(signUpRequest.gender().toUpperCase()));
        user.setDateOfBirth(LocalDate.parse(signUpRequest.dateOfBirth()));
        user.setPassword(passwordEncoder.encode(signUpRequest.password()));
        user.setRole(Role.USER);
        userRepository.save(user);

        String newToken = jwtService.createToken(user);
        return RegisterResponse.builder()
                .token(newToken)
                .simpleResponse(
                        SimpleResponse.builder()
                                .httpStatus(HttpStatus.OK)
                                .message("Successfully saved!")
                                .build())
                .build();
    }

    @Override
    public SignResponse signIn(SignInRequest signInRequest) {
        User user = userRepository.findByEmail(signInRequest.email()).orElseThrow(() ->
                new NotFoundException("User with email: " + signInRequest.email() + " not found!"));

        String encodePassword = user.getPassword();
        String password = signInRequest.password();

        boolean matches = passwordEncoder.matches(password, encodePassword);

        if (!matches) throw new AuthorizeException("Invalid password");


        String token = jwtService.createToken(user);
        return SignResponse.builder()
                .token(token)
                .id(user.getId())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }

    @Override
    @Transactional
    public SimpleResponse update(Principal principal, Long userId, UpdateRequest user) {
        String email = principal.getName();
        User loginUser = userRepository.getByEmail(email);
        User realUser = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User with id " + userId + " not found!"));

        if (loginUser.getRole().equals(Role.ADMIN) || loginUser.getId().equals(userId)) {
            if (realUser != null) {
                realUser.setLastName(user.lastName());
                realUser.setFirstName(user.firstName());
                realUser.setPhoneNumber(user.phoneNumber());
                realUser.setGender(Gender.valueOf(user.gender().toUpperCase()));
            }
        } else {
            throw new ForbiddenException("User can't update if he doesn't owner or role not equals Admin");
        }
        return SimpleResponse.builder().message("Successfully updated!").httpStatus(HttpStatus.OK).build();
    }

    @Override
    public SimpleResponse delete(Long userId) {
        userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User with id " + userId + " not found!"));
        userRepository.deleteById(userId);
        return SimpleResponse.builder().httpStatus(HttpStatus.OK).message("Successfully deleted!").build();
    }
}
