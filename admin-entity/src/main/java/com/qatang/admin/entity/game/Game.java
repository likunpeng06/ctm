package com.qatang.admin.entity.game;

import com.qatang.core.entity.AbstractEntity;

import javax.persistence.*;
import java.util.Date;

/**
 * @author qatang
 * @since 2015-01-26 09:51
 */
@Entity
@Table(name = "a_game", indexes = {
        @Index(name = "idx_created_time", columnList = "created_time")
})
public class Game extends AbstractEntity {
    @Transient
    private static final long serialVersionUID = 5382533982572676099L;

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @Column(name = "icon_url")
    private String iconUrl;

    @Column(name = "large_image_url")
    private String largeImgUrl;

    @Column(name = "game_url")
    private String gameUrl;

    private String description;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_time", updatable = false, nullable = false)
    private Date createdTime = new Date();

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_time")
    private Date updatedTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getLargeImgUrl() {
        return largeImgUrl;
    }

    public void setLargeImgUrl(String largeImgUrl) {
        this.largeImgUrl = largeImgUrl;
    }

    public String getGameUrl() {
        return gameUrl;
    }

    public void setGameUrl(String gameUrl) {
        this.gameUrl = gameUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }
}
