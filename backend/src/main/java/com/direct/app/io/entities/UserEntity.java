package com.direct.app.io.entities;

import com.direct.app.io.dto.UserDto;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

	
@Entity
@Getter
@Setter
@Table(name = "users")
public class UserEntity extends BaseEntity implements Serializable {

	private static final long serialVersionUID = -6671347407969225029L;

	// Entity attributes

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_generator")
	@SequenceGenerator(name = "user_generator", sequenceName = "users_sequence", allocationSize = 1)
	@EqualsAndHashCode.Include
	private Long id;

	@Column(name = "first_name", nullable = false, length = 50)
	private String firstName;

	@Column(name = "last_name", nullable = false, length = 50)
	private String lastName;

	@EqualsAndHashCode.Include
	@Column(name = "username", nullable = false, length = 150)
	private String username;

	@Column(name = "encrypted_password", nullable = false, length = 500)
	private String encryptedPassword;

	@Column(name = "email_verification_status", nullable = false)
	private boolean emailVerificationStatus = false;

	@Column(name = "virtual_user_id", nullable = false, length = 255)
	private String virtualUserId;

	
	// Relationships

	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL) // 1- with 'user_authorities'
	private UserAuthorityEntity authority;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_details_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_user_details_id"))// 2- with 'user_details'
	private UserDetailsEntity userDetails;

	// TODO: replace eager with lazy
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER) // 3- with 'subscriptions'
	private List<SubscriptionEntity> subscriptions;

	@OneToMany(mappedBy = "sender", cascade = CascadeType.ALL, fetch = FetchType.LAZY) // 4- with 'requests'
	private List<RequestEntity> sentRequests;

	@OneToMany(mappedBy = "receiver", cascade = CascadeType.ALL, fetch = FetchType.LAZY) // 5- with 'requests'
	private List<RequestEntity> receivedRequests;

	@OneToMany(mappedBy = "firstUser", cascade = CascadeType.ALL, fetch = FetchType.LAZY) // 6- with 'connections'
	private List<ConnectionEntity> sentConnections;

	@OneToMany(mappedBy = "secondUser", cascade = CascadeType.ALL) // 7- with 'connections'
	private List<ConnectionEntity> receivedConnections;

	
	@OneToMany(mappedBy = "sender", cascade = CascadeType.ALL) // 8- with 'publications'
	private List<PublicationEntity> sentPublications;

	@OneToMany(mappedBy = "receiver", cascade = CascadeType.ALL) // 9- with 'publications'
	private List<PublicationEntity> receivedPublications;

	public UserEntity() {
		this.authority = new UserAuthorityEntity();
		this.userDetails = new UserDetailsEntity();
		this.subscriptions = new ArrayList<>();
		this.sentRequests = new ArrayList<>();
		this.receivedRequests = new ArrayList<>();
		this.sentConnections = new ArrayList<>();
		this.receivedConnections = new ArrayList<>();
	}
	public UserEntity(Long id) {
		this.id = id;
	}

	// Add subscription to the user
	public void addSubscription(SubscriptionEntity sub) {
		if (subscriptions == null)
			subscriptions = new ArrayList<>();

		subscriptions.add(sub);
	}

	// Add sent request to the user
	public void addSentRequest(RequestEntity request) {
		if (sentRequests == null)
			sentRequests = new ArrayList<>();

		sentRequests.add(request);
	}

	// Add received request to the user
	public void addReceivedRequest(RequestEntity request) {
		if (receivedRequests == null)
			receivedRequests = new ArrayList<>();

		receivedRequests.add(request);
	}

	// Add sent connection to the user
	public void addSentConnection(ConnectionEntity connection) {
		if (sentConnections == null)
			sentConnections = new ArrayList<>();

		sentConnections.add(connection);
	}

	// Add received connection to the user
	public void addReceivedConnection(ConnectionEntity connection) {
		if (receivedConnections == null)
			receivedConnections = new ArrayList<>();

		receivedConnections.add(connection);
	}

	// Add sent connection to the user
	public void addSentPublication(PublicationEntity publication) {
		if (sentPublications == null)
			sentPublications = new ArrayList<>();

		sentPublications.add(publication);
	}

	// Add received connection to the user
	public void addReceivedPublication(PublicationEntity publication) {
		if (receivedPublications == null)
			receivedPublications = new ArrayList<>();

		receivedPublications.add(publication);
	}

	public UserDto generateUserDTOFromEntity(){
		UserDto userDto = new UserDto();

		userDto.setId(this.id);
		userDto.setUsername(this.username);
		userDto.setFirstName(this.firstName);
		userDto.setLastName(this.lastName);

		return userDto;
	}
}
