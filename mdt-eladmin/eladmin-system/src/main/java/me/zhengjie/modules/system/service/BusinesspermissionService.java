/*
package me.zhengjie.modules.system.service;

import me.zhengjie.modules.system.domain.Businesspermission;
import me.zhengjie.modules.system.service.dto.BusinesspermissionDTO;
import me.zhengjie.modules.system.service.dto.CommonQueryCriteria;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

@CacheConfig(cacheNames = "Businesspermission")
public interface BusinesspermissionService {


    */
/**
     * get
     * @param id
     * @return
     *//*

    @Cacheable(key = "#p0")
    BusinesspermissionDTO findById(long id);

    */
/**
     * create
     * @param resources
     * @return
     *//*

    @CacheEvict(allEntries = true)
    BusinesspermissionDTO create(Businesspermission resources);

    */
/**
     * update
     * @param resources
     *//*

    @CacheEvict(allEntries = true)
    void update(Businesspermission resources);

    */
/**
     * delete
     * @param id
     *//*

    @CacheEvict(allEntries = true)
    void delete(Long id);

    */
/**
     * permission tree
     * @return
     *//*

    @Cacheable(key = "'tree'")
    Object getBusinesspermissionTree(List<Businesspermission> businesspermissions);

    */
/**
     * findByPid
     * @param pid
     * @return
     *//*

    @Cacheable(key = "'pid:'+#p0")
    List<Businesspermission> findByPid(long pid);

    */
/**
     * build Tree
     * @param businesspermissionDTOS
     * @return
     *//*

    @Cacheable(keyGenerator = "keyGenerator")
    Object buildTree(List<BusinesspermissionDTO> businesspermissionDTOS);

    */
/**
     * queryAll
     * @param criteria
     * @return
     *//*

    @Cacheable(keyGenerator = "keyGenerator")
    List<BusinesspermissionDTO> queryAll(CommonQueryCriteria criteria);
}
*/
