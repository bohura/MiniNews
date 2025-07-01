package com.example.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.Data;

import java.io.Serializable;

/**
 * @TableName news_type
 */
@Data
public class Type implements Serializable {
    private static final long serialVersionUID = 1L;
    @TableId
    private Integer tid;
    private String tname;
    @Version
    private Integer version;
    private Integer isDeleted;
}