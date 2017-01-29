package com.gvstave.mistergift.data.persistence;

import com.gvstave.mistergift.data.domain.FileMetadata;
import org.springframework.stereotype.Service;

/**
 * Persistence service for {@link FileMetadata}.
 */
@Service
public class FileMetadataPersistenceService extends BaseEntityPersistenceService<FileMetadata, Long> {

    /**
     * Constructor.
     */
    public FileMetadataPersistenceService() {
        super(FileMetadata.class);
    }

}
