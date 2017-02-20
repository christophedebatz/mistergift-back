package com.gvstave.mistergift.data.repositories.other;

import com.gvstave.mistergift.data.domain.jpa.FileMetadata;
import org.springframework.stereotype.Repository;

/**
 * Repository for {@link FileMetadata}.
 */
@Repository
public interface FileMetadataRepository extends EntityRepository<FileMetadata, Long> {
}
