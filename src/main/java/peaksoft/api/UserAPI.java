package peaksoft.api;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.requests.UpdateRequest;
import peaksoft.dto.responses.*;
import peaksoft.services.UserService;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserAPI {
    private final UserService userService;

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @Operation(description = "Get by id")
    @GetMapping("/{userId}")
    public UserResponse findUserById(@PathVariable Long userId) {
        return userService.findById(userId);
    }
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @Operation(description = "Get all users")
    @GetMapping("/users")
    public UserPaginationResponse getAllUsers(@RequestParam int page, @RequestParam int size) {
        return userService.getAllUsers(page, size);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @Operation(description = "Update user")
    @PutMapping("/{userId}")
    public SimpleResponse updateUser(@PathVariable Long userId, @RequestBody @Valid UpdateRequest user, Principal principal) {
        return userService.update(principal, userId, user);
    }
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @Operation(description = "Delete user")
    @DeleteMapping("/{userId}")
    public SimpleResponse delete(@PathVariable Long userId) {
        return userService.delete(userId);
    }



}