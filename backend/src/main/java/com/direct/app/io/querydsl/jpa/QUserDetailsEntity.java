package com.direct.app.io.querydsl.jpa;

import com.direct.app.io.entities.UserDetailsEntity;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.PathInits;
import com.querydsl.core.types.dsl.StringPath;

import javax.annotation.Generated;

import static com.querydsl.core.types.PathMetadataFactory.forVariable;


/**
 * QUserDetailsEntity is a Querydsl query type for UserDetailsEntity
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QUserDetailsEntity extends EntityPathBase<UserDetailsEntity> {

    private static final long serialVersionUID = 1382544752L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUserDetailsEntity userDetailsEntity = new QUserDetailsEntity("userDetailsEntity");

    public final StringPath bio = createString("bio");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath majorField = createString("majorField");

    public final StringPath phone = createString("phone");

    public final StringPath professionalTitle = createString("professionalTitle");

    public final QUserImageEntity userImage;

    public QUserDetailsEntity(String variable) {
        this(UserDetailsEntity.class, forVariable(variable), INITS);
    }

    public QUserDetailsEntity(Path<? extends UserDetailsEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUserDetailsEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUserDetailsEntity(PathMetadata metadata, PathInits inits) {
        this(UserDetailsEntity.class, metadata, inits);
    }

    public QUserDetailsEntity(Class<? extends UserDetailsEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.userImage = inits.isInitialized("userImage") ? new QUserImageEntity(forProperty("userImage")) : null;
    }

}

