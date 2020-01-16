package me.zhengjie.modules.system.service.impl;

import me.zhengjie.modules.system.domain.AuditInfo;
import me.zhengjie.modules.system.domain.User;
import me.zhengjie.modules.system.repository.AuditInfoRepository;
import me.zhengjie.modules.system.service.AuditInfoService;
import me.zhengjie.modules.system.service.dto.AuditInfoQueryCriteria;
import me.zhengjie.modules.system.service.mapper.AuditInfoMapper;
import me.zhengjie.utils.PageUtil;
import me.zhengjie.utils.QueryHelp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class AuditInfoServiceImpl implements AuditInfoService {

    @Autowired
    private AuditInfoRepository auditInfoRepository;

    @Autowired
    private AuditInfoMapper auditInfoMapper;

    @Override
    public Object queryAll(AuditInfoQueryCriteria criteria, Pageable pageable) {
        Page<AuditInfo> page = auditInfoRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(auditInfoMapper::toDto));
    }
}
