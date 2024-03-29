package peaksoft.dto.responses;

import lombok.Builder;
import peaksoft.enums.Role;

@Builder
public record SignResponse(
        Long id,
        Role role,
        String email,
        String token) {
}
