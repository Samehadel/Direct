package com.direct.app.io.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@Table(name = "subscriptions")
public class SubscriptionEntity {

	// Entity attributes

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "subscriptions_generator")
	@SequenceGenerator(name = "subscriptions_generator", sequenceName = "subscriptions_sequence", allocationSize = 1)
	private Long id;

	// Relationships

	@ManyToOne(cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST,
			CascadeType.REFRESH }, fetch = FetchType.EAGER)
	@JoinColumn(name = "keyword_id", foreignKey = @ForeignKey(name = "fk_subscriptions_keyword_id"))
	private KeywordEntity keyword;

	@ManyToOne(cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST,
			CascadeType.REFRESH })
	@JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "fk_subscriptions_user_id"))
	private UserEntity user;
}
