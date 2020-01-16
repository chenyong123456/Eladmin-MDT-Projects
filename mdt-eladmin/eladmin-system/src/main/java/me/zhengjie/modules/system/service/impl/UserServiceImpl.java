package me.zhengjie.modules.system.service.impl;

import me.zhengjie.modules.monitor.service.RedisService;
import me.zhengjie.modules.system.domain.User;
import me.zhengjie.exception.EntityExistException;
import me.zhengjie.exception.EntityNotFoundException;
import me.zhengjie.modules.system.repository.UserRepository;
import me.zhengjie.modules.system.service.UserService;
import me.zhengjie.modules.system.service.dto.UserDTO;
import me.zhengjie.modules.system.service.dto.UserQueryCriteria;
import me.zhengjie.modules.system.service.mapper.UserMapper;
import me.zhengjie.utils.*;
import org.apache.catalina.security.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;
import java.util.Optional;

/**
 * @author Zheng Jie
 * @date 2018-11-23
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisService redisService;

    @Override
    public Object queryAll(UserQueryCriteria criteria, Pageable pageable) {
        Page<User> page = userRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        System.out.println("|----------------------查询所有用户--------------------------------|"+page.toString());
        return PageUtil.toPage(page.map(userMapper::toDto));
    }

    @Override
    public UserDTO findById(long id) {
        Optional<User> user = userRepository.findById(id);
        ValidationUtil.isNull(user,"User","id",id);
        return userMapper.toDto(user.get());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserDTO create(User resources) {

        if(userRepository.findByUsername(resources.getUsername())!=null){
            throw new EntityExistException(User.class,"username",resources.getUsername());
        }

        if(resources.getEmail()!=null&&!resources.getEmail().equals("")&&userRepository.findByEmail(resources.getEmail())!=null){
            throw new EntityExistException(User.class,"email",resources.getEmail());
        }


        String password = EncryptUtils.encryptPassword(resources.getPassword());
        resources.setPassword(password);
        // 默认密码 123456，此密码是加密后的字符
        //resources.setPassword("e10adc3949ba59abbe56e057f20f883e");
       // resources.setAvatar("https://i.loli.net/2019/04/04/5ca5b971e1548.jpeg");
        //设置默认头像，梁梁修改
        resources.setAvatar("https://i.loli.net/2019/09/09/PTWxLSnyz1NiGAU.jpg");
        resources.setCreator(SecurityUtils.getUsername());
        return userMapper.toDto(userRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(User resources) {
        Optional<User> userOptional = userRepository.findById(resources.getId());
        ValidationUtil.isNull(userOptional,"User","id",resources.getId());
        //根据id拿到用户信息，比较该用户与编辑后的用户信息
        User user = userOptional.get();

        User user1 = userRepository.findByUsername(user.getUsername());
        //如果email不为空，对比数据库 2020.1.8 修改
        if(user.getEmail()!=null&&!user.getEmail().equals("")){
          User user2 = userRepository.findByEmail(user.getEmail());
          if(user2!=null&&!user.getId().equals(user2.getId())){
            throw new EntityExistException(User.class,"email",resources.getEmail());
          }

        }
        System.out.println("user1================================================================================"+user1.toString());
        if(user1 !=null&&!user.getId().equals(user1.getId())){
            System.out.println("进了异常=========================================================================================");
            System.out.println("进了异常=========================================================================================");
            System.out.println("进了异常=========================================================================================");
            System.out.println("进了异常=========================================================================================");
            throw new EntityExistException(User.class,"username",resources.getUsername());
        }


        Date date = new Date();
        if (!resources.getPassword().equals((Long.toString(user1.getCreateTime().getTime()))+""+"1")){
            System.out.println("|-----------修改用户名-----------|");
            System.out.println("进入方法");
            System.out.println("进入方法");
            System.out.println("进入方法");
            System.out.println("进入方法");
            System.out.println("进入方法");
            System.out.println("进入方法");
            System.out.println("进入方法");
            user.setPassword(EncryptUtils.encryptPassword(resources.getPassword()));
            user.setLastPasswordResetTime(date);
        }

        // 如果用户的角色改变了，需要手动清理下缓存
        if (!resources.getRoles().equals(user.getRoles())) {
            String key = "role::loadPermissionByUser:" + user.getUsername();
            redisService.delete(key);
            key = "role::findByUsers_Id:" + user.getId();
            redisService.delete(key);
        }
        UserDetails userDetails = SecurityUtils.getUserDetails();
        user.setUsername(resources.getUsername());
        user.setEmail(resources.getEmail());
        user.setEnabled(resources.getEnabled());
        user.setRoles(resources.getRoles());
        user.setDept(resources.getDept());
        user.setJob(resources.getJob());
        user.setPhone(resources.getPhone());
        user.setPosition(resources.getPosition());
        user.setModifier(userDetails.getUsername());
        user.setModiftime(date);
        user.setOtherContacts(resources.getOtherContacts());
        user.setSex(resources.getSex());
        user.setAge(resources.getAge());
        userRepository.save(user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserDTO findByName(String userName) {
        User user = null;
        if(ValidationUtil.isEmail(userName)){
            user = userRepository.findByEmail(userName);
        } else {
            user = userRepository.findByUsername(userName);
        }
        if (user == null) {
            throw new EntityNotFoundException(User.class, "name", userName);
        } else {
            return userMapper.toDto(user);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updatePass(String username, String pass) {
        userRepository.updatePass(username,pass,new Date());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateAvatar(String username, String url) {
        userRepository.updateAvatar(username,url);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateEmail(String username, String email) {
        userRepository.updateEmail(username,email);
    }

	@Override
	public User findid(Long id) {
		// TODO Auto-generated method stub
		return userRepository.findid(id);
	}

	@Override
	public User selectWx(String wx) {
		// TODO Auto-generated method stub
		return userRepository.selectWx(wx);
	}

	@Override
	public int updateWx(String wx,String invitationcode) {
		// TODO Auto-generated method stub
		return userRepository.updateWx(wx,invitationcode);
	}

  @Override
  public User selectUserByPhone(String phone) {
    return userRepository.selectUserByPhone(phone);
  }

  @Override
	public int updateByPrimaryKey(String wx,String invitationcode) {
		// TODO Auto-generated method stub
		return userRepository.updateInvitationcode(wx,invitationcode);
	}

	@Override
	public User selectinvitationcode(String invitationcode) {
		// TODO Auto-generated method stub
		return userRepository.selectinvitationcode(invitationcode);
	}

	@Override
	public User selectusername(String username) {
		// TODO Auto-generated method stub
		return userRepository.selectusername(username);
	}

  @Override
  public int updateWxByPhone(String wx,String wxAvatar,String phone) {
    return userRepository.updateWxByPhone(wx,wxAvatar,phone);
  }


}
