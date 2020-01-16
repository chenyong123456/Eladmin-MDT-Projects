package me.zhengjie.modules.system.service.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import me.zhengjie.annotation.Query;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class PositionQueryCriteria implements Serializable {

  @Query(type = Query.Type.INNER_LIKE)
  private String name;

  @Query
  private Boolean enabled;

}
