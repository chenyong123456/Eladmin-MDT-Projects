package me.zhengjie.modules.system.service;

import me.zhengjie.modules.system.domain.Position;
import me.zhengjie.modules.system.service.dto.PositionDTO;
import me.zhengjie.modules.system.service.dto.PositionQueryCriteria;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
/**
 * @author Liang Yuxuan
 * @date 2019-11-29
 */
@CacheConfig(cacheNames = "position")
public interface PositionService {

  /**
   * findById
   * @param id
   * @return
   */
  @Cacheable(key = "#p0")
  PositionDTO findById(Long id);

  /**
   * create
   * @param resources
   * @return
   */
  @CacheEvict(allEntries = true)
  PositionDTO create(Position resources);

  /**
   * update
   * @param resources
   */
  @CacheEvict(allEntries = true)
  PositionDTO update(Position resources);

  /**
   * delete
   * @param id
   */
  @CacheEvict(allEntries = true)
  PositionDTO delete(Long id);

  /**
   * queryAll
   * @param criteria
   * @param pageable
   * @return
   */
  Object queryAll(PositionQueryCriteria criteria, Pageable pageable);
  /**
   * queryAll
   * @param pageable
   * @return
   */
  Object queryAll(Pageable pageable);
}
