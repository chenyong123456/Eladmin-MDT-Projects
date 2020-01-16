//package me.zhengjie.modules.system.rest;
//
//import me.zhengjie.aop.log.Log;
//import me.zhengjie.exception.BadRequestException;
//import me.zhengjie.modules.system.domain.Businesspermission;
//import me.zhengjie.modules.system.service.BusinesspermissionService;
//import me.zhengjie.modules.system.service.dto.BusinesspermissionDTO;
//import me.zhengjie.modules.system.service.dto.CommonQueryCriteria;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("api")
//public class BusinesspermissionController {
//
//    @Autowired
//    private BusinesspermissionService businesspermissionService;
//
//    private static final String ENTITY_NAME = "bussinesspermission";
//
//
//
//     /**
//     * 返回全部的权限，新增角色时下拉选择
//     * @return
//     */
//
//    @GetMapping(value = "/businesspermissions/tree")
//    @PreAuthorize("hasAnyRole('ADMIN','STATEMENT_SELECT','CP_SELECT','DATA_SUBMIT','CP_EDIT','CP_AUDIT')")
//    public ResponseEntity getTree(){
//        return new ResponseEntity(businesspermissionService.getBusinesspermissionTree(businesspermissionService.findByPid(0L)), HttpStatus.OK);
//    }
//
//    @Log("查询权限")
//    @GetMapping(value = "/businesspermissions")
//    @PreAuthorize("hasAnyRole('ADMIN','STATEMENT_SELECT','CP_SELECT','DATA_SUBMIT','CP_EDIT','CP_AUDIT')")
//    public ResponseEntity getBusinesspermissions(CommonQueryCriteria criteria){
//        List<BusinesspermissionDTO> businesspermissionDTOS = businesspermissionService.queryAll(criteria);
//        return new ResponseEntity(businesspermissionService.buildTree(businesspermissionDTOS),HttpStatus.OK);
//    }
//
//    @Log("新增权限")
//    @PostMapping(value = "/businesspermissions")
//    @PreAuthorize("hasAnyRole('ADMIN','STATEMENT_SELECT','CP_SELECT','DATA_SUBMIT','CP_EDIT','CP_AUDIT')")
//    public ResponseEntity create(@Validated @RequestBody Businesspermission resources){
//        if (resources.getId() != null) {
//            throw new BadRequestException("A new "+ ENTITY_NAME +" cannot already have an ID");
//        }
//        return new ResponseEntity(businesspermissionService.create(resources),HttpStatus.CREATED);
//    }
//
//    @Log("修改权限")
//    @PutMapping(value = "/businesspermissions")
//    @PreAuthorize("hasAnyRole('ADMIN','STATEMENT_SELECT','CP_SELECT','DATA_SUBMIT','CP_EDIT','CP_AUDIT')")
//    public ResponseEntity update(@Validated(Businesspermission.Update.class) @RequestBody Businesspermission resources){
//        businesspermissionService.update(resources);
//        return new ResponseEntity(HttpStatus.NO_CONTENT);
//    }
//
//    @Log("删除权限")
//    @DeleteMapping(value = "/businesspermissions/{id}")
//    @PreAuthorize("hasAnyRole('ADMIN','STATEMENT_SELECT','CP_SELECT','DATA_SUBMIT','CP_EDIT','CP_AUDIT')")
//    public ResponseEntity delete(@PathVariable Long id){
//        businesspermissionService.delete(id);
//        return new ResponseEntity(HttpStatus.OK);
//    }
//}
//
