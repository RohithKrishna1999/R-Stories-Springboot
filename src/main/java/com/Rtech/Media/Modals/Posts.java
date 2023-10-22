package com.Rtech.Media.Modals;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "posts")
public class Posts {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "post_id")
    private Integer post_id;
    @Column(name = "user_id")
    private Integer user_id;
    @Column(name = "createdDate")
    private String createdDate;
    @Column(name = "imageUrl")
    private String imageUrl;
    @Column(name = "story")
    private String story;
    @Column(name = "title")
    private String title;
    @Column(name = "filetype")
    private String filetype;
}
