package peaksoft.services;

import peaksoft.dto.responses.UserPaginationResponse;
import peaksoft.dto.requests.SignInRequest;
import peaksoft.dto.requests.SignUpRequest;
import peaksoft.dto.requests.UpdateRequest;
import peaksoft.dto.responses.RegisterResponse;
import peaksoft.dto.responses.SignResponse;
import peaksoft.dto.responses.SimpleResponse;
import peaksoft.dto.responses.UserResponse;

import java.security.Principal;

public interface UserService {
    UserResponse findById(Long userId);
    UserPaginationResponse getAllUsers(int page, int size);
    RegisterResponse signUp(SignUpRequest signUpRequest);

    SignResponse signIn(SignInRequest signInRequest);

    SimpleResponse update(Principal principal, Long userId, UpdateRequest user);
    SimpleResponse delete(Long userId);
}
