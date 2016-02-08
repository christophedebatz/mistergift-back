package com.debatz.mistergift.data.persistence.repository;

import com.debatz.mistergift.data.domain.FileMetadata;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileMetadataRepository extends PagingAndSortingRepository<FileMetadata, Long>, QueryDslPredicateExecutor<FileMetadata> {
}
