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
 * QUsers is a Querydsl query type for QUsers
 */
@Generated("com.querydsl.sql.codegen.MetaDataSerializer")
public class QUsers extends com.querydsl.sql.RelationalPathBase<QUsers> {

    private static final long serialVersionUID = -194162291;

    public static final QUsers users = new QUsers("users");

    public final BooleanPath emailVerificationStatus = createBoolean("emailVerificationStatus");

    public final StringPath encryptedPassword = createString("encryptedPassword");

    public final StringPath firstName = createString("firstName");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath lastName = createString("lastName");

    public final NumberPath<Long> userDetailsId = createNumber("userDetailsId", Long.class);

    public final StringPath username = createString("username");

    public final StringPath virtualUserId = createString("virtualUserId");

    public final com.querydsl.sql.PrimaryKey<QUsers> primary = createPrimaryKey(id);

    public QUsers(String variable) {
        super(QUsers.class, forVariable(variable), "null", "users");
        addMetadata();
    }

    public QUsers(String variable, String schema, String table) {
        super(QUsers.class, forVariable(variable), schema, table);
        addMetadata();
    }

    public QUsers(String variable, String schema) {
        super(QUsers.class, forVariable(variable), schema, "users");
        addMetadata();
    }

    public QUsers(Path<? extends QUsers> path) {
        super(path.getType(), path.getMetadata(), "null", "users");
        addMetadata();
    }

    public QUsers(PathMetadata metadata) {
        super(QUsers.class, metadata, "null", "users");
        addMetadata();
    }

    public void addMetadata() {
        addMetadata(emailVerificationStatus, ColumnMetadata.named("email_verification_status").withIndex(2).ofType(Types.BIT).withSize(1).notNull());
        addMetadata(encryptedPassword, ColumnMetadata.named("encrypted_password").withIndex(3).ofType(Types.VARCHAR).withSize(500).notNull());
        addMetadata(firstName, ColumnMetadata.named("first_name").withIndex(4).ofType(Types.VARCHAR).withSize(50).notNull());
        addMetadata(id, ColumnMetadata.named("id").withIndex(1).ofType(Types.BIGINT).withSize(19).notNull());
        addMetadata(lastName, ColumnMetadata.named("last_name").withIndex(5).ofType(Types.VARCHAR).withSize(50).notNull());
        addMetadata(userDetailsId, ColumnMetadata.named("user_details_id").withIndex(8).ofType(Types.BIGINT).withSize(19));
        addMetadata(username, ColumnMetadata.named("username").withIndex(6).ofType(Types.VARCHAR).withSize(150).notNull());
        addMetadata(virtualUserId, ColumnMetadata.named("virtual_user_id").withIndex(7).ofType(Types.VARCHAR).withSize(255));
    }

}

