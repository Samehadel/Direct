package com.direct.app.io.entities;

import javax.persistence.*;

@Entity
@Table(name = "connections")
public class ConnectionEntity {

	// Entity attributes

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "connections_generator")
	@SequenceGenerator(name = "connections_generator", sequenceName = "connections_sequence", allocationSize = 1)
	private long id;

	
	// Relationships
	
	@ManyToOne(cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
	@JoinColumn(name = "first_user_id", foreignKey = @ForeignKey(name = "fk_connections_first_user_id"))
	private UserEntity firstUser;

	@ManyToOne(cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
	@JoinColumn(name = "second_user_id", foreignKey = @ForeignKey(name = "fk_connections_second_user_id"))
	private UserEntity secondUser;

	// Constructors

	public ConnectionEntity() {}

	
	// Setters & Getters

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public UserEntity getFirstUser() {
		return firstUser;
	}

	public void setFirstUser(UserEntity firstUser) {
		this.firstUser = firstUser;
	}

	public UserEntity getSecondUser() {
		return secondUser;
	}

	public void setSecondUser(UserEntity secondUser) {
		this.secondUser = secondUser;
	}
}
