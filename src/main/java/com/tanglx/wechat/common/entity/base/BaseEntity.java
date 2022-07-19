package com.tanglx.wechat.common.entity.base;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Describe
 * @Author tanglingxiao
 * @Date 2022-05-25
 */
@Data
@MappedSuperclass
public class BaseEntity implements Serializable {

    /**
     * 主键
     */
    @Id
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;

    /**
     * 是否启用
     */
    @TableField(fill = FieldFill.INSERT)
    @TableLogic
    private Boolean enable;
}
