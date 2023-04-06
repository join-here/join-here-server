package com.hongik.joinhere.domain.club.entity;

import com.hongik.joinhere.global.common.entity.TimeBaseEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Club extends TimeBaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50)
    private String name;

    @Column(length = 10)
    @Enumerated(EnumType.STRING)
    private Category category;

    @Column(length = 2)
    @Enumerated(EnumType.STRING)
    private Area area;

    @Column(name = "image_url", length = 2083)
    private String imageUrl;

    @Column(length = 1000)
    private String introduction;

    @Column(columnDefinition = "default 0")
    private Long view;

    public void updateName(String name) {
        this.name = name;
    }

    public void updateCategory(Category category) {
        this.category = category;
    }

    public void updateArea(Area area) {
        this.area = area;
    }

    public void updateImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void updateIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public void increaseView() {
        view++;
    }
}
