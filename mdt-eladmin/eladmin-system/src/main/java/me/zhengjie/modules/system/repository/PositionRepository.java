package me.zhengjie.modules.system.repository;

import me.zhengjie.modules.system.domain.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
/**
 * @author Liang Yuxuan
 * @date 2019-11-29
 */
public interface PositionRepository extends JpaRepository<Position, Long>, JpaSpecificationExecutor {
}
