package org.eclipse.shoply.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.eclipse.shoply.model.User;
import org.eclipse.shoply.support.UserToUserDto;
import org.eclipse.shoply.web.dto.UserDto;
import org.ecplise.shoply.service.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired  
	private UserServices userService;

	@Autowired  
	private UserToUserDto toUserDto;

  /* Zelimo da predstavimo usera preko UserDetails klase - nacina
  *  na koji Spring boot predstavlja usera. Ucitamo na osnovu korisnickog imena
  *  usera iz nase mysql baze i u okviru UserDetails namapiramo njegove podatke
  *  - kredencijale i rolu kroz GrantedAuthorities. */
  @Override
  @Transactional
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userService.findByUsername(username);

    if (user==null) {
    	 throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
    }
    UserDto dto = toUserDto.convert(user);
    
     if (dto.getUsername() == null || dto.getPassword()==null) {
      throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
     	
     } else {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();

        String role = "ROLE_" + dto.getRole();
    
        grantedAuthorities.add(new SimpleGrantedAuthority(role));

        return new org.springframework.security.core.userdetails.User(
                dto.getUsername().trim(),
                dto.getPassword().trim(),
                grantedAuthorities);
    }
  }
}
