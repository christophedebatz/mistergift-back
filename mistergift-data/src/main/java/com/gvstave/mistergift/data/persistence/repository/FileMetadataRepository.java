package com.gvstave.mistergift.data.persistence.repository;

import com.gvstave.mistergift.data.domain.FileMetadata;
import org.springframework.stereotype.Repository;

/**
 * Repository for {@link FileMetadata}.
 */
@Repository
public interface FileMetadataRepository extends EntityRepository<FileMetadata, Long> {
}
