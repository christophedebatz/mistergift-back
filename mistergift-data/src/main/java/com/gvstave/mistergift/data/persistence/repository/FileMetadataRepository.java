package com.gvstave.mistergift.data.persistence.repository;

import com.gvstave.mistergift.data.domain.FileMetadata;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileMetadataRepository extends CrudRepository<FileMetadata, Long> {
}
