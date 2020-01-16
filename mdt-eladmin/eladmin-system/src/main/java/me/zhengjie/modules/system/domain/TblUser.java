package me.zhengjie.modules.system.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@Table(name="tbl_user")
public class TblUser {

  /*   `id` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `last_name` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,*/
         @Id// 标注用于声明一个实体类的属性映射为数据库的主键列。
         @GeneratedValue(strategy = GenerationType.IDENTITY)//用于标注主键的生成策略，通过strategy 属性指定。
         @NotNull(groups = User.Update.class)//被注释的元素不能为null
         private Integer id;

        @NotBlank
        @Column(unique = true)
        private String email;

        @NotBlank
        @Column(name="last_name")
        private String lastName;

}
