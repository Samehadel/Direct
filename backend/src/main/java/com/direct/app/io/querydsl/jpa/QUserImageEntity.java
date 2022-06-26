package com.direct.app.io.querydsl.jpa;

import com.direct.app.io.entities.UserImageEntity;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.StringPath;

import javax.annotation.Generated;

import static com.querydsl.core.types.PathMetadataFactory.forVariable;


/**
 * QUserImageEntity is a Querydsl query type for UserImageEntity
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QUserImageEntity extends EntityPathBase<UserImageEntity> {

    private static final long serialVersionUID = -384211831L;

    public static final QUserImageEntity userImageEntity = new QUserImageEntity("userImageEntity");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath imageFormat = createString("imageFormat");

    public final StringPath imageName = createString("imageName");

    public final StringPath imageUrl = createString("imageUrl");

    public QUserImageEntity(String variable) {
        super(UserImageEntity.class, forVariable(variable));
    }

    public QUserImageEntity(Path<? extends UserImageEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUserImageEntity(PathMetadata metadata) {
        super(UserImageEntity.class, metadata);
    }

}

