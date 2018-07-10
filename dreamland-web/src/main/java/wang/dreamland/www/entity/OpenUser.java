package wang.dreamland.www.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import wang.dreamland.www.common.ValidateUtils;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Created by 12903 on 2018/6/12.
 */
public class OpenUser implements UserDetails{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long uId;

    private String openId;

    private String accessToken;

    private String nickName;

    private String avatar;

    private String openType;

    private Long expiredTime;

    private Date lastLoginTime;

    @Transient
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getuId() {
        return uId;
    }

    public void setuId(Long uId) {
        this.uId = uId;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getOpenType() {
        return openType;
    }

    public void setOpenType(String openType) {
        this.openType = openType;
    }

    public Long getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime(Long expiredTime) {
        this.expiredTime = expiredTime;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
        if(user==null){
            return authorities;
        }
        List<Role> roles = user.getRoles();
        if(roles == null || roles.size()<=0){
            return null;
        }
        for(Role r:roles){
            authorities.add(new SimpleGrantedAuthority(r.getRoleValue()));
        }
        return authorities;
    }

    @Override
    public String getPassword() {
        return getAccessToken();
    }

    @Override
    public String getUsername() {
        return getOpenId();
    }

    @Override
    public boolean isAccountNonExpired() {
        return ValidateUtils.isAccountNonExpired(new Date(),getLastLoginTime(),getExpiredTime());
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isAccountNonExpired();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof OpenUser) {
            return getOpenId().equals(((OpenUser)obj).getOpenId())||getUsername().equals(((OpenUser)obj).getUsername());
        }
        return false;
    }
    @Override
    public int hashCode() {
        return getUsername().hashCode();
    }

    @Override
    public String toString() {
        return "OpenUser{" +
                "id=" + id +
                ", uId=" + uId +
                ", openId='" + openId + '\'' +
                ", accessToken='" + accessToken + '\'' +
                ", nickName='" + nickName + '\'' +
                ", avatar='" + avatar + '\'' +
                ", openType='" + openType + '\'' +
                ", expiredTime=" + expiredTime +
                ", lastLoginTime=" + lastLoginTime +
                ", user=" + user +
                '}';
    }
}
