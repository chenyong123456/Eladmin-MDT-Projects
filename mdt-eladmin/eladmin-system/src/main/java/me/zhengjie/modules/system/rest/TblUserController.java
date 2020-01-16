package me.zhengjie.modules.system.rest;

import me.zhengjie.modules.system.service.TblUserService;
import me.zhengjie.modules.system.service.dto.TblUserQueryCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("uer")
public class TblUserController {
    @Autowired
    private TblUserService tblUserService;
    @GetMapping("/rs")
    public ResponseEntity findALL(TblUserQueryCriteria criteria, Pageable pageable){
            return new ResponseEntity(tblUserService.queryAll(criteria,pageable), HttpStatus.OK);
    }
}
