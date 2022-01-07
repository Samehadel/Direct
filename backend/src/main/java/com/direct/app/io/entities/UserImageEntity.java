package com.direct.app.io.entities;

import javax.persistence.*;

@Entity(name = "users_images")
public class UserImageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "image_generator")
    @SequenceGenerator(name = "image_generator", sequenceName = "images_sequence", allocationSize = 1)
    private long id;

    @Column(name = "image_data", nullable = true)
    private byte [] imageData;

    @Column(name = "image_format", nullable = true)
    private String imageFormat;


    // Relationships
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_details_id", foreignKey = @ForeignKey(name = "fk_users_images_user_details_id"))
    private UserDetailsEntity userDetails;

    public UserImageEntity() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public byte[] getImageData() {
        return imageData;
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }

    public String getImageFormat() {
        return imageFormat;
    }

    public void setImageFormat(String imageFormat) {
        this.imageFormat = imageFormat;
    }

    public UserDetailsEntity getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(UserDetailsEntity userDetails) {
        this.userDetails = userDetails;
    }
}
