package com.dms.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DashboardStats {
    
    private Long totalFiles;
    
    private Long totalApplications;
    
    private Long storageUsed;
    
    private Long storageLimit;
    
    private Double storagePercentage;
    
    private FileExtensionStats[] fileExtensionStats;
    
    private RecentDocument[] recentDocuments;
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FileExtensionStats {
        private String extension;
        private Long count;
        private Double percentage;
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RecentDocument {
        private String id;
        private String fileName;
        private String fileExtension;
        private Long fileSize;
        private String uploadedBy;
        private String uploadedAt;
    }
}
