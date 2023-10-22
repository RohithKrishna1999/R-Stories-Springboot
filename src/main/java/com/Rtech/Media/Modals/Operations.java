package com.Rtech.Media.Modals;


import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "operations")
public class Operations {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "sno")
    private Integer sno;
    @Column(name = "user_id")
    private Integer user_id;
    @Column(name = "post_id")
    private Integer post_id;
    @Column(name = "type")
    private String type;
    @Column(name = "comments")
    private String comments;
}
