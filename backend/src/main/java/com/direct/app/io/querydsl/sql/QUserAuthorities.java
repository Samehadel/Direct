package com.direct.app.io.querydsl.sql;

import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.sql.ColumnMetadata;

import javax.annotation.Generated;
import java.sql.Types;

import static com.querydsl.core.types.PathMetadataFactory.forVariable;




/**
 * QUserAuthorities is a Querydsl query type for QUserAuthorities
 */
@Generated("com.querydsl.sql.codegen.MetaDataSerializer")
public class QUserAuthorities extends com.querydsl.sql.RelationalPathBase<QUserAuthorities> {

    private static final long serialVersionUID = 443478939;

    public static final QUserAuthorities userAuthorities = new QUserAuthorities("user_authorities");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath role = createString("role");

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public final com.querydsl.sql.PrimaryKey<QUserAuthorities> primary = createPrimaryKey(id);

    public QUserAuthorities(String variable) {
        super(QUserAuthorities.class, forVariable(variable), "null", "user_authorities");
        addMetadata();
    }

    public QUserAuthorities(String variable, String schema, String table) {
        super(QUserAuthorities.class, forVariable(variable), schema, table);
        addMetadata();
    }

    public QUserAuthorities(String variable, String schema) {
        super(QUserAuthorities.class, forVariable(variable), schema, "user_authorities");
        addMetadata();
    }

    public QUserAuthorities(Path<? extends QUserAuthorities> path) {
        super(path.getType(), path.getMetadata(), "null", "user_authorities");
        addMetadata();
    }

    public QUserAuthorities(PathMetadata metadata) {
        super(QUserAuthorities.class, metadata, "null", "user_authorities");
        addMetadata();
    }

    public void addMetadata() {
        addMetadata(id, ColumnMetadata.named("id").withIndex(1).ofType(Types.BIGINT).withSize(19).notNull());
        addMetadata(role, ColumnMetadata.named("role").withIndex(2).ofType(Types.VARCHAR).withSize(50).notNull());
        addMetadata(userId, ColumnMetadata.named("user_id").withIndex(3).ofType(Types.BIGINT).withSize(19));
    }

}

