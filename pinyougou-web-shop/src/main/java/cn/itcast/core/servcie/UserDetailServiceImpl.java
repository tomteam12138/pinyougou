package cn.itcast.core.servcie;


import cn.itcast.core.pojo.seller.Seller;
import cn.itcast.core.service.SellerService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by wang on 2019/4/12.
 */
@Service
@Transactional
public class UserDetailServiceImpl implements UserDetailsService {
    private SellerService sellerService;

    public void setSellerService(SellerService sellerService) {
        this.sellerService = sellerService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Seller seller = sellerService.findOne(username);
        if (seller != null){
            if ("1".equals(seller.getStatus())){
                Set<GrantedAuthority> grantedAuthoritySet = new HashSet<>();
                grantedAuthoritySet.add(new SimpleGrantedAuthority("ROLE_SELLER"));
                User user = new User(username,seller.getPassword(),grantedAuthoritySet);
                return user;
            }
        }
        return null;
    }
}
