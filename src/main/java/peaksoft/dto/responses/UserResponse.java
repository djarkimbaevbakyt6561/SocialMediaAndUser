package peaksoft.dto.responses;

import lombok.Builder;
import peaksoft.enums.Gender;
import peaksoft.enums.Role;

import java.time.LocalDate;
import java.util.List;

@Builder
public record UserResponse(
        String firstName,
        String lastName,
        String email,
        String phoneNumber,
        LocalDate dateOfBirth,
        Role role,
        Gender gender,
        List<SocialMediaResponse> socialMedias
) {
}
