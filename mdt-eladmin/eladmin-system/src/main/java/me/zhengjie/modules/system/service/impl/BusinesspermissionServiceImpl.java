/*
package me.zhengjie.modules.system.service.impl;

import me.zhengjie.exception.BadRequestException;
import me.zhengjie.exception.EntityExistException;
import me.zhengjie.modules.system.domain.Businesspermission;
import me.zhengjie.modules.system.repository.BusinesspermissionRepository;
import me.zhengjie.modules.system.service.BusinesspermissionService;
import me.zhengjie.modules.system.service.dto.BusinesspermissionDTO;
import me.zhengjie.modules.system.service.dto.CommonQueryCriteria;
import me.zhengjie.modules.system.service.mapper.BusinesspermissionMapper;
import me.zhengjie.utils.QueryHelp;
import me.zhengjie.utils.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = exception.class)
public class BusinesspermissionServiceImpl implements BusinesspermissionService {
   @Autowired
   private BusinesspermissionRepository businesspermissionRepository;

   @Autowired
   private BusinesspermissionMapper businesspermissionMapper;


    @Override
    public BusinesspermissionDTO findById(long id) {
        Optional<Businesspermission> businesspermission=businesspermissionRepository.findById(id);
        ValidationUtil.isNull(businesspermission,"Businesspermission","id",id);
        return businesspermissionMapper.toDto(businesspermission.get());
    }

    @Override
    @Transactional(rollbackFor = exception.class)
    public BusinesspermissionDTO create(Businesspermission resources) {
        if(businesspermissionRepository.findByName(resources.getName()) != null){
            throw new EntityExistException(Businesspermission.class,"name",resources.getName());
        }
        return businesspermissionMapper.toDto(businesspermissionRepository.save(resources));
    }

    @Override
    public void update(Businesspermission resources) {
        Optional<Businesspermission> optionalBusinesspermission = businesspermissionRepository.findById(resources.getId());
        if(resources.getId().equals(resources.getPid())) {
            throw new BadRequestException("上级不能为自己");
        }
        ValidationUtil.isNull(optionalBusinesspermission,"Businesspermission","id",resources.getId());

        Businesspermission businesspermission = optionalBusinesspermission.get();

        Businesspermission businesspermission1 = businesspermissionRepository.findByName(resources.getName());

        if(businesspermission1 != null && !businesspermission1.getId().equals(businesspermission1.getId())){
            throw new EntityExistException(Businesspermission.class,"name",resources.getName());
        }

        businesspermission.setName(resources.getName());
        businesspermission.setAlias(resources.getAlias());
        businesspermission.setPid(resources.getPid());
        businesspermissionRepository.save(businesspermission);
    }

    @Override
    @Transactional(rollbackFor = exception.class)
    public void delete(Long id) {
List<Businesspermission> businesspermissionList=businesspermissionRepository.findByPid(id);
for(Businesspermission businesspermission:businesspermissionList){
    businesspermissionRepository.delete(businesspermission);
}
businesspermissionRepository.deleteById(id);
    }

    @Override
    public Object getBusinesspermissionTree(List<Businesspermission> businesspermissions) {
        List<Map<String,Object>> list = new LinkedList<>();
        businesspermissions.forEach(businesspermission -> {
                    if (businesspermission!=null){
                        List<Businesspermission> businesspermissionList = businesspermissionRepository.findByPid(businesspermission.getId());
                        Map<String,Object> map = new HashMap<>();
                        map.put("id",businesspermission.getId());
                        map.put("label",businesspermission.getAlias());
                        if(businesspermissionList!=null && businesspermissionList.size()!=0){
                            map.put("children",getBusinesspermissionTree(businesspermissionList));
                        }
                        list.add(map);
                    }
                }
        );
        return list;
    }

    @Override
    public List<Businesspermission> findByPid(long pid) {
        return businesspermissionRepository.findByPid(pid);

    }

    @Override
    public Object buildTree(List<BusinesspermissionDTO> businesspermissionDTOS) {
        List<BusinesspermissionDTO> trees = new ArrayList<BusinesspermissionDTO>();

        for (BusinesspermissionDTO businesspermissionDTO : businesspermissionDTOS) {

            if ("0".equals(businesspermissionDTO.getPid().toString())) {
                trees.add(businesspermissionDTO);
            }

            for (BusinesspermissionDTO it : businesspermissionDTOS) {
                if (it.getPid().equals(businesspermissionDTO.getId())) {
                    if (businesspermissionDTO.getChildren() == null) {
                        businesspermissionDTO.setChildren(new ArrayList<BusinesspermissionDTO>());
                    }
                    businesspermissionDTO.getChildren().add(it);
                }
            }
        }

        Integer totalElements = businesspermissionDTOS!=null?businesspermissionDTOS.size():0;

        Map map = new HashMap();
        map.put("content",trees.size() == 0?businesspermissionDTOS:trees);
        map.put("totalElements",totalElements);
        return map;
    }


    @Override
    public List<BusinesspermissionDTO> queryAll(CommonQueryCriteria criteria) {
        return businesspermissionMapper.toDto(businesspermissionRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));

    }
}
*/
