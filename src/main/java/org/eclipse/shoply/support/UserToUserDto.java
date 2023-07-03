package org.eclipse.shoply.support;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.shoply.model.User;
import org.eclipse.shoply.web.dto.UserDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserToUserDto implements Converter<User, UserDto>{

	@Override
	public UserDto convert(User user) {
		
		UserDto dto = new UserDto();
		
		dto.setAge(user.getAge());
		dto.setCountry(user.getCountry());
		dto.setGender(user.getGender());
		dto.setId(user.getId());
		dto.setRole(user.getRole().toString());
		dto.setUsername(user.getUsername()); 
		dto.setSurname(user.getSurname());
		dto.setPassword(user.getPassword());
		dto.setName(user.getName()); 
		dto.setEmail(user.getEmail());
		return dto;
	}
	
	public List<UserDto> convert(List<User> users){
		List<UserDto> result = new ArrayList<UserDto>();
		for (User user:users) {
			result.add(convert(user));
		}
		
		return result;
	}

	
}
