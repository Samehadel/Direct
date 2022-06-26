package com.direct.app.io.querydsl.sql;

import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.BooleanPath;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.sql.ColumnMetadata;

import javax.annotation.Generated;
import java.sql.Types;

import static com.querydsl.core.types.PathMetadataFactory.forVariable;




/**
 * QPublications is a Querydsl query type for QPublications
 */
@Generated("com.querydsl.sql.codegen.MetaDataSerializer")
public class QPublications extends com.querydsl.sql.RelationalPathBase<QPublications> {

    private static final long serialVersionUID = -597897054;

    public static final QPublications publications = new QPublications("publications");

    public final StringPath content = createString("content");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isRead = createBoolean("isRead");

    public final StringPath link = createString("link");

    public final NumberPath<Long> receiverId = createNumber("receiverId", Long.class);

    public final NumberPath<Long> senderId = createNumber("senderId", Long.class);

    public final com.querydsl.sql.PrimaryKey<QPublications> primary = createPrimaryKey(id);

    public QPublications(String variable) {
        super(QPublications.class, forVariable(variable), "null", "publications");
        addMetadata();
    }

    public QPublications(String variable, String schema, String table) {
        super(QPublications.class, forVariable(variable), schema, table);
        addMetadata();
    }

    public QPublications(String variable, String schema) {
        super(QPublications.class, forVariable(variable), schema, "publications");
        addMetadata();
    }

    public QPublications(Path<? extends QPublications> path) {
        super(path.getType(), path.getMetadata(), "null", "publications");
        addMetadata();
    }

    public QPublications(PathMetadata metadata) {
        super(QPublications.class, metadata, "null", "publications");
        addMetadata();
    }

    public void addMetadata() {
        addMetadata(content, ColumnMetadata.named("content").withIndex(2).ofType(Types.VARCHAR).withSize(255));
        addMetadata(id, ColumnMetadata.named("id").withIndex(1).ofType(Types.BIGINT).withSize(19).notNull());
        addMetadata(isRead, ColumnMetadata.named("is_read").withIndex(3).ofType(Types.BIT).withSize(1).notNull());
        addMetadata(link, ColumnMetadata.named("link").withIndex(4).ofType(Types.VARCHAR).withSize(255));
        addMetadata(receiverId, ColumnMetadata.named("receiver_id").withIndex(5).ofType(Types.BIGINT).withSize(19));
        addMetadata(senderId, ColumnMetadata.named("sender_id").withIndex(6).ofType(Types.BIGINT).withSize(19));
    }

}

