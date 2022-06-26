package com.direct.app.io.querydsl.jpa;

import com.direct.app.io.entities.ConnectionEntity;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.PathInits;

import javax.annotation.Generated;

import static com.querydsl.core.types.PathMetadataFactory.forVariable;


/**
 * QConnectionEntity is a Querydsl query type for ConnectionEntity
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QConnectionEntity extends EntityPathBase<ConnectionEntity> {

    private static final long serialVersionUID = -1090250005L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QConnectionEntity connectionEntity = new QConnectionEntity("connectionEntity");

    public final QUserEntity firstUser;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QUserEntity secondUser;

    public QConnectionEntity(String variable) {
        this(ConnectionEntity.class, forVariable(variable), INITS);
    }

    public QConnectionEntity(Path<? extends ConnectionEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QConnectionEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QConnectionEntity(PathMetadata metadata, PathInits inits) {
        this(ConnectionEntity.class, metadata, inits);
    }

    public QConnectionEntity(Class<? extends ConnectionEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.firstUser = inits.isInitialized("firstUser") ? new QUserEntity(forProperty("firstUser"), inits.get("firstUser")) : null;
        this.secondUser = inits.isInitialized("secondUser") ? new QUserEntity(forProperty("secondUser"), inits.get("secondUser")) : null;
    }

}

