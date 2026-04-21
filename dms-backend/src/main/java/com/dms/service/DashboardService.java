package com.dms.service;

import com.dms.model.DashboardStats;
import com.dms.model.Document;
import com.dms.model.User;
import com.dms.repository.DocumentRepository;
import com.dms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DashboardService {
    
    @Autowired
    private DocumentRepository documentRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    public DashboardStats getUserDashboardStats(String userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return null;
        }
        
        long totalFiles = documentRepository.countByUserIdAndActiveTrue(userId);
        List<Document> recentDocs = documentRepository.findByUserIdAndActiveTrueOrderByUploadedAtDesc(userId, 5);
        List<DocumentRepository.FileExtensionCount> extensionCounts = 
            documentRepository.getFileExtensionCountsByUserId(userId);
        
        DashboardStats stats = new DashboardStats();
        stats.setTotalFiles(totalFiles);
        stats.setTotalApplications(totalFiles); // Each file is considered an application
        stats.setStorageUsed(user.getStorageUsed());
        stats.setStorageLimit(user.getStorageLimit());
        
        double percentage = (user.getStorageLimit() > 0) ? 
            ((double) user.getStorageUsed() / user.getStorageLimit()) * 100 : 0.0;
        stats.setStoragePercentage(percentage);
        
        // File extension statistics
        List<DashboardStats.FileExtensionStats> extStats = new ArrayList<>();
        for (DocumentRepository.FileExtensionCount ec : extensionCounts) {
            double extPercentage = (totalFiles > 0) ? 
                ((double) ec.getCount() / totalFiles) * 100 : 0.0;
            extStats.add(new DashboardStats.FileExtensionStats(
                ec.get_id(), ec.getCount(), extPercentage));
        }
        stats.setFileExtensionStats(extStats.toArray(new DashboardStats.FileExtensionStats[0]));
        
        // Recent documents
        List<DashboardStats.RecentDocument> recentDocuments = recentDocs.stream()
            .map(doc -> new DashboardStats.RecentDocument(
                doc.getId(),
                doc.getOriginalFileName(),
                doc.getFileExtension(),
                doc.getFileSize(),
                doc.getUsername(),
                doc.getUploadedAt().toString()
            ))
            .collect(Collectors.toList());
        stats.setRecentDocuments(recentDocuments.toArray(new DashboardStats.RecentDocument[0]));
        
        return stats;
    }
    
    public DashboardStats getAdminDashboardStats() {
        long totalFiles = documentRepository.countByActiveTrue();
        List<Document> recentDocs = documentRepository.findByActiveTrueOrderByUploadedAtDesc()
            .stream().limit(5).collect(Collectors.toList());
        List<DocumentRepository.FileExtensionCount> extensionCounts = 
            documentRepository.getFileExtensionCountsAll();
        
        // Calculate total storage used by all users
        List<User> allUsers = userRepository.findAll();
        long totalStorageUsed = allUsers.stream()
            .mapToLong(User::getStorageUsed)
            .sum();
        long totalStorageLimit = allUsers.stream()
            .mapToLong(User::getStorageLimit)
            .sum();
        
        DashboardStats stats = new DashboardStats();
        stats.setTotalFiles(totalFiles);
        stats.setTotalApplications((long) allUsers.size()); // Total users as applications
        stats.setStorageUsed(totalStorageUsed);
        stats.setStorageLimit(totalStorageLimit);
        
        double percentage = (totalStorageLimit > 0) ? 
            ((double) totalStorageUsed / totalStorageLimit) * 100 : 0.0;
        stats.setStoragePercentage(percentage);
        
        // File extension statistics
        List<DashboardStats.FileExtensionStats> extStats = new ArrayList<>();
        for (DocumentRepository.FileExtensionCount ec : extensionCounts) {
            double extPercentage = (totalFiles > 0) ? 
                ((double) ec.getCount() / totalFiles) * 100 : 0.0;
            extStats.add(new DashboardStats.FileExtensionStats(
                ec.get_id(), ec.getCount(), extPercentage));
        }
        stats.setFileExtensionStats(extStats.toArray(new DashboardStats.FileExtensionStats[0]));
        
        // Recent documents
        List<DashboardStats.RecentDocument> recentDocuments = recentDocs.stream()
            .map(doc -> new DashboardStats.RecentDocument(
                doc.getId(),
                doc.getOriginalFileName(),
                doc.getFileExtension(),
                doc.getFileSize(),
                doc.getUsername(),
                doc.getUploadedAt().toString()
            ))
            .collect(Collectors.toList());
        stats.setRecentDocuments(recentDocuments.toArray(new DashboardStats.RecentDocument[0]));
        
        return stats;
    }
}
