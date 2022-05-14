package com.direct.app.service.implementation;

import com.direct.app.config.AppConfiguration;
import com.direct.app.exceptions.RuntimeBusinessException;
import com.direct.app.io.entities.UserDetailsEntity;
import com.direct.app.io.entities.UserEntity;
import com.direct.app.io.entities.UserImageEntity;
import com.direct.app.mappers.EntityToDtoMapper;
import com.direct.app.mappers.impl.UserImageEntityToDtoMapper;
import com.direct.app.repositery.UserImageRepository;
import com.direct.app.service.ProfileImageService;
import com.direct.app.service.UserService;
import com.direct.app.shared.dto.ProfileImageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.direct.app.exceptions.ErrorCode.*;
import static java.util.Optional.ofNullable;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Service
public class ProfileImageServiceImplementation implements ProfileImageService {

	@Autowired
	private UserImageRepository imageRepository;

	@Autowired
	private UserService userService;

	@Autowired
	private AppConfiguration appConfig;

	private Path basePath;
	private UserImageEntity userImageEntity;
	private ProfileImageDTO imageDTO;

	@PostConstruct
	public void setupFileLocation() throws RuntimeBusinessException {
		basePath = Paths.get(appConfig.getProfileImagesBasePath());

		if (!Files.exists(basePath)) {
			createDirectory();
		}
	}

	private void createDirectory() {
		try {
			Files.createDirectories(basePath);
		} catch (Exception exception) {
			throw new RuntimeBusinessException(INTERNAL_SERVER_ERROR, IMG$0001, basePath);
		}
	}

	@Override
	public String setProfileImage(MultipartFile imageFile) throws Exception {
		getImageEntity();
		deletePreviousImageIfExist();
		setImageDetails(imageFile);
		saveImageToDirectory(imageFile);
		saveImageToDB();

		return userImageEntity.getImageUrl();
	}

	private void getImageEntity() throws Exception {
		UserEntity currentUserEntity = userService.getCurrentUserEntity_FullData();
		UserDetailsEntity userDetails = ofNullable(currentUserEntity.getUserDetails())
				.orElse(new UserDetailsEntity());
		userImageEntity = ofNullable(userDetails.getUserImage())
				.orElse(new UserImageEntity());
		userDetails.setUserImage(userImageEntity);
	}

	private void deletePreviousImageIfExist() throws Exception {
		ofNullable(userImageEntity.getImageUrl())
				.map(Paths::get)
				.ifPresent(this::deletePath);
	}

	private void deletePath(Path imagePath) {
		try {
			Files.delete(imagePath);
		} catch (Exception e) {
			throw  new RuntimeBusinessException(INTERNAL_SERVER_ERROR, IMG$0003, imagePath.toString());
		}
	}

	private void setImageDetails(MultipartFile imageFile) {
		String imageOriginalName = imageFile.getOriginalFilename();
		String imageFormat = imageFile.getContentType();
		String imageUrl = getSavePath(imageFile).toString();

		userImageEntity.setImageFormat(imageFormat);
		userImageEntity.setImageName(imageOriginalName);
		userImageEntity.setImageUrl(imageUrl);
	}

	private void saveImageToDirectory(MultipartFile imageFile) {
		try {
			Path saveDirectory = getSavePath(imageFile);
			imageFile.transferTo(saveDirectory);
		} catch (Exception exception) {
			throw new RuntimeBusinessException(
					INTERNAL_SERVER_ERROR,
					IMG$0002,
					imageFile.getOriginalFilename(), getSavePath(imageFile).toString());
		}
	}

	private Path getSavePath(MultipartFile imageFile) {
		String imageOriginalName = imageFile.getOriginalFilename();
		return basePath.resolve(imageOriginalName);
	}

	private void saveImageToDB() {
		imageRepository.save(userImageEntity);
	}

	@Override
	public ProfileImageDTO getProfileImage() throws Exception {
		UserEntity currentUserEntity = userService.getCurrentUserEntity_FullData();

		ofNullable(currentUserEntity.getUserDetails())
				.map(UserDetailsEntity::getUserImage)
				.ifPresent(this::getImageDTO);

		return imageDTO;
	}

	private void getImageDTO(UserImageEntity imageEntity){
		EntityToDtoMapper mapper = new UserImageEntityToDtoMapper();
		byte [] imageData = getImageData(imageEntity.getImageUrl());
		imageDTO = (ProfileImageDTO) mapper.mapToDTO(imageEntity);
		imageDTO.setImageData(imageData);
	}

	private byte [] getImageData(String imageUrl){
		try {
			Path imagePath = Paths.get(imageUrl);
			return Files.readAllBytes(imagePath);
		}catch (Exception exception){
			throw new RuntimeBusinessException(INTERNAL_SERVER_ERROR, IMG$0004, imageUrl);
		}
	}
}
