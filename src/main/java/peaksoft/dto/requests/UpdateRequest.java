package peaksoft.dto.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import peaksoft.validations.validations.PhoneNumberValidation;

public record UpdateRequest(

        @NotBlank(message = "First name is blank")
        String firstName,
        @NotBlank(message = "Last name is blank")
        String lastName,
        @PhoneNumberValidation
        String phoneNumber,
        @NotBlank(message = "Gender is blank") @NotNull(message = "Gender must not be null")
        String gender
) {
}
