package com.direct.app.io.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "users_images")
@Data
@NoArgsConstructor
public class UserImageEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "image_generator")
    @SequenceGenerator(name = "image_generator", sequenceName = "images_sequence", allocationSize = 1)
    private long id;

    @Column(name = "image_data", nullable = true)
    private Byte [] imageData;

    @Column(name = "image_format", nullable = true)
    private String imageFormat;


    // Relationships
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_details_id", foreignKey = @ForeignKey(name = "fk_users_images_user_details_id"))
    private UserDetailsEntity userDetails;
}
