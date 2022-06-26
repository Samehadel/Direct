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
 * QKeywords is a Querydsl query type for QKeywords
 */
@Generated("com.querydsl.sql.codegen.MetaDataSerializer")
public class QKeywords extends com.querydsl.sql.RelationalPathBase<QKeywords> {

    private static final long serialVersionUID = 1820946949;

    public static final QKeywords keywords = new QKeywords("keywords");

    public final StringPath description = createString("description");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final com.querydsl.sql.PrimaryKey<QKeywords> primary = createPrimaryKey(id);

    public QKeywords(String variable) {
        super(QKeywords.class, forVariable(variable), "null", "keywords");
        addMetadata();
    }

    public QKeywords(String variable, String schema, String table) {
        super(QKeywords.class, forVariable(variable), schema, table);
        addMetadata();
    }

    public QKeywords(String variable, String schema) {
        super(QKeywords.class, forVariable(variable), schema, "keywords");
        addMetadata();
    }

    public QKeywords(Path<? extends QKeywords> path) {
        super(path.getType(), path.getMetadata(), "null", "keywords");
        addMetadata();
    }

    public QKeywords(PathMetadata metadata) {
        super(QKeywords.class, metadata, "null", "keywords");
        addMetadata();
    }

    public void addMetadata() {
        addMetadata(description, ColumnMetadata.named("description").withIndex(2).ofType(Types.VARCHAR).withSize(30).notNull());
        addMetadata(id, ColumnMetadata.named("id").withIndex(1).ofType(Types.INTEGER).withSize(10).notNull());
    }

}

