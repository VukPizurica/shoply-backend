package org.eclipse.shoply.support;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.shoply.enumeration.UserRole;
import org.eclipse.shoply.model.User;
import org.eclipse.shoply.web.dto.UserDto;
import org.ecplise.shoply.service.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserDtoToUser implements Converter<UserDto, User> {

	@Autowired
	private UserServices userService;
	
	 @Autowired
	    private PasswordEncoder passwordEncoder;
	    
	
	@Override
	public User convert(UserDto dto) {
		Long id = dto.getId();
		User user = id ==null? userService.findByUsername(dto.getUsername()) : userService.findById(id).get();
		
		if (user !=null) { 
			if(dto.getRole()!=null) {
				user.setRole(UserRole.valueOf(dto.getRole()));
			}
			
			user.setAge(dto.getAge());
			user.setSurname(dto.getSurname());
			user.setName(dto.getName());
			user.setEmail(dto.getEmail());
			user.setUsername(dto.getUsername());
			user.setGender(dto.getGender());
		}
		return user;
	}
	
	
	public List<User> convert(List<UserDto> users) {
		List<User> result = new ArrayList<User>();
		
		for (UserDto dto : users) {
			result.add(convert(dto));
			
		}
		return result;
	}

}
