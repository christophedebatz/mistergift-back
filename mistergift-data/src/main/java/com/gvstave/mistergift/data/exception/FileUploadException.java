package com.gvstave.mistergift.data.exception;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Exception when error in uploading.
 */
public class FileUploadException extends IOException {

    /**
     * Constructor/
     *
     * @param file The multipart file.
     */
    public FileUploadException(MultipartFile file, Throwable exception) {
        super(String.format("There has been an error when uploading file %s.", file.getOriginalFilename()), exception);
    }

}
