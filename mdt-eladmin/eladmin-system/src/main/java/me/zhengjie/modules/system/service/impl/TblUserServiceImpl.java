package me.zhengjie.modules.system.service.impl;

import me.zhengjie.modules.system.domain.TblUser;
import me.zhengjie.modules.system.repository.TblUserRepository;
import me.zhengjie.modules.system.service.TblUserService;
import me.zhengjie.modules.system.service.dto.TblUserQueryCriteria;
import me.zhengjie.modules.system.service.mapper.TblUserMapper;
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
public class TblUserServiceImpl implements TblUserService {
    @Autowired
    private TblUserRepository tblUserRepository;

@Autowired
private TblUserMapper tblUserMapper;
    @Override
    public Object queryAll(TblUserQueryCriteria criteria, Pageable pageable) {
        Page<TblUser> page=tblUserRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder), (org.springframework.data.domain.Pageable) pageable);
        return PageUtil.toPage(page.map(tblUserMapper::toDto));
    }
}
