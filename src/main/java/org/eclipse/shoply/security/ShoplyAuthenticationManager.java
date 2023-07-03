
package org.eclipse.shoply.security;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.shoply.model.User;
import org.eclipse.shoply.repository.UserRepository;
import org.ecplise.shoply.service.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class ShoplyAuthenticationManager implements AuthenticationManager {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserServices userService;
    
    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        User user = userService.findByUsername(authentication.getPrincipal().toString());
        if (user!=null) {
            if (passwordEncoder.matches(authentication.getCredentials().toString(), user.getPassword())) {
                List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();
                
                    grantedAuthorityList.add(new SimpleGrantedAuthority(user.getRole().toString()));
          
                return new UsernamePasswordAuthenticationToken(authentication.getPrincipal(), authentication.getCredentials(), grantedAuthorityList);
            } else {
                throw new BadCredentialsException("Wrong Password");
            }
        } else {
            throw new BadCredentialsException("Wrong UserName");
        }
    }
}
