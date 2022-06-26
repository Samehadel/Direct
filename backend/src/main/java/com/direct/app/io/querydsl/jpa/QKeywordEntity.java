package com.direct.app.io.querydsl.jpa;

import com.direct.app.io.entities.KeywordEntity;
import com.direct.app.io.entities.SubscriptionEntity;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.*;

import javax.annotation.Generated;

import static com.querydsl.core.types.PathMetadataFactory.forVariable;


/**
 * QKeywordEntity is a Querydsl query type for KeywordEntity
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QKeywordEntity extends EntityPathBase<KeywordEntity> {

    private static final long serialVersionUID = 906448098L;

    public static final QKeywordEntity keywordEntity = new QKeywordEntity("keywordEntity");

    public final StringPath description = createString("description");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final ListPath<SubscriptionEntity, QSubscriptionEntity> subscriptions = this.<SubscriptionEntity, QSubscriptionEntity>createList("subscriptions", SubscriptionEntity.class, QSubscriptionEntity.class, PathInits.DIRECT2);

    public QKeywordEntity(String variable) {
        super(KeywordEntity.class, forVariable(variable));
    }

    public QKeywordEntity(Path<? extends KeywordEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QKeywordEntity(PathMetadata metadata) {
        super(KeywordEntity.class, metadata);
    }

}

