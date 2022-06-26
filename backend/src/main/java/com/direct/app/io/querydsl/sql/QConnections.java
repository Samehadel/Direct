package com.direct.app.io.querydsl.sql;

import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.sql.ColumnMetadata;

import javax.annotation.Generated;
import java.sql.Types;

import static com.querydsl.core.types.PathMetadataFactory.forVariable;




/**
 * QConnections is a Querydsl query type for QConnections
 */
@Generated("com.querydsl.sql.codegen.MetaDataSerializer")
public class QConnections extends com.querydsl.sql.RelationalPathBase<QConnections> {

    private static final long serialVersionUID = 1120971034;

    public static final QConnections connections = new QConnections("connections");

    public final NumberPath<Long> firstUserId = createNumber("firstUserId", Long.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> secondUserId = createNumber("secondUserId", Long.class);

    public final com.querydsl.sql.PrimaryKey<QConnections> primary = createPrimaryKey(id);

    public QConnections(String variable) {
        super(QConnections.class, forVariable(variable), "null", "connections");
        addMetadata();
    }

    public QConnections(String variable, String schema, String table) {
        super(QConnections.class, forVariable(variable), schema, table);
        addMetadata();
    }

    public QConnections(String variable, String schema) {
        super(QConnections.class, forVariable(variable), schema, "connections");
        addMetadata();
    }

    public QConnections(Path<? extends QConnections> path) {
        super(path.getType(), path.getMetadata(), "null", "connections");
        addMetadata();
    }

    public QConnections(PathMetadata metadata) {
        super(QConnections.class, metadata, "null", "connections");
        addMetadata();
    }

    public void addMetadata() {
        addMetadata(firstUserId, ColumnMetadata.named("first_user_id").withIndex(2).ofType(Types.BIGINT).withSize(19));
        addMetadata(id, ColumnMetadata.named("id").withIndex(1).ofType(Types.BIGINT).withSize(19).notNull());
        addMetadata(secondUserId, ColumnMetadata.named("second_user_id").withIndex(3).ofType(Types.BIGINT).withSize(19));
    }

}

