package com.direct.app.io.entities;

import javax.persistence.*;

@Entity
@Table(name = "requests")
public class RequestEntity {

	// Entity attributes

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "requests_generator")
	@SequenceGenerator(name = "requests_generator", sequenceName = "requests_sequence", allocationSize = 1)
	private long id;

	
	// Relationships

	@ManyToOne(cascade = { CascadeType.DETACH, CascadeType.MERGE, 
							CascadeType.PERSIST, CascadeType.REFRESH })
	@JoinColumn(name = "sender_id", foreignKey = @ForeignKey(name = "fk_requests_sender_id"))
	private UserEntity sender;

	@ManyToOne(cascade = { CascadeType.DETACH, CascadeType.MERGE, 
			CascadeType.PERSIST, CascadeType.REFRESH })
	@JoinColumn(name = "receiver_id", foreignKey = @ForeignKey(name = "fk_requests_receiver_id"))
	private UserEntity receiver;

	
	
	// Default Constructor
	public RequestEntity() {}

	
	// Setters & Getters
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public UserEntity getSender() {
		return sender;
	}

	public void setSender(UserEntity sender) {
		this.sender = sender;
	}

	public UserEntity getReceiver() {
		return receiver;
	}

	public void setReceiver(UserEntity receiver) {
		this.receiver = receiver;
	}
}
