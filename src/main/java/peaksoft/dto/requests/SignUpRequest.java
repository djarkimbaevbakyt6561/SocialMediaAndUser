package peaksoft.dto.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import peaksoft.validations.validations.DateOfBirthValidation;
import peaksoft.validations.validations.PasswordValidation;
import peaksoft.validations.validations.PhoneNumberValidation;

import java.time.LocalDate;

public record SignUpRequest(
        @NotBlank(message = "First name is blank")
        String firstName,
        @NotBlank(message = "Last name is blank")
        String lastName,
        @Email
        String email,
        @PasswordValidation
        String password,
        @PhoneNumberValidation
        String phoneNumber,
        @DateOfBirthValidation
        String dateOfBirth,
        @NotBlank(message = "Gender is blank") @NotNull(message = "Gender must not be null")
        String gender

) {

}
