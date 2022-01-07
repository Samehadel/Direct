package com.direct.app.io.entities;

import javax.persistence.*;

@Entity
@Table(name = "publications")
public class PublicationEntity {

	//Entity attributes

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "publications_generator")
	@SequenceGenerator(name = "publications_generator", sequenceName = "publications_sequence", allocationSize = 1)
	private long id;

	@Column(name = "content", nullable = true)
	private String content;
	
	@Column(name = "link", nullable = true)
	private String link;
	
	@Column(name = "is_read", nullable = false)
	private boolean isRead = false;

	//Relationships
	@ManyToOne(cascade = { CascadeType.DETACH, CascadeType.MERGE, 
			CascadeType.PERSIST, CascadeType.REFRESH })
	@JoinColumn(name = "sender_id", foreignKey = @ForeignKey(name = "fk_publications_sender_id"))
	private UserEntity sender; 
	
	@ManyToOne(cascade = { CascadeType.DETACH, CascadeType.MERGE, 
			CascadeType.PERSIST, CascadeType.REFRESH })
	@JoinColumn(name = "receiver_id", foreignKey = @ForeignKey(name = "fk_publications_receiver_id"))
	private UserEntity receiver;


	//Default Constructor
	public PublicationEntity() {}


	//Setters & Getters

	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}


	public String getLink() {
		return link;
	}


	public void setLink(String link) {
		this.link = link;
	}

	public boolean isRead() {
		return isRead;
	}

	public void setRead(boolean read) {
		isRead = read;
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
