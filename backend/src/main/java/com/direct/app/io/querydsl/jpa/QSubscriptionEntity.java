package com.direct.app.io.querydsl.jpa;

import com.direct.app.io.entities.SubscriptionEntity;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.PathInits;

import javax.annotation.Generated;

import static com.querydsl.core.types.PathMetadataFactory.forVariable;


/**
 * QSubscriptionEntity is a Querydsl query type for SubscriptionEntity
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QSubscriptionEntity extends EntityPathBase<SubscriptionEntity> {

    private static final long serialVersionUID = -123564182L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSubscriptionEntity subscriptionEntity = new QSubscriptionEntity("subscriptionEntity");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QKeywordEntity keyword;

    public final QUserEntity user;

    public QSubscriptionEntity(String variable) {
        this(SubscriptionEntity.class, forVariable(variable), INITS);
    }

    public QSubscriptionEntity(Path<? extends SubscriptionEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSubscriptionEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSubscriptionEntity(PathMetadata metadata, PathInits inits) {
        this(SubscriptionEntity.class, metadata, inits);
    }

    public QSubscriptionEntity(Class<? extends SubscriptionEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.keyword = inits.isInitialized("keyword") ? new QKeywordEntity(forProperty("keyword")) : null;
        this.user = inits.isInitialized("user") ? new QUserEntity(forProperty("user"), inits.get("user")) : null;
    }

}

