package com.direct.app.io.querydsl.sql;

import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.sql.ColumnMetadata;

import javax.annotation.Generated;
import java.sql.Types;

import static com.querydsl.core.types.PathMetadataFactory.forVariable;




/**
 * QSubscriptions is a Querydsl query type for QSubscriptions
 */
@Generated("com.querydsl.sql.codegen.MetaDataSerializer")
public class QSubscriptions extends com.querydsl.sql.RelationalPathBase<QSubscriptions> {

    private static final long serialVersionUID = 1716926843;

    public static final QSubscriptions subscriptions = new QSubscriptions("subscriptions");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> keywordId = createNumber("keywordId", Integer.class);

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public final com.querydsl.sql.PrimaryKey<QSubscriptions> primary = createPrimaryKey(id);

    public QSubscriptions(String variable) {
        super(QSubscriptions.class, forVariable(variable), "null", "subscriptions");
        addMetadata();
    }

    public QSubscriptions(String variable, String schema, String table) {
        super(QSubscriptions.class, forVariable(variable), schema, table);
        addMetadata();
    }

    public QSubscriptions(String variable, String schema) {
        super(QSubscriptions.class, forVariable(variable), schema, "subscriptions");
        addMetadata();
    }

    public QSubscriptions(Path<? extends QSubscriptions> path) {
        super(path.getType(), path.getMetadata(), "null", "subscriptions");
        addMetadata();
    }

    public QSubscriptions(PathMetadata metadata) {
        super(QSubscriptions.class, metadata, "null", "subscriptions");
        addMetadata();
    }

    public void addMetadata() {
        addMetadata(id, ColumnMetadata.named("id").withIndex(1).ofType(Types.BIGINT).withSize(19).notNull());
        addMetadata(keywordId, ColumnMetadata.named("keyword_id").withIndex(2).ofType(Types.INTEGER).withSize(10));
        addMetadata(userId, ColumnMetadata.named("user_id").withIndex(3).ofType(Types.BIGINT).withSize(19));
    }

}

