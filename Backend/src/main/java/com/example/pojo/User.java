package com.example.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.Data;

import java.io.Serializable;

/**
 * @TableName news_user
 */
@Data
public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId
    private Integer uid;
    private String username;
    private String userPwd;
    private String nickName;
    @Version
    private Integer version;
    private Integer isDeleted;
}