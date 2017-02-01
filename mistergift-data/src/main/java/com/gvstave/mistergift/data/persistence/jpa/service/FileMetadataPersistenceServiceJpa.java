package com.gvstave.mistergift.data.persistence.jpa.service;

import com.gvstave.mistergift.data.domain.jpa.FileMetadata;
import org.springframework.stereotype.Service;

/**
 * Persistence service for {@link FileMetadata}.
 */
@Service
public class FileMetadataPersistenceServiceJpa extends JpaEntityPersistenceService<FileMetadata, Long> {

}
