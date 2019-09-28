package com.zhijia.wechatserver.src.deviceserver.entity.wechat;

import javax.persistence.*;
import java.util.Date;

/**
 * @Author: WangJiaPeng
 * @Date: 2019/4/8 20:04
 * @Version 1.0
 */
@Entity
public class Audio {
    @Id
    @Column(name = "id",nullable = false,length = 10)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "file_path",nullable = false,length = 200)
    private String file_path;

    //1:服务器已保存;2:本地已保存;3:服务器已删除
    @Column(name = "status",nullable = false,length = 1)
    private int status;

    @Column(name = "create_date",nullable = false)
    private Date create_date;

    @Column(name = "update_date",nullable = false)
    private Date update_date;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFile_path() {
        return file_path;
    }

    public void setFile_path(String file_path) {
        this.file_path = file_path;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getCreate_date() {
        return create_date;
    }

    public void setCreate_date(Date create_date) {
        this.create_date = create_date;
    }

    public Date getUpdate_date() {
        return update_date;
    }

    public void setUpdate_date(Date update_date) {
        this.update_date = update_date;
    }
}
