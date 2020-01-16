package me.zhengjie.modules.system.rest;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import me.zhengjie.modules.system.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import me.zhengjie.aop.log.Log;
import me.zhengjie.config.DataScope;
import me.zhengjie.domain.Picture;
import me.zhengjie.domain.VerificationCode;
import me.zhengjie.exception.BadRequestException;
import me.zhengjie.modules.monitor.service.RedisService;
import me.zhengjie.modules.system.domain.User;
import me.zhengjie.modules.system.domain.vo.UserPassVo;
import me.zhengjie.modules.system.service.DeptService;
import me.zhengjie.modules.system.service.RoleService;
import me.zhengjie.modules.system.service.UserService;
import me.zhengjie.modules.system.service.dto.RoleSmallDTO;
import me.zhengjie.modules.system.service.dto.UserQueryCriteria;
import me.zhengjie.service.PictureService;
import me.zhengjie.service.VerificationCodeService;
import me.zhengjie.utils.ElAdminConstant;
import me.zhengjie.utils.EncryptUtils;
import me.zhengjie.utils.HttpClientUtil;
import me.zhengjie.utils.MD5Util;
import me.zhengjie.utils.PageUtil;
import me.zhengjie.utils.SecurityUtils;


/**
 * @author Zheng Jie
 * @date 2018-11-23
 */
@RestController
@RequestMapping("api")
public class UserController {

  @Autowired
  private UserService userService;

  @Autowired
  private PictureService pictureService;

  @Autowired
  private DataScope dataScope;

  @Autowired
  private DeptService deptService;

  @Autowired
  private RoleService roleService;

  @Autowired
  private RedisService redisService;

  @Autowired
  private VerificationCodeService verificationCodeService;

  @Autowired
  private HttpServletRequest request;

  @Autowired
  private UserRepository userRepository;
  /**
   * 邮箱验证
   */
  private static Pattern EMAIL_PATTERN =  Pattern.compile("([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}");


  @Log("查询用户")
  @GetMapping(value = "/users")
  @PreAuthorize("hasAnyRole('ADMIN','USER_ALL','USER_SELECT')")
  public ResponseEntity getUsers(UserQueryCriteria criteria, Pageable pageable) {
    Set<Long> deptSet = new HashSet<>();
    Set<Long> result = new HashSet<>();

    if (!ObjectUtils.isEmpty(criteria.getDeptId())) {
      deptSet.add(criteria.getDeptId());
      deptSet.addAll(dataScope.getDeptChildren(deptService.findByPid(criteria.getDeptId())));
    }

    // 数据权限
    Set<Long> deptIds = dataScope.getDeptIds();

    // 查询条件不为空并且数据权限不为空则取交集
    if (!CollectionUtils.isEmpty(deptIds) && !CollectionUtils.isEmpty(deptSet)) {

      // 取交集
      result.addAll(deptSet);
      result.retainAll(deptIds);

      // 若无交集，则代表无数据权限
      criteria.setDeptIds(result);
      if (result.size() == 0) {
        return new ResponseEntity(PageUtil.toPage(null, 0), HttpStatus.OK);
      } else {
        return new ResponseEntity(userService.queryAll(criteria, pageable), HttpStatus.OK);
      }
      // 否则取并集
    } else {
      result.addAll(deptSet);
      result.addAll(deptIds);
      criteria.setDeptIds(result);
      return new ResponseEntity(userService.queryAll(criteria, pageable), HttpStatus.OK);
    }
  }

