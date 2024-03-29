package com.direct.app.io.dto;

import com.direct.app.io.entities.UserEntity;
import com.direct.app.redis.RedisHash;
import com.direct.app.redis.RedisSchema;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class UserDto extends BaseDTO implements RedisHash {
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

	@Override
	public String toString() {
		return "UserDto{" +
				"id=" + id +
				", firstName='" + firstName + '\'' +
				", lastName='" + lastName + '\'' +
				", username='" + username + '\'' +
				", password='" + password + '\'' +
				'}';
	}

	@Override
	public String getRedisKey() {
		return RedisSchema.getUserHashKey() + ":" + getId().toString();
	}

}
