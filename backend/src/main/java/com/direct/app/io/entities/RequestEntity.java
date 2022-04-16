package com.direct.app.io.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Table(name = "requests")
public class RequestEntity {

	// Entity attributes

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "requests_generator")
	@SequenceGenerator(name = "requests_generator", sequenceName = "requests_sequence", allocationSize = 1)
	private Long id;

	
	// Relationships

	@ManyToOne(cascade = { CascadeType.DETACH, CascadeType.MERGE, 
							CascadeType.PERSIST, CascadeType.REFRESH })
	@JoinColumn(name = "sender_id", foreignKey = @ForeignKey(name = "fk_requests_sender_id"))
	private UserEntity sender;

	@ManyToOne(cascade = { CascadeType.DETACH, CascadeType.MERGE, 
			CascadeType.PERSIST, CascadeType.REFRESH })
	@JoinColumn(name = "receiver_id", foreignKey = @ForeignKey(name = "fk_requests_receiver_id"))
	private UserEntity receiver;

	public RequestEntity(Long id) {
		this.id = id;
	}

	public void assignConnectionRequestSender(UserEntity sender){
		sender.addSentRequest(this);
		this.setSender(sender);
	}
	public void assignConnectionRequestReceiver(UserEntity receiver) {
		receiver.addReceivedRequest(this);
		this.setReceiver(receiver);
	}
}
