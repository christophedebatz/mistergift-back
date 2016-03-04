package com.gvstave.mistergift.data.persistence;

import com.gvstave.mistergift.data.domain.FileMetadata;
import com.gvstave.mistergift.data.persistence.querydsl.BaseQueryDslRepositorySupport;
import com.gvstave.mistergift.data.persistence.repository.FileMetadataRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Repository
public class FileMetadataPersistenceService extends BaseQueryDslRepositorySupport<FileMetadata> implements FileMetadataRepository {

    /**
     * The default constructor for QueryDsl support.
     */
    public FileMetadataPersistenceService() {
        super(FileMetadata.class);
    }

    /**
     * Saves a file.
     *
     * @param file The user to persist.
     * @return The hydrated file.
     */
    @Transactional
    @Override
    public <S extends FileMetadata> S save(S file) {
        Objects.requireNonNull(file);
        S newFile = getEntityManager().merge(file);
        getEntityManager().flush();
        return newFile;
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
