package com.finance.dashboard.DTO.userUpdate;

import com.finance.dashboard.entity.user.Role;
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
public class updateRoleDTO {

    @NotNull(message = "Role is required")
    private Role role;
}
