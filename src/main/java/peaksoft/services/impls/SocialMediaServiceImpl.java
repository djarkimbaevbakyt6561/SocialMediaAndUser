package peaksoft.services.impls;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import peaksoft.dto.requests.SocialMediaRequest;
import peaksoft.dto.responses.SimpleResponse;
import peaksoft.dto.responses.SocialMediaResponse;
import peaksoft.entities.SocialMedia;
import peaksoft.entities.User;
import peaksoft.exceptions.NotFoundException;
import peaksoft.repositories.SocialMediaRepository;
import peaksoft.repositories.UserRepository;
import peaksoft.services.SocialMediaService;

@Service
@RequiredArgsConstructor
public class SocialMediaServiceImpl implements SocialMediaService {
    private final SocialMediaRepository socialMediaRepository;
    private final UserRepository userRepository;

    @Override
    public SocialMediaResponse findById(Long socialMediaId) {
        SocialMedia socialMedia = socialMediaRepository.findById(socialMediaId).orElseThrow(() -> new NotFoundException("Social media with id " + socialMediaId + " not found!"));
        return SocialMediaResponse
                .builder()
                .name(socialMedia.getName())
                .logo(socialMedia.getLogo())
                .publishedDate(socialMedia.getPublishedDate())
                .build();
    }

    @Override
    public SocialMediaResponse findSocialMediaByUserId(Long userId) {
        SocialMedia socialMedia = socialMediaRepository.findSocialMediaByUserId(userId).orElseThrow(() -> new NotFoundException("User with id " + userId + " not found!"));
        return SocialMediaResponse
                .builder()
                .name(socialMedia.getName())
                .logo(socialMedia.getLogo())
                .publishedDate(socialMedia.getPublishedDate())
                .build();
    }

    @Override
    public SimpleResponse save(SocialMediaRequest socialMediaRequest) {
        SocialMedia buildedSocialMedia = socialMediaRequest.build();
        User user = userRepository.findById(socialMediaRequest.userId()).orElseThrow(() -> new NotFoundException("User with id: " + socialMediaRequest.userId() + " not found!"));
        buildedSocialMedia.setUser(user);
        socialMediaRepository.save(buildedSocialMedia);
        return new SimpleResponse(HttpStatus.OK, "Successfully saved social media to user with id: " + user.getId());
    }

    @Override
    @Transactional
    public SimpleResponse update(Long socialMediaId, SocialMediaRequest socialMediaRequest) {
        SocialMedia socialMedia = socialMediaRepository.findById(socialMediaId).orElseThrow(() -> new NotFoundException("Social media with id " + socialMediaId + " not found!"));
        socialMedia.setLogo(socialMediaRequest.logo());
        socialMedia.setName(socialMediaRequest.name());
        return SimpleResponse.builder().httpStatus(HttpStatus.OK).message("Successfully updated!").build();
    }

    @Override
    public SimpleResponse delete(Long socialMediaId) {
        socialMediaRepository.findById(socialMediaId).orElseThrow(() -> new NotFoundException("Social media with id " + socialMediaId + " not found!"));
        socialMediaRepository.deleteById(socialMediaId);
        return SimpleResponse.builder().httpStatus(HttpStatus.OK).message("Successfully deleted!").build();
    }
}
