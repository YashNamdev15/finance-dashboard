package com.finance.dashboard.DTO.userLoginRegister;

import com.finance.dashboard.entity.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponseDTO {

    private Long id;
    private String userName;
    private String email;

    @Enumerated(EnumType.STRING)
    private Role role;
    private Boolean active;
}