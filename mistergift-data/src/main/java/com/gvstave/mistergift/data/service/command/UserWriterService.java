package com.gvstave.mistergift.data.service.command;

import com.gvstave.mistergift.data.domain.jpa.*;
import com.gvstave.mistergift.data.exception.DuplicatedEntityException;
import com.gvstave.mistergift.data.exception.FileUploadException;
import com.gvstave.mistergift.data.exception.InvalidFieldValueException;
import com.gvstave.mistergift.data.service.dto.UserDto;
import com.gvstave.mistergift.service.CroppingService;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.inject.Inject;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * .
 */
@Service
public class UserWriterService {

    /** The user repositories service. */
    @Inject
    private UserPersistenceService userPersistenceService;

    /** The token repositories service. */
    @Inject
    private TokenPersistenceService tokenPersistenceService;

    /** The file metadata repositories service. */
    @Inject
    private FileMetadataPersistenceService fileMetadataPersistenceService;

    /** The cropping service. */
    @Inject
    private CroppingService croppingService;

    /** The password encoder. */
    @Inject
    private PasswordEncoder passwordEncoder;

    /** The environment. */
    @Inject
    private Environment env;

    /**
     * Removes user token.
     *
     * @param user The user.
     */
    @Transactional
    public void removeToken(User user) {
        Objects.requireNonNull(user);
        Token token = user.getToken();
        user.setToken(null);
        userPersistenceService.save(user);
        tokenPersistenceService.delete(token.getId());
    }

    /**
     * Saves a new user in database.
     *
     * @param userDto The new user.
     * @return The created user id.
     */
    @Transactional
    public User createUser(UserDto userDto) {
        Optional<User> existentUser = Optional.ofNullable(
            userPersistenceService.findOne(QUser.user.email.eq(userDto.getEmail()))
        );

        if (existentUser.isPresent()) {
            throw new DuplicatedEntityException("user", "email");
        }

        return saveOrUpdate(userDto);
    }

    /**
     *
     * @param userDto
     * @return
     */
    @Transactional
    public User saveOrUpdate(UserDto userDto) {
        // encode password and set role
        User user = new User();
        user.setRole(User.Role.ROLE_USER);
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        return userPersistenceService.save(user);
    }

    /**
     *
     * @param user The user
     * @param file The file to upload.
     * @param coords The coords for cropping.
     * @throws InvalidFieldValueException
     */
    @Transactional
    public FileMetadata uploadPicture(User user,MultipartFile file, String coords) throws InvalidFieldValueException,
        FileUploadException {
        if (file == null || file.isEmpty()) {
            throw new InvalidFieldValueException("file");
        }

        Rectangle croppingCoords = parseCroppingCoords(coords);
        String extension = env.getProperty("upload.picture.extension");

        String targetFileName = String.format("%s/%s.%s",
            env.getProperty("upload.picture.directory"),
            UUID.randomUUID().toString(),
            extension
        );

        try {
            // convert to file
            File pictureFile = new File(targetFileName);
            file.transferTo(pictureFile);

            // crop and save
            croppingService.crop(pictureFile, croppingCoords).save(extension);

            // create file-metadata
            FileMetadata profilePicture = new FileMetadata();
            profilePicture.setOwner(user);
            profilePicture.setUrl(env.getProperty("upload.domain") + targetFileName);

            // save file meta data
            profilePicture = fileMetadataPersistenceService.save(profilePicture);

            // save user
            user.setPicture(profilePicture);
            userPersistenceService.save(user);

            return profilePicture;

        } catch (IOException exception) {
            throw new FileUploadException(file, exception);
        }
    }

    /**
     * Returns a Rectangle from String coordinates.
     *
     * @param stringCoords The coordinates in string format.
     * @return The rectangle.
     * @throws InvalidFieldValueException whether the coordinate string is invalid.
     */
    private Rectangle parseCroppingCoords(String stringCoords) throws InvalidFieldValueException {

        java.util.List<Integer> coords = Arrays.asList(stringCoords.split("\\s")).stream()
            .map(Integer::valueOf)
            .collect(Collectors.toList());

        if (coords.size() < 4) {
            throw new InvalidFieldValueException("coords");
        }

        return new Rectangle(coords.get(0), coords.get(1), coords.get(2), coords.get(3));
    }

}
