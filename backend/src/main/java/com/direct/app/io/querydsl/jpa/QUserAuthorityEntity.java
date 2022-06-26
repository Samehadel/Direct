package com.direct.app.io.querydsl.jpa;

import com.direct.app.io.entities.UserAuthorityEntity;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.PathInits;
import com.querydsl.core.types.dsl.StringPath;

import javax.annotation.Generated;

import static com.querydsl.core.types.PathMetadataFactory.forVariable;


/**
 * QUserAuthorityEntity is a Querydsl query type for UserAuthorityEntity
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QUserAuthorityEntity extends EntityPathBase<UserAuthorityEntity> {

    private static final long serialVersionUID = 320669425L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUserAuthorityEntity userAuthorityEntity = new QUserAuthorityEntity("userAuthorityEntity");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath role = createString("role");

    public final QUserEntity user;

    public QUserAuthorityEntity(String variable) {
        this(UserAuthorityEntity.class, forVariable(variable), INITS);
    }

    public QUserAuthorityEntity(Path<? extends UserAuthorityEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUserAuthorityEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUserAuthorityEntity(PathMetadata metadata, PathInits inits) {
        this(UserAuthorityEntity.class, metadata, inits);
    }

    public QUserAuthorityEntity(Class<? extends UserAuthorityEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new QUserEntity(forProperty("user"), inits.get("user")) : null;
    }

}

