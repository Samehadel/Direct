package com.direct.app.ws.integration;

import com.direct.app.config.AppConfiguration;
import com.direct.app.io.entities.UserDetailsEntity;
import com.direct.app.io.entities.UserEntity;
import com.direct.app.io.entities.UserImageEntity;
import com.direct.app.repositery.UserImageRepository;
import com.direct.app.service.UserService;
import com.direct.app.ws.commons.TestCommons;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.util.Optional.ofNullable;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@TestPropertySource("/test.application.properties")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(executionPhase= Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts={"/sql/database_cleanup.sql"})
public class ProfileImageControllerTest {

	private final String ADD_PROFILE_IMAGE_URL = "/profile/image";
	private final String GET_ACCOUNT_IMAGE_URL = "/profile/image";

	@LocalServerPort
	private int port;

	@Autowired
	private UserImageRepository imageRepository;

	@Autowired
	private TestRestTemplate testRestTemplate;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Autowired
	private TestCommons testCommons;

	@Autowired
	private UserService userService;

	@Autowired
	private AppConfiguration appConfig;

	private String authToken;
	private static Path basePath;
	private MockMvc mockMvc;


	@Before
	public void init(){
		basePath = Paths.get(appConfig.getProfileImagesBasePath());
		authToken = testCommons.generateUserAuthToken("username_1");
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@AfterClass
	public static void cleanup() throws IOException {
		File file = new File(basePath.toString());

		if(Files.exists(basePath)) {
			FileUtils.cleanDirectory(file);
			FileUtils.deleteDirectory(file);
		}
	}

	@Test
	@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = {"/sql/database_cleanup.sql", "/sql/Insert_User_Data.sql"})
	public void addProfileImageTest() throws Exception {
		MockMultipartFile imageFile = getDummyImage_jpeg("name");

		String imageUrl = sendImageRequest(imageFile)
									.andExpect(status().is(200))
									.andReturn()
									.getResponse()
									.getContentAsString();
		assertImageExistByUrl(imageUrl);
	}

	private MockMultipartFile getDummyImage_jpeg(String imageName) {
		return new MockMultipartFile(
				"imageFile",
				imageName,
				MediaType.IMAGE_JPEG_VALUE,
				"content".getBytes());
	}

	private ResultActions sendImageRequest(MockMultipartFile imageFile) throws Exception {
		return mockMvc.perform(MockMvcRequestBuilders.multipart(ADD_PROFILE_IMAGE_URL)
						.file(imageFile)
						.header("Authorization", authToken)
						.contentType(MediaType.MULTIPART_FORM_DATA_VALUE));
	}

	private void assertImageExistByUrl(String imageUrl) throws UnsupportedEncodingException {
		Path imagePath = Paths.get(imageUrl);

		assertTrue(Files.exists(imagePath));
	}

	@Test
	@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = {"/sql/database_cleanup.sql", "/sql/Insert_User_Data.sql"})
	public void changeExistingProfileImage() throws Exception {
		String imageName_1 = "name_1";
		String imageName_2 = "name_2";

		MockMultipartFile imageFile_1 = getDummyImage_jpeg(imageName_1);
		MockMultipartFile imageFile_2 = getDummyImage_jpeg(imageName_2);

		sendImageRequest(imageFile_1);
		String imageUrl = sendImageRequest(imageFile_2)
							.andReturn()
							.getResponse()
							.getContentAsString();

		assertUserImageHasNameOf(imageName_2);
		assertUserImageHasUrlOf(imageUrl);
	}

	private void assertUserImageHasNameOf(String imageName) throws Exception {
		UserImageEntity userImage = getImageEntity();

		assertEquals(imageName, userImage.getImageName());
	}

	private void assertUserImageHasUrlOf(String imageUrl) throws Exception {
		UserImageEntity userImage = getImageEntity();

		assertEquals(imageUrl, userImage.getImageUrl());
	}

	private UserImageEntity getImageEntity() throws Exception {
		UserEntity currentUser = userService.getCurrentUserEntity_FullData();

		return ofNullable(currentUser.getUserDetails())
					.map(UserDetailsEntity::getUserImage)
					.orElse(new UserImageEntity());
	}
}
