package com.dms.controller;

import com.dms.model.DashboardStats;
import com.dms.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {
    
    @Autowired
    private DashboardService dashboardService;
    
    @GetMapping
    public ResponseEntity<DashboardStats> getDashboardStats(
            @AuthenticationPrincipal UserDetails userDetails) {
        
        // In a real application, you would get the user ID from the token or database
        // For now, we'll use username to fetch user details
        String username = userDetails.getUsername();
        
        // You would need to get the actual user ID from your user service
        // This is a simplified version
        DashboardStats stats = dashboardService.getAdminDashboardStats();
        
        if (stats == null) {
            return ResponseEntity.notFound().build();
        }
        
        return ResponseEntity.ok(stats);
    }
}
