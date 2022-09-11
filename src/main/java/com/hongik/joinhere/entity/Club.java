package com.hongik.joinhere.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Club {

    @Id @GeneratedValue
    private Long id;
    private String name;
    private String category;
    private String area;
    private byte[] image;
    private String introduction;
    private Long view;
    private Long scrap;
}
