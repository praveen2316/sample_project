package com.dms.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "documents")
public class Document {
    
    @Id
    private String id;
    
    @Indexed
    private String userId;
    
    @Indexed
    private String username;
    
    private String fileName;
    
    private String originalFileName;
    
    private String fileExtension;
    
    private Long fileSize;
    
    private String mimeType;
    
    private String filePath;
    
    private String collectionName;
    
    private String description;
    
    private LocalDateTime uploadedAt;
    
    private LocalDateTime updatedAt;
    
    private boolean active = true;
    
    private Long version = 1L;
}
