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

    @Column(name = "image_name", nullable = true)
    private String imageName;

    @Column(name = "image_url", nullable = true)
    private String imageUrl;

    @Column(name = "image_format", nullable = true)
    private String imageFormat;

}
