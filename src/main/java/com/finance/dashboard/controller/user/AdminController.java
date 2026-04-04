package com.finance.dashboard.controller.user;


import com.finance.dashboard.DTO.userLoginRegister.UserResponseDTO;
import com.finance.dashboard.DTO.userUpdate.updateRoleDTO;
import com.finance.dashboard.service.AdminService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;


    @GetMapping("all-users")
    @PreAuthorize("hasAnyRole('ADMIN','ANALYST')")
    public ResponseEntity<?> getAllUsers(){

        List<UserResponseDTO> userResponseDTOs = adminService.getAllUser();
        return new ResponseEntity<>(
                userResponseDTOs,
                HttpStatus.OK);
    }

    @PatchMapping("deactivate/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<?> deactivate(@PathVariable Long id){

        UserResponseDTO user = adminService.softDeleteUser(id);
        return new ResponseEntity<>(
                user,
                HttpStatus.OK);
    }

    @PatchMapping("activate/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<?> activate(@PathVariable Long id){

        UserResponseDTO user = adminService.activateUser(id);
        return new ResponseEntity<>(
                user,
                HttpStatus.OK);
    }

    @PutMapping("/role-change/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<?> UpdateRole(@PathVariable
                                               Long id,
                                        @Valid @RequestBody
                                               updateRoleDTO updateRoleDTO)
    {
        UserResponseDTO user = adminService.updateRole(id, updateRoleDTO);
        return new ResponseEntity<>(
                user,
                HttpStatus.OK);
    }
}
