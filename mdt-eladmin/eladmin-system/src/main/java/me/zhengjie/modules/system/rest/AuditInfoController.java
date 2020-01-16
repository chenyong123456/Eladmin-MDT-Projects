package me.zhengjie.modules.system.rest;

import me.zhengjie.modules.system.service.AuditInfoService;
import me.zhengjie.modules.system.service.dto.AuditInfoQueryCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("audit")
public class AuditInfoController {
    @Autowired
    private AuditInfoService auditInfoService;

    @GetMapping(value = "/aa")
    public Object getAudit(AuditInfoQueryCriteria criteria, Pageable pageable){
       /* return HttpClientUtil.doGet("http://192.168.50.118:8088/pathway_id");*/
         return new ResponseEntity(auditInfoService.queryAll(criteria,pageable), HttpStatus.OK);

    }
}
