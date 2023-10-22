package com.Rtech.Media.Modals;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;

@Data
public class PostWithLike {
    @Id
    private Integer post_id;
    private Integer user_id;
    private String createdDate;
    private String imageUrl;
    private String story;
    private String title;
    private Integer likes;
}
