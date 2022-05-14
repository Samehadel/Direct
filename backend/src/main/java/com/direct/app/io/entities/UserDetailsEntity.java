package com.direct.app.io.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Entity
@Getter
@Setter
@EqualsAndHashCode
@Table(name = "user_details")
public class UserDetailsEntity extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "details_generator")
	@SequenceGenerator(name = "details_generator", sequenceName = "details_sequence", allocationSize = 1)
	private Long id;
	
	@Column(name = "phone", nullable = true, length = 14)
	private String phone;
	
	@Column(name = "major_field", nullable = true, length = 50)
	private String majorField;
	
	@Column(name = "bio", nullable = true, length = 500)
	private String bio;
	
	@Column(name = "professional_title", nullable = true, length = 50)
	private String professionalTitle;

	//Relationships
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_image_id", foreignKey = @ForeignKey(name = "fk_user_image_id"), referencedColumnName = "id")
	private UserImageEntity userImage;

}
