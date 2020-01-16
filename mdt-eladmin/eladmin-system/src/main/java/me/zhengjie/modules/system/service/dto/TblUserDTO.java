package me.zhengjie.modules.system.service.dto;

import lombok.Data;

import java.io.Serializable;
@Data
public class TblUserDTO implements Serializable {

    private Integer id;


    private String email;


    private String lastName;
}
