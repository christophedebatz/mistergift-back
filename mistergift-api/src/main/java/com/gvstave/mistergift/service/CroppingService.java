package com.gvstave.mistergift.service;

import org.apache.log4j.Logger;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import javax.inject.Inject;
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

    /** The environment. */
    @Inject
    private Environment environment;

    /** The picture file. */
    private File picture;

    /** The buffered image. */
    private BufferedImage bufferedImage;

    /** The logger. */
    private static Logger LOGGER = Logger.getLogger(CroppingService.class);

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
            LOGGER.error(exception);
            throw exception;
        }
    }

    /**
     * Save current work on disk and returns the file.
     */
    public void save(String extension) throws IOException {

//        String filePath = String.format(
//                "%s/%s_%s",
//                environment.getProperty("upload.picture.thumbnail.directory"),
//                picture.getName(),
//                environment.getProperty("cropping.filename.suffix")
//        );

        try {
            ImageIO.write(bufferedImage, extension, picture);
        } catch (IOException exception) {
            LOGGER.error(exception);
            throw exception;
        }
    }

}
