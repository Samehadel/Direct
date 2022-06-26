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
 * QUserDetails is a Querydsl query type for QUserDetails
 */
@Generated("com.querydsl.sql.codegen.MetaDataSerializer")
public class QUserDetails extends com.querydsl.sql.RelationalPathBase<QUserDetails> {

    private static final long serialVersionUID = 652222396;

    public static final QUserDetails userDetails = new QUserDetails("user_details");

    public final StringPath bio = createString("bio");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath majorField = createString("majorField");

    public final StringPath phone = createString("phone");

    public final StringPath professionalTitle = createString("professionalTitle");

    public final StringPath profileImageUrl = createString("profileImageUrl");

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public final NumberPath<Long> userImageId = createNumber("userImageId", Long.class);

    public final com.querydsl.sql.PrimaryKey<QUserDetails> primary = createPrimaryKey(id);

    public QUserDetails(String variable) {
        super(QUserDetails.class, forVariable(variable), "null", "user_details");
        addMetadata();
    }

    public QUserDetails(String variable, String schema, String table) {
        super(QUserDetails.class, forVariable(variable), schema, table);
        addMetadata();
    }

    public QUserDetails(String variable, String schema) {
        super(QUserDetails.class, forVariable(variable), schema, "user_details");
        addMetadata();
    }

    public QUserDetails(Path<? extends QUserDetails> path) {
        super(path.getType(), path.getMetadata(), "null", "user_details");
        addMetadata();
    }

    public QUserDetails(PathMetadata metadata) {
        super(QUserDetails.class, metadata, "null", "user_details");
        addMetadata();
    }

    public void addMetadata() {
        addMetadata(bio, ColumnMetadata.named("bio").withIndex(2).ofType(Types.VARCHAR).withSize(500));
        addMetadata(id, ColumnMetadata.named("id").withIndex(1).ofType(Types.BIGINT).withSize(19).notNull());
        addMetadata(majorField, ColumnMetadata.named("major_field").withIndex(3).ofType(Types.VARCHAR).withSize(50));
        addMetadata(phone, ColumnMetadata.named("phone").withIndex(4).ofType(Types.VARCHAR).withSize(14));
        addMetadata(professionalTitle, ColumnMetadata.named("professional_title").withIndex(5).ofType(Types.VARCHAR).withSize(50));
        addMetadata(profileImageUrl, ColumnMetadata.named("profile_image_url").withIndex(7).ofType(Types.VARCHAR).withSize(255));
        addMetadata(userId, ColumnMetadata.named("user_id").withIndex(6).ofType(Types.BIGINT).withSize(19));
        addMetadata(userImageId, ColumnMetadata.named("user_image_id").withIndex(8).ofType(Types.BIGINT).withSize(19));
    }

}

