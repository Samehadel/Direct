package com.direct.app.io.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "publications")
public class PublicationEntity extends BaseEntity {

	//Entity attributes

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "publications_generator")
	@SequenceGenerator(name = "publications_generator", sequenceName = "publications_sequence", allocationSize = 1)
	private Long id;

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

	public PublicationEntity() {
		this.sender = new UserEntity();
		this.receiver = new UserEntity();
	}
}
