package me.zhengjie.modules.system.service.dto;

import lombok.Data;
import me.zhengjie.annotation.Query;

import java.io.Serializable;

@Data
public class TblUserQueryCriteria implements Serializable {
    @Query
    private Integer id;

    @Query(type = Query.Type.INNER_LIKE)
    private String email;

    @Query(type = Query.Type.INNER_LIKE)
    private String lastName;
}
