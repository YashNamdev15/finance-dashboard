package com.finance.dashboard.DTO.userLoginRegister;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDTO {

    @NotBlank(message = "Username cannot be blank")
    @NotNull(message = "Username is required")
    private String userName;

    @NotBlank(message = "Email cannot be blank")
    @NotNull(message = "Email is required")
    @Email(message = "Please enter a valid email address")
    private String email;

    @NotBlank(message = "Password cannot be blank")
    @NotNull(message = "Password is required")
    private String password;

}
