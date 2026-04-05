package com.finance.dashboard.service;

import com.finance.dashboard.DTO.dashboard.AdminDashboardDTO;
import com.finance.dashboard.DTO.dashboard.AnalystDashboardDTO;
import com.finance.dashboard.DTO.dashboard.ViewerDashboardDTO;
import com.finance.dashboard.entity.user.Users;

public interface DashboardHelper {

    ViewerDashboardDTO loadViewerDashboard(Users user);
    AnalystDashboardDTO loadAnalystDashboard(Users user);
    AdminDashboardDTO loadAdminDashboard(Users user);
}
