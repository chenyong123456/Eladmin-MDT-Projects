package me.zhengjie.modules.system.repository;

import me.zhengjie.modules.system.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author Zheng Jie
 * @date 2018-11-22
 */
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor {

    /**
     * 根据用户名查询用户，区分大小写
     * findByUsername
     * @param username
     * @return
     */
    @Query(value = "select * from EL_USER where 1=1 and binary username = ?1",nativeQuery = true)
    User findByUsername(String username);

    @Query(value = "select * from EL_USER where 1=1 and phone = ?1",nativeQuery = true)
    User findByPhone(String phone);
    User findByInvitationcode(String invitationcode);
    /**
     * 根据邮箱查询用户，区分大小写
     * findByEmail
     * @param email
     * @return
     */
    @Query(value = "select * from EL_USER where 1=1 and binary email = ?1",nativeQuery = true)
    User findByEmail(String email);

    /**
     * 修改密码
     * @param username
     * @param pass
     */
    @Modifying
    @Query(value = "update EL_USER set password = ?2 , last_password_reset_time = ?3 where username = ?1",nativeQuery = true)
    void updatePass(String username, String pass, Date lastPasswordResetTime);

    /**
     * 修改头像
     * @param username
     * @param url
     */
    @Modifying
    @Query(value = "update EL_USER set avatar = ?2 where username = ?1",nativeQuery = true)
    void updateAvatar(String username, String url);

    /**
     * 修改邮箱
     * @param username
     * @param email
     */
    @Modifying
    @Query(value = "update EL_USER set email = ?2 where username = ?1",nativeQuery = true)
    void updateEmail(String username, String email);

    
   
    @Query(value = "select * from EL_USER where 1=1 and id = ?",nativeQuery = true)
  	User findid(Long id);

    /**
     * 根据微信号查看用户
     * @param wx
     * @return
     */
    @Query(value=" select * from EL_USER where wx =?",nativeQuery = true)
    User selectWx(String wx);

   /**
   * 根据电话查询用户
   * @param phone
   * @return
   */
    @Query(value=" select * from EL_USER where phone =?",nativeQuery = true)
    User selectUserByPhone(String phone);

  /**
   * 根据电话号码修改微信
   * @param wx
   * @param phone
   * @return
   */
    @Transactional
    @Modifying
    @Query(value=" update EL_USER set wx =?1,wx_avatar=?2 where phone=?3",nativeQuery = true)
    int updateWxByPhone(String wx,String wxAvatar,String phone);

    @Transactional
    @Modifying
    @Query(value=" update EL_USER set invitationcode =?2 where wx=?1",nativeQuery = true)
    int updateWx(String wx,String invitationcode);
    
    @Transactional
    @Modifying
    @Query(value=" update EL_USER set wx =?1 where invitationcode=?2",nativeQuery = true)
	  int updateInvitationcode(String wx,String invitationcode);

    @Query(value=" select * from EL_USER where invitationcode =?",nativeQuery = true)
	  User selectinvitationcode(String invitationcode);

    @Query(value=" select * from EL_USER where username =?",nativeQuery = true)
   	User selectusername(String username);
   
    
    
}
