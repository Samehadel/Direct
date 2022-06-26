package com.direct.app.io.querydsl.jpa;

import com.direct.app.io.entities.PublicationEntity;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.*;

import javax.annotation.Generated;

import static com.querydsl.core.types.PathMetadataFactory.forVariable;


/**
 * QPublicationEntity is a Querydsl query type for PublicationEntity
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QPublicationEntity extends EntityPathBase<PublicationEntity> {

    private static final long serialVersionUID = -694122523L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPublicationEntity publicationEntity = new QPublicationEntity("publicationEntity");

    public final StringPath content = createString("content");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isRead = createBoolean("isRead");

    public final StringPath link = createString("link");

    public final QUserEntity receiver;

    public final QUserEntity sender;

    public QPublicationEntity(String variable) {
        this(PublicationEntity.class, forVariable(variable), INITS);
    }

    public QPublicationEntity(Path<? extends PublicationEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPublicationEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPublicationEntity(PathMetadata metadata, PathInits inits) {
        this(PublicationEntity.class, metadata, inits);
    }

    public QPublicationEntity(Class<? extends PublicationEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.receiver = inits.isInitialized("receiver") ? new QUserEntity(forProperty("receiver"), inits.get("receiver")) : null;
        this.sender = inits.isInitialized("sender") ? new QUserEntity(forProperty("sender"), inits.get("sender")) : null;
    }

}

