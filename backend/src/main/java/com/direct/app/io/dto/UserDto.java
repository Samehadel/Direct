package com.direct.app.io.dto;

import com.direct.app.io.entities.UserEntity;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class UserDto extends BaseDTO {
	private Long id;
	private String firstName;
	private String lastName;
	private String username;
	private String password;


	public UserEntity generateUserEntityFromDTO(){

		UserEntity userEntity = new UserEntity();
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

		userEntity.setId(this.id);
		userEntity.setUsername(this.username);
		userEntity.setFirstName(this.firstName);
		userEntity.setLastName(this.lastName);
		userEntity.setEncryptedPassword(encoder.encode(this.password));

		return userEntity;
	}
}
