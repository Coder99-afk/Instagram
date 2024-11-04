package com.wbd.Instagram.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @UuidGenerator
    @Column(updatable = false, nullable = false)
    private long id;
    private String imageName;
    private String imageType;
    @Lob
    private byte[] imageData;
    @ManyToOne
    private Post post;
}
