package com.gvstave.mistergift.data.persistence.jpa.repository;

import com.gvstave.mistergift.data.domain.jpa.FileMetadata;
import com.gvstave.mistergift.data.persistence.jpa.service.JpaEntityRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for {@link FileMetadata}.
 */
@Repository
public interface FileMetadataRepositoryJpa extends JpaEntityRepository<FileMetadata, Long> {
}
