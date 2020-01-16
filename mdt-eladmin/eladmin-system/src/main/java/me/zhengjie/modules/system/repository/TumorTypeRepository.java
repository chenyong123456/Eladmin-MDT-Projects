package me.zhengjie.modules.system.repository;

import me.zhengjie.modules.system.domain.DepartmentWeightSetting;
import me.zhengjie.modules.system.domain.TumorType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author Liang Yuxuan
 * @date 2020-01-06
 */
public interface TumorTypeRepository extends JpaRepository<TumorType, Long>, JpaSpecificationExecutor {
}
