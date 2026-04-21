package com.dms.repository;

import com.dms.model.Document;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentRepository extends MongoRepository<Document, String> {
    
    List<Document> findByUserIdAndActiveTrue(String userId);
    
    List<Document> findByUserIdAndActiveTrueOrderByUploadedAtDesc(String userId, int limit);
    
    List<Document> findByActiveTrueOrderByUploadedAtDesc();
    
    long countByUserIdAndActiveTrue(String userId);
    
    long countByActiveTrue();
    
    @Aggregation(pipeline = {
        "{ $match: { userId: ?0, active: true } }",
        "{ $group: { _id: '$fileExtension', count: { $sum: 1 } } }"
    })
    List<FileExtensionCount> getFileExtensionCountsByUserId(String userId);
    
    @Aggregation(pipeline = {
        "{ $match: { active: true } }",
        "{ $group: { _id: '$fileExtension', count: { $sum: 1 } } }"
    })
    List<FileExtensionCount> getFileExtensionCountsAll();
    
    interface FileExtensionCount {
        String get_id();
        Long getCount();
    }
}
