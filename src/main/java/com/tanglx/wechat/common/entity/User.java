package com.tanglx.wechat.common.entity;

import com.tanglx.wechat.common.entity.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @Describe
 * @Author tanglingxiao
 * @Date 2022-07-19
 */
@Entity
@Table(name = "user")
@ApiModel
@Data
public class User extends BaseEntity {

    @ApiModelProperty("name")
    private String name;
}
