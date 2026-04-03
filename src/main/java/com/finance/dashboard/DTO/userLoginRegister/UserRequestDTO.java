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

    @NotBlank
    @NotNull
    private String userName;

    @Email()
    private String email;

    @NotBlank
    @NotNull
    private String password;

}