  @Log("新增用户")
  @PostMapping(value = "/users")
  @PreAuthorize("hasAnyRole('ADMIN','USER_ALL','USER_CREATE')")
  public ResponseEntity create(@Validated @RequestBody User resources) {
    if (userRepository.findByUsername(resources.getUsername()) != null) {
      System.out.println("|----------用户名已存在-------------|");
      return new ResponseEntity(HttpStatus.PRECONDITION_REQUIRED);
    }else if(userRepository.findByPhone(resources.getPhone())!=null){
      throw new BadRequestException("手机号已存在");
    }else if (resources.getEmail()!=null&&!resources.getEmail().equals("")&&userRepository.findByEmail(resources.getEmail()) != null) {
      System.out.println("|----------邮箱号已存在-----------------|");
      return new ResponseEntity(HttpStatus.CONFLICT);
    } else if(resources.getEmail()!=null&&!resources.getEmail().equals("")){
      Matcher matcher = EMAIL_PATTERN.matcher(resources.getEmail());
      boolean email = matcher.matches();
      if(email){
        resources.setCreator(SecurityUtils.getUsername());
        checkLevel(resources);
        System.out.println("有效的邮箱地址=============================");
        return new ResponseEntity(userService.create(resources), HttpStatus.CREATED);
      }else{
        throw new BadRequestException("邮箱格式错误");
      }
    } else {
      resources.setCreator(SecurityUtils.getUsername());
      checkLevel(resources);
      return new ResponseEntity(userService.create(resources), HttpStatus.CREATED);

    }
     /* if(userRepository.findByUsername(resources.getUsername())==null&&userRepository.findByEmail(resources.getEmail())==null){
        resources.setCreator(SecurityUtils.getUsername());
        checkLevel(resources);
        userService.create(resources);
      };

      System.out.println("|------------------------新增的用户名----------------------------------------|"+resources.getUsername());

        try {
          resources.setCreator(SecurityUtils.getUsername());
          checkLevel(resources);
          userService.create(resources);
        }catch (Exception e){
          throw new BadRequestException("该用户名或者该邮箱已存在");
        }
          return new ResponseEntity(HttpStatus.NO_CONTENT);*/
    //return new ResponseEntity(userService.create(resources),HttpStatus.CREATED);
  }

  @Log("修改用户")
  @PutMapping(value = "/users")
  @PreAuthorize("hasAnyRole('ADMIN','USER_ALL','USER_EDIT')")
  public ResponseEntity update(@Validated(User.Update.class) @RequestBody User resources) {
    //如果修改了用户名
    if(!userService.findById(resources.getId()).getUsername().equals(resources.getUsername())){
      //判断用户名是否在数据库存在
      if (userRepository.findByUsername(resources.getUsername())!=null){
        System.out.println("|----------该用户名已存在-------------|");
        return new ResponseEntity(HttpStatus.PRECONDITION_REQUIRED);
      }
    }
    //如果修改了邮箱号
    if(!userService.findById(resources.getId()).getEmail().equals(resources.getEmail())){
      //判断邮箱是否在数据库存在
      if (userRepository.findByEmail(resources.getEmail()) != null) {
        System.out.println("|----------该邮箱已存在-------------|");
        return new ResponseEntity(HttpStatus.CONFLICT);
      }
    }

    //如果修改了手机号
    if(!userService.findById(resources.getId()).getPhone().equals(resources.getPhone())){
      //判断手机号在库里是否存在
      if(userRepository.findByPhone(resources.getPhone())!=null){
        throw new BadRequestException("手机号已存在");
      }
    }


/*        if (SecurityUtils.getUserId().equals(resources.getId())){
            throw new BadRequestException("该账户不能修改");
            //return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }else {
            try {
                checkLevel(resources);
                userService.update(resources);
            }catch (Exception e){
                throw new BadRequestException("该用户名或者该邮箱已存在");
            }
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }*/
    checkLevel(resources);
    userService.update(resources);
    return new ResponseEntity(HttpStatus.OK);
  }

  @Log("删除用户")
  @DeleteMapping(value = "/users/{id}")
  @PreAuthorize("hasAnyRole('ADMIN','USER_ALL','USER_DELETE')")
  public ResponseEntity delete(@PathVariable Long id) {
    if (SecurityUtils.getUserId().equals(id)) {
      throw new BadRequestException("该账户不能删除");
    } else {
      Integer currentLevel = Collections.min(roleService.findByUsers_Id(SecurityUtils.getUserId()).stream().map(RoleSmallDTO::getLevel).collect(Collectors.toList()));
      Integer optLevel = Collections.min(roleService.findByUsers_Id(id).stream().map(RoleSmallDTO::getLevel).collect(Collectors.toList()));

      if (currentLevel > optLevel) {
        throw new BadRequestException("角色权限不足");
      }
      userService.delete(id);
      return new ResponseEntity(HttpStatus.OK);
    }
  }

