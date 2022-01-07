package com.direct.app.io.entities;

import javax.persistence.*;


@Entity
@Table(name = "user_details")
public class UserDetailsEntity {

	//Entity attributes
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "details_generator")
	@SequenceGenerator(name = "details_generator", sequenceName = "details_sequence", allocationSize = 1)
	private long id;
	
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
	@JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "fk_user_details_user_id"), referencedColumnName = "id")
	private UserEntity user;

	@OneToOne(mappedBy = "userDetails", cascade = CascadeType.ALL)
	private UserImageEntity userImage;

	//Default Constructor
	public UserDetailsEntity() {}

	
	//Setters & Getters
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getMajorField() {
		return majorField;
	}

	public void setMajorField(String majorField) {
		this.majorField = majorField;
	}

	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

	public String getProfessionalTitle() {
		return professionalTitle;
	}

	public void setProfessionalTitle(String professionalTitle) {
		this.professionalTitle = professionalTitle;
	}

	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}

	public UserImageEntity getUserImage() {
		return userImage;
	}

	public void setUserImage(UserImageEntity userImage) {
		this.userImage = userImage;
	}
}
