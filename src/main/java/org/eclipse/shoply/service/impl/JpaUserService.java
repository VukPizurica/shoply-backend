package org.eclipse.shoply.service.impl;

import java.util.List;
import java.util.Optional;

import org.eclipse.shoply.model.User;
import org.eclipse.shoply.repository.UserRepository;
import org.ecplise.shoply.service.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class JpaUserService implements UserServices{

	
	@Autowired
	private UserRepository userRepository;
	

	public List<User> findAll() {
		
		return userRepository.findAll();
	}

	public void delete(Long id) {
		
		userRepository.deleteById(id);
	}
	
	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}
	public boolean update(User user) {
		boolean result;
		return false;
	}


	@Override
	public Optional<User> findById(Long id) {
		return userRepository.findById(id);
	}
	
	@Override
	public boolean saveUser(User user) {
		boolean result=false;
		User fullUser =userRepository.findByUsername(user.getUsername());
		
		if(user.getPassword()==null || user.getPassword().equals("") ) {
			user.setPassword(fullUser.getPassword());
		}
		return false;
	
		
	}

	@Override
	public User save(User user) {
		userRepository.save(user);
		return null;
	}

}
