package com.direct.app.io.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
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

}
