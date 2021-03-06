package com.gvstave.mistergift.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

/**
 * Manage image filter and cropping.
 */
@Service
public class CroppingService {

    /** The logger. */
    private static Logger LOGGER = LoggerFactory.getLogger(CroppingService.class);

    /** The picture file. */
    private File picture;

    /** The buffered image. */
    private BufferedImage bufferedImage;

    /**
     * Crop picture.
     *
     * @param picture The picture file.
     * @param coords The cropping coordinates.
     * @return The {@link CroppingService}.
     */
    public CroppingService crop(File picture, Rectangle coords) throws IOException {
        Objects.requireNonNull(picture);
        Objects.requireNonNull(coords);

        this.picture = picture;

        try {
            bufferedImage = ImageIO.read(picture);
            bufferedImage = bufferedImage.getSubimage(
                (int) coords.getX(),
                (int) coords.getY(),
                (int) coords.getWidth(),
                (int) coords.getHeight()
            );

            return this;
        } catch (IOException exception) {
            LOGGER.error("Error while cropping, error={}", exception);
            throw exception;
        }
    }

    /**
     * Save current work on disk and returns the file.
     */
    public void save(String extension) throws IOException {

//        String filePath = String.format(
//                "%s/%s_%s",
//                env.getProperty("upload.picture.thumbnail.directory"),
//                picture.getName(),
//                env.getProperty("cropping.filename.suffix")
//        );

        try {
            ImageIO.write(bufferedImage, extension, picture);
        } catch (IOException exception) {
            LOGGER.error("Error while saving picture on disk, error={}", exception);
            throw exception;
        }
    }

}
