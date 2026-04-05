package com.finance.dashboard.serviceImplementation;

import com.finance.dashboard.DTO.dashboard.AdminDashboardDTO;
import com.finance.dashboard.DTO.dashboard.AnalystDashboardDTO;
import com.finance.dashboard.DTO.dashboard.ViewerDashboardDTO;
import com.finance.dashboard.entity.user.Role;
import com.finance.dashboard.entity.user.Users;
import com.finance.dashboard.exception.ResourceNotFound;
import com.finance.dashboard.repository.FinanceRecordRepo;
import com.finance.dashboard.repository.UserRepo;
import com.finance.dashboard.service.DashboardService;
import com.finance.dashboard.service.DashboardHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class DashboardServiceImpl implements DashboardService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private FinanceRecordRepo recordRepo;

    @Autowired
    private DashboardHelper dashboardHelper;

    @Override
    public Object getDashboard() {

        Users user = getLoggedInUser();

        if(user.getRole() == Role.VIEWER){
            return dashboardHelper.loadViewerDashboard(user);
        } else if (user.getRole() == Role.ANALYST) {
            return dashboardHelper.loadAnalystDashboard(user);
        } else {
            return dashboardHelper.loadAdminDashboard(user);
        }
    }

    public Users getLoggedInUser(){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        Users user = userRepo.findByEmail(email).orElseThrow(
                () -> new ResourceNotFound("User not found"));

        return user;
    }
}
