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
 * QUsersImages is a Querydsl query type for QUsersImages
 */
@Generated("com.querydsl.sql.codegen.MetaDataSerializer")
public class QUsersImages extends com.querydsl.sql.RelationalPathBase<QUsersImages> {

    private static final long serialVersionUID = -1392860571;

    public static final QUsersImages usersImages = new QUsersImages("users_images");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath imageFormat = createString("imageFormat");

    public final StringPath imageName = createString("imageName");

    public final StringPath imageUrl = createString("imageUrl");

    public final com.querydsl.sql.PrimaryKey<QUsersImages> primary = createPrimaryKey(id);

    public QUsersImages(String variable) {
        super(QUsersImages.class, forVariable(variable), "null", "users_images");
        addMetadata();
    }

    public QUsersImages(String variable, String schema, String table) {
        super(QUsersImages.class, forVariable(variable), schema, table);
        addMetadata();
    }

    public QUsersImages(String variable, String schema) {
        super(QUsersImages.class, forVariable(variable), schema, "users_images");
        addMetadata();
    }

    public QUsersImages(Path<? extends QUsersImages> path) {
        super(path.getType(), path.getMetadata(), "null", "users_images");
        addMetadata();
    }

    public QUsersImages(PathMetadata metadata) {
        super(QUsersImages.class, metadata, "null", "users_images");
        addMetadata();
    }

    public void addMetadata() {
        addMetadata(id, ColumnMetadata.named("id").withIndex(1).ofType(Types.BIGINT).withSize(19).notNull());
        addMetadata(imageFormat, ColumnMetadata.named("image_format").withIndex(2).ofType(Types.VARCHAR).withSize(255));
        addMetadata(imageName, ColumnMetadata.named("image_name").withIndex(3).ofType(Types.VARCHAR).withSize(255));
        addMetadata(imageUrl, ColumnMetadata.named("image_url").withIndex(4).ofType(Types.VARCHAR).withSize(255));
    }

}

