package me.zhengjie.modules.system.repository;

import me.zhengjie.modules.system.domain.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/**
* @author Zheng Jie
* @date 2019-03-29
*/
public interface JobRepository extends JpaRepository<Job, Long>, JpaSpecificationExecutor {
  /**
   * findByUsername
   * @param name
   * @return
   */
  @Query(value = "select * from EL_JOB where 1=1 and binary name = ?1",nativeQuery = true)
  Job findByJobName(String name);

}
