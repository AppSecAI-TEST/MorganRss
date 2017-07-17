package com.morladim.morganrss.database.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.util.Date;

/**
 * <br>创建时间：2017/7/17.
 *
 * @author morladim
 */
@Entity
public class Category {

    @Id
    private Long id;

    private String name;

    private Date createAt;

    public Category(String name) {
        this.name = name;
        createAt = new Date();
    }

}
