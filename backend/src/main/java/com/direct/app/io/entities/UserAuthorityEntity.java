package com.direct.app.io.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "user_authorities")
public class UserAuthorityEntity extends BaseEntity {

	//Entity attributes
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "auth_generator")
	@SequenceGenerator(name = "auth_generator", sequenceName = "auth_sequence", allocationSize = 1)
	private Long id;
	
	@Column(name = "role", nullable = false, length = 50)
	private String role;

	//Relationships
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "fk_user_authorities_user_id"))
	private UserEntity user;

	public UserAuthorityEntity(String role) {
		super();
		this.role = role;
	}
}
