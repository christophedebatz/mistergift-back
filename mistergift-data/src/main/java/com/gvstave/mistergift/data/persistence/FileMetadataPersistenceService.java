package com.gvstave.mistergift.data.persistence;

import com.gvstave.mistergift.data.domain.FileMetadata;
import com.gvstave.mistergift.data.persistence.repository.FileMetadataRepository;
import org.springframework.stereotype.Repository;

@Repository
public class FileMetadataPersistenceService implements FileMetadataRepository {

    @Override
    public <S extends FileMetadata> S save(S s) {
        return null;
    }

    @Override
    public <S extends FileMetadata> Iterable<S> save(Iterable<S> iterable) {
        return null;
    }

    @Override
    public FileMetadata findOne(Long aLong) {
        return null;
    }

    @Override
    public boolean exists(Long aLong) {
        return false;
    }

    @Override
    public Iterable<FileMetadata> findAll() {
        return null;
    }

    @Override
    public Iterable<FileMetadata> findAll(Iterable<Long> iterable) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void delete(Long aLong) {

    }

    @Override
    public void delete(FileMetadata fileMetadata) {

    }

    @Override
    public void delete(Iterable<? extends FileMetadata> iterable) {

    }

    @Override
    public void deleteAll() {

    }

}
