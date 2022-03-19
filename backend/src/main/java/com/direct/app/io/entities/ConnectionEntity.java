package com.direct.app.io.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "connections")
public class ConnectionEntity {

	// Entity attributes

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "connections_generator")
	@SequenceGenerator(name = "connections_generator", sequenceName = "connections_sequence", allocationSize = 1)
	private Long id;

	
	// Relationships
	
	@ManyToOne(cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
	@JoinColumn(name = "first_user_id", foreignKey = @ForeignKey(name = "fk_connections_first_user_id"))
	private UserEntity firstUser;

	@ManyToOne(cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
	@JoinColumn(name = "second_user_id", foreignKey = @ForeignKey(name = "fk_connections_second_user_id"))
	private UserEntity secondUser;
}
