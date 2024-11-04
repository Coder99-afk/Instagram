package com.wbd.Instagram.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.util.List;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @UuidGenerator
    @Column(updatable = false, nullable = false)
    private long id;
    private String caption;
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Image> images;
    @OneToMany(mappedBy = "post")
    private List<Comment> comments;
}
