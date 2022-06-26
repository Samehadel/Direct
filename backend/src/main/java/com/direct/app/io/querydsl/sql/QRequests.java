package com.direct.app.io.querydsl.sql;

import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.sql.ColumnMetadata;

import javax.annotation.Generated;
import java.sql.Types;

import static com.querydsl.core.types.PathMetadataFactory.forVariable;




/**
 * QRequests is a Querydsl query type for QRequests
 */
@Generated("com.querydsl.sql.codegen.MetaDataSerializer")
public class QRequests extends com.querydsl.sql.RelationalPathBase<QRequests> {

    private static final long serialVersionUID = 904540703;

    public static final QRequests requests = new QRequests("requests");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> receiverId = createNumber("receiverId", Long.class);

    public final NumberPath<Long> senderId = createNumber("senderId", Long.class);

    public final com.querydsl.sql.PrimaryKey<QRequests> primary = createPrimaryKey(id);

    public QRequests(String variable) {
        super(QRequests.class, forVariable(variable), "null", "requests");
        addMetadata();
    }

    public QRequests(String variable, String schema, String table) {
        super(QRequests.class, forVariable(variable), schema, table);
        addMetadata();
    }

    public QRequests(String variable, String schema) {
        super(QRequests.class, forVariable(variable), schema, "requests");
        addMetadata();
    }

    public QRequests(Path<? extends QRequests> path) {
        super(path.getType(), path.getMetadata(), "null", "requests");
        addMetadata();
    }

    public QRequests(PathMetadata metadata) {
        super(QRequests.class, metadata, "null", "requests");
        addMetadata();
    }

    public void addMetadata() {
        addMetadata(id, ColumnMetadata.named("id").withIndex(1).ofType(Types.BIGINT).withSize(19).notNull());
        addMetadata(receiverId, ColumnMetadata.named("receiver_id").withIndex(2).ofType(Types.BIGINT).withSize(19));
        addMetadata(senderId, ColumnMetadata.named("sender_id").withIndex(3).ofType(Types.BIGINT).withSize(19));
    }

}

