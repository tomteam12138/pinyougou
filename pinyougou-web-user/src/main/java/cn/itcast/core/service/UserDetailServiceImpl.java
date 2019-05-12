package cn.itcast.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by wang on 2019/4/26.
 */
public class UserDetailServiceImpl implements UserDetailsService {

    private UserService userService;
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        cn.itcast.core.pojo.user.User userAndRole = userService.findUserAndRole(username);
        if (userAndRole!=null){
            Set<GrantedAuthority> authorities = new HashSet<>();
            if (!"VISITOR".equals(userAndRole.getRole().getRoleName())){
                authorities.add(new SimpleGrantedAuthority("ROLE_"+userAndRole.getRole().getRoleName()));
            }else {
                authorities.add(new SimpleGrantedAuthority(""));
            }
            User user = new User(username, "", authorities);
            return user;
        }
        return null;
    }
}
