package com.gvstave.mistergift.data.repositories.other;

import com.gvstave.mistergift.data.domain.jpa.FileMetadata;
import org.springframework.stereotype.Service;

/**
 * Persistence service for {@link FileMetadata}.
 */
@Service
public class FileMetadataPersistenceService extends EntityPersistenceService<FileMetadata, Long> {

}
