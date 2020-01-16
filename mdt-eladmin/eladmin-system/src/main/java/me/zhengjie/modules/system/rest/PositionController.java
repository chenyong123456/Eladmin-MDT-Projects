package me.zhengjie.modules.system.rest;

import me.zhengjie.aop.log.Log;
import me.zhengjie.config.DataScope;
import me.zhengjie.modules.system.service.DeptService;
import me.zhengjie.modules.system.service.PositionService;
import me.zhengjie.modules.system.service.dto.DeptDTO;
import me.zhengjie.modules.system.service.dto.DeptQueryCriteria;
import me.zhengjie.modules.system.service.dto.PositionQueryCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 查询职称
 */
@RestController
@RequestMapping("api")
public class PositionController {


    @Autowired
    private PositionService positionService;

    @Autowired
    private DataScope dataScope;

    private static final String ENTITY_NAME = "dept";

    @Log("查询职称")
    @GetMapping(value = "/positions/all")
    public ResponseEntity getDepts(Pageable pageable){
      System.out.println("|------------我所有的职称-----------|"+positionService.queryAll(pageable));
      return new ResponseEntity(positionService.queryAll(pageable), HttpStatus.OK);
    }

  /**
   * 返回全部的角色，新增用户时下拉选择
   * @return
   */
//  @GetMapping(value = "/roles/all")
//  @PreAuthorize("hasAnyRole('ADMIN','ROLES_ALL','USER_ALL','USER_CREATE','USER_EDIT')")
//  public ResponseEntity getAll(@PageableDefault(value = 2000, sort = {"level"}, direction = Sort.Direction.ASC) Pageable pageable){
//    return new ResponseEntity(roleService.queryAll(pageable),HttpStatus.OK);
//  }


}
