package me.zhengjie.modules.system.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Set;

/**
 * @author Zheng Jie
 * @date 2018-11-22
 */
@Entity
@Getter
@Setter
@Table(name="EL_USER")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull(groups = Update.class)
    private Long id;

    @NotBlank
    @Column(unique = true)
    private String username;

    @Column(name="other_contacts")
    private String otherContacts;

    @NotNull
    @Column(name="sex")
    private Long sex;

    @NotNull
    @Column(name="age")
    private Long age;

    private String avatar;

    @Column(name = "wx_avatar")
    private String wxAvatar;

    //@Pattern(regexp = "([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}",message = "格式错误")
    private String email;

    @NotBlank
    private String phone;

    @NotNull
    private Boolean enabled;

    private String wx; 

    private String password;

    @CreationTimestamp
    @Column(name = "create_time")
    private Timestamp createTime;

    @Column(name = "last_password_reset_time")
    private Date lastPasswordResetTime;

    @ManyToMany
    @JoinTable(name = "EL_USERS_ROLES", joinColumns = {@JoinColumn(name = "user_id",referencedColumnName = "id")}, inverseJoinColumns = {@JoinColumn(name = "role_id",referencedColumnName = "id")})
    private Set<Role> roles;

    @OneToOne
    @JoinColumn(name = "job_id")
    private Job job;


    @OneToOne
    @JoinColumn(name = "position_id")
    private Position position;

    @OneToOne
    @JoinColumn(name = "dept_id")
    private Dept dept;
    
    private String invitationcode;
    
    private String creator;
    
    private String modifier;
    
    private Date modiftime;

   public @interface Update {}

  @Override
  public String toString() {
    return "User{" +
      "id=" + id +
      ", username='" + username + '\'' +
      ", otherContacts='" + otherContacts + '\'' +
      ", sex=" + sex +
      ", age=" + age +
      ", avatar='" + avatar + '\'' +
      ", wxAvatar='" + wxAvatar + '\'' +
      ", email='" + email + '\'' +
      ", phone='" + phone + '\'' +
      ", enabled=" + enabled +
      ", wx='" + wx + '\'' +
      ", password='" + password + '\'' +
      ", createTime=" + createTime +
      ", lastPasswordResetTime=" + lastPasswordResetTime +
      ", roles=" + roles +
      ", job=" + job +
      ", position=" + position +
      ", dept=" + dept +
      ", invitationcode='" + invitationcode + '\'' +
      ", creator='" + creator + '\'' +
      ", modifier='" + modifier + '\'' +
      ", modiftime=" + modiftime +
      '}';
  }
}
