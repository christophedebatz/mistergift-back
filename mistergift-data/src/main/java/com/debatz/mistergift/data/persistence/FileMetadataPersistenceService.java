package com.debatz.mistergift.data.persistence;

import com.debatz.mistergift.data.persistence.repository.FileMetadataRepository;
import com.debatz.mistergift.data.domain.FileMetadata;
import com.mysema.query.types.OrderSpecifier;
import com.mysema.query.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class FileMetadataPersistenceService implements FileMetadataRepository {

    public Iterable<FileMetadata> findAll(Sort sort) {
        return null;
    }

    public Page<FileMetadata> findAll(Pageable pageable) {
        return null;
    }

    public <S extends FileMetadata> S save(S s) {
        return null;
    }

    public <S extends FileMetadata> Iterable<S> save(Iterable<S> iterable) {
        return null;
    }

    public FileMetadata findOne(Long aLong) {
        return null;
    }

    public boolean exists(Long aLong) {
        return false;
    }

    public Iterable<FileMetadata> findAll() {
        return null;
    }

    public Iterable<FileMetadata> findAll(Iterable<Long> iterable) {
        return null;
    }

    public long count() {
        return 0;
    }

    public void delete(Long aLong) {

    }

    public void delete(FileMetadata fileMetadata) {

    }

    public void delete(Iterable<? extends FileMetadata> iterable) {

    }

    public void deleteAll() {

    }

    public FileMetadata findOne(Predicate predicate) {
        return null;
    }

    public Iterable<FileMetadata> findAll(Predicate predicate) {
        return null;
    }

    public Iterable<FileMetadata> findAll(Predicate predicate, Sort sort) {
        return null;
    }

    public Iterable<FileMetadata> findAll(Predicate predicate, OrderSpecifier<?>... orderSpecifiers) {
        return null;
    }

    public Iterable<FileMetadata> findAll(OrderSpecifier<?>... orderSpecifiers) {
        return null;
    }

    public Page<FileMetadata> findAll(Predicate predicate, Pageable pageable) {
        return null;
    }

    public long count(Predicate predicate) {
        return 0;
    }

    public boolean exists(Predicate predicate) {
        return false;
    }
}