  /**
   * 修改密码
   *
   * @param user
   * @return
   */
  @PostMapping(value = "/users/updatePass")
  public ResponseEntity updatePass(@RequestBody UserPassVo user) {
    UserDetails userDetails = SecurityUtils.getUserDetails();
    if (!userDetails.getPassword().equals(EncryptUtils.encryptPassword(user.getOldPass()))) {
      throw new BadRequestException("修改失败，旧密码错误");
    }
    if (userDetails.getPassword().equals(EncryptUtils.encryptPassword(user.getNewPass()))) {
      throw new BadRequestException("新密码不能与旧密码相同");
    }
    userService.updatePass(userDetails.getUsername(), EncryptUtils.encryptPassword(user.getNewPass()));
    return new ResponseEntity(HttpStatus.OK);
  }

  /**
   * 修改头像
   *
   * @param file
   * @return
   */
  @PostMapping(value = "/users/updateAvatar")
  public ResponseEntity updateAvatar(@RequestParam MultipartFile file) {
    Picture picture = pictureService.upload(file, SecurityUtils.getUsername());
    userService.updateAvatar(SecurityUtils.getUsername(), picture.getUrl());
    return new ResponseEntity(HttpStatus.OK);
  }

  /**
   * 修改邮箱
   *
   * @param user
   * @param user
   * @return
   */
  @Log("修改邮箱")
  @PostMapping(value = "/users/updateEmail/{code}")
  public ResponseEntity updateEmail(@PathVariable String code, @RequestBody User user) {
    UserDetails userDetails = SecurityUtils.getUserDetails();
    if (!userDetails.getPassword().equals(EncryptUtils.encryptPassword(user.getPassword()))) {
      throw new BadRequestException("密码错误");
    }
    VerificationCode verificationCode = new VerificationCode(code, ElAdminConstant.RESET_MAIL, "email", user.getEmail());
    verificationCodeService.validated(verificationCode);
    userService.updateEmail(userDetails.getUsername(), user.getEmail());
    return new ResponseEntity(HttpStatus.OK);
  }

  @PostMapping("/updateWx")
  public String updateUser(String wx, String invitationcode) {
    System.out.println(wx);
    System.out.println(invitationcode);
    if (invitationcode != null && (userService.selectWx(wx) == null || userService.selectWx(wx).equals("")) && (userService.selectinvitationcode(invitationcode) == null || ("".equals(userService.selectinvitationcode(invitationcode))))) {
      return "没有邀请码";
    } else if (userService.selectWx(wx) != null && (invitationcode == null || ("").equals(invitationcode))) {
      invitationcode = null;
         /*  User user=new User();
           user.setWx(wx);
           user.setInvitationcode(invitationcode);*/
      userService.updateWx(wx, invitationcode);
      return "修改验证码";
    } else if (invitationcode != null && userService.selectinvitationcode(invitationcode) != null) {
         /*  User user=new User();
           user.setWx(wx);
           user.setInvitationcode(invitationcode);*/
      userService.updateByPrimaryKey(wx, invitationcode);
      return "添加微信";
    }
    return null;
  }


  /**
   * 如果当前用户的角色级别低于创建用户的角色级别，则抛出权限不足的错误
   *
   * @param resources
   */
  private void checkLevel(User resources) {
    Integer currentLevel = Collections.min(roleService.findByUsers_Id(SecurityUtils.getUserId()).stream().map(RoleSmallDTO::getLevel).collect(Collectors.toList()));
    Integer optLevel = roleService.findByRoles(resources.getRoles());
    if (currentLevel > optLevel) {
      throw new BadRequestException("角色权限不足");
    }
  }
  //接收code
  @PostMapping("/codeWX")
  public Map<String, String> aa(String code) {

    Map<String, String> mapParam = new HashMap<>();
    mapParam.put("appid", "wx662fc36c6fbfd894");
    mapParam.put("secret", "2d8b12635e1139768243a99ea0c7a67c");
    mapParam.put("js_code", code);
    mapParam.put("grant_type", "authorization_code");


    String ss = HttpClientUtil.doGet("https://api.weixin.qq.com/sns/jscode2session", mapParam);


    String s = new String(ss);

    Map<String, String> mapDD = new HashMap<>();
    mapDD.put("token", MD5Util.strToMD5(s));
    System.out.println(MD5Util.strToMD5(s));//加密
    System.out.println(MD5Util.convertMD5(MD5Util.convertMD5(s)));//解密
    return mapDD;

  }

  @PostMapping("/username")
  public User sd(String username) {

    return userService.selectusername(username);
  }

  @GetMapping("/getUserByOpenId")
  public User getUserByOpenId(String openId) {
    return userService.selectWx(openId);
  }
}
