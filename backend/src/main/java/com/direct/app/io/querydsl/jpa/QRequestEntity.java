package com.direct.app.io.querydsl.jpa;

import com.direct.app.io.entities.RequestEntity;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.PathInits;

import javax.annotation.Generated;

import static com.querydsl.core.types.PathMetadataFactory.forVariable;


/**
 * QRequestEntity is a Querydsl query type for RequestEntity
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QRequestEntity extends EntityPathBase<RequestEntity> {

    private static final long serialVersionUID = 394038536L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRequestEntity requestEntity = new QRequestEntity("requestEntity");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QUserEntity receiver;

    public final QUserEntity sender;

    public QRequestEntity(String variable) {
        this(RequestEntity.class, forVariable(variable), INITS);
    }

    public QRequestEntity(Path<? extends RequestEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRequestEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRequestEntity(PathMetadata metadata, PathInits inits) {
        this(RequestEntity.class, metadata, inits);
    }

    public QRequestEntity(Class<? extends RequestEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.receiver = inits.isInitialized("receiver") ? new QUserEntity(forProperty("receiver"), inits.get("receiver")) : null;
        this.sender = inits.isInitialized("sender") ? new QUserEntity(forProperty("sender"), inits.get("sender")) : null;
    }

}

