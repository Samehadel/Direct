package com.direct.app.io.querydsl.jpa;

import com.direct.app.io.entities.*;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.*;

import javax.annotation.Generated;

import static com.querydsl.core.types.PathMetadataFactory.forVariable;


/**
 * QUserEntity is a Querydsl query type for UserEntity
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QUserEntity extends EntityPathBase<UserEntity> {

    private static final long serialVersionUID = 970428824L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUserEntity userEntity = new QUserEntity("userEntity");

    public final QUserAuthorityEntity authority;

    public final BooleanPath emailVerificationStatus = createBoolean("emailVerificationStatus");

    public final StringPath encryptedPassword = createString("encryptedPassword");

    public final StringPath firstName = createString("firstName");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath lastName = createString("lastName");

    public final ListPath<ConnectionEntity, QConnectionEntity> receivedConnections = this.<ConnectionEntity, QConnectionEntity>createList("receivedConnections", ConnectionEntity.class, QConnectionEntity.class, PathInits.DIRECT2);

    public final ListPath<PublicationEntity, QPublicationEntity> receivedPublications = this.<PublicationEntity, QPublicationEntity>createList("receivedPublications", PublicationEntity.class, QPublicationEntity.class, PathInits.DIRECT2);

    public final ListPath<RequestEntity, QRequestEntity> receivedRequests = this.<RequestEntity, QRequestEntity>createList("receivedRequests", RequestEntity.class, QRequestEntity.class, PathInits.DIRECT2);

    public final ListPath<ConnectionEntity, QConnectionEntity> sentConnections = this.<ConnectionEntity, QConnectionEntity>createList("sentConnections", ConnectionEntity.class, QConnectionEntity.class, PathInits.DIRECT2);

    public final ListPath<PublicationEntity, QPublicationEntity> sentPublications = this.<PublicationEntity, QPublicationEntity>createList("sentPublications", PublicationEntity.class, QPublicationEntity.class, PathInits.DIRECT2);

    public final ListPath<RequestEntity, QRequestEntity> sentRequests = this.<RequestEntity, QRequestEntity>createList("sentRequests", RequestEntity.class, QRequestEntity.class, PathInits.DIRECT2);

    public final ListPath<SubscriptionEntity, QSubscriptionEntity> subscriptions = this.<SubscriptionEntity, QSubscriptionEntity>createList("subscriptions", SubscriptionEntity.class, QSubscriptionEntity.class, PathInits.DIRECT2);

    public final QUserDetailsEntity userDetails;

    public final StringPath username = createString("username");

    public final StringPath virtualUserId = createString("virtualUserId");

    public QUserEntity(String variable) {
        this(UserEntity.class, forVariable(variable), INITS);
    }

    public QUserEntity(Path<? extends UserEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUserEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUserEntity(PathMetadata metadata, PathInits inits) {
        this(UserEntity.class, metadata, inits);
    }

    public QUserEntity(Class<? extends UserEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.authority = inits.isInitialized("authority") ? new QUserAuthorityEntity(forProperty("authority"), inits.get("authority")) : null;
        this.userDetails = inits.isInitialized("userDetails") ? new QUserDetailsEntity(forProperty("userDetails"), inits.get("userDetails")) : null;
    }

}

