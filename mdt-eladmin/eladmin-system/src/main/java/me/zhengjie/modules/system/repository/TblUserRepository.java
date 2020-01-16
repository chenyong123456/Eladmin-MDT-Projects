package me.zhengjie.modules.system.repository;

import me.zhengjie.modules.system.domain.TblUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TblUserRepository extends JpaRepository<TblUser,Integer> , JpaSpecificationExecutor {
}
