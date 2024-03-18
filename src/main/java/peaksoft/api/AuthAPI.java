package peaksoft.api;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.requests.SignInRequest;
import peaksoft.dto.requests.SignUpRequest;
import peaksoft.dto.responses.RegisterResponse;
import peaksoft.dto.responses.SignResponse;
import peaksoft.services.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthAPI {
    private final UserService userService;

    @PostMapping("/signUp")
    @Operation(description = "This is registration")
    public RegisterResponse signUp(@RequestBody @Valid SignUpRequest signUpRequest) {
        return userService.signUp(signUpRequest);
    }

    @GetMapping("/signIn")
    @Operation(description = "This is login")
    public SignResponse signIn(@RequestBody SignInRequest signInRequest) {
        return userService.signIn(signInRequest);
    }

}

