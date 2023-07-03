package org.eclipse.shoply.web.controller;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.shoply.enumeration.UserRole;
import org.eclipse.shoply.model.User;
import org.eclipse.shoply.security.ShoplyAuthenticationManager;
import org.eclipse.shoply.security.TokenUtils;
import org.eclipse.shoply.support.UserDtoToUser;
import org.eclipse.shoply.support.UserToUserDto;
import org.eclipse.shoply.web.dto.UserAuthDto;
import org.eclipse.shoply.web.dto.UserDto;
import org.eclipse.shoply.web.dto.UserRegisterDto;
import org.ecplise.shoply.service.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {
	
    @Autowired
    private ShoplyAuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;
	
    @Autowired
    private TokenUtils tokenUtils;
    
    @Autowired
    private UserToUserDto toUserDto;
    
    @Autowired
    private UserDtoToUser toUser;

    @Autowired
    private PasswordEncoder passwordEncoder;
    

	@Autowired  
	private UserServices userService;
    


    
    @PreAuthorize("permitAll()")
    @GetMapping(value = "/getUser/{name}")
    public ResponseEntity<UserDto> find(@PathVariable String name){
    	User user = userService.findByUsername(name);
    	if(user==null) {
    		return null;
    	}
        return new ResponseEntity<>(toUserDto.convert(user),HttpStatus.OK);
    }

   
    
    @PreAuthorize("permitAll()")
    @RequestMapping(path = "/auth", method = RequestMethod.POST)
    public ResponseEntity<String> authenticateUser(@RequestBody UserAuthDto dto) {
        
    	// Perform the authentication
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken
        		(dto.getUsername(), dto.getPassword());
       
       Authentication authentication = authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        
        try {
             //Reload user details so we can generate token
            UserDetails userDetails = userDetailsService.loadUserByUsername(dto.getUsername());
          
            return new ResponseEntity<>(tokenUtils.generateToken(userDetails), HttpStatus.OK);
          
        } catch (UsernameNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
    
    
    
    @PreAuthorize("permitAll()")
    @RequestMapping(path = "/register", method = RequestMethod.POST)
    public ResponseEntity<Map<String, String>> register(@RequestBody UserRegisterDto dto) {
        if (dto.getPassword().length() < 7) {
            return new ResponseEntity<>(Map.of("message", "Password too short"), HttpStatus.BAD_REQUEST);
        }
        if (!dto.getConfirmPassword().equals(dto.getPassword())) {

            return new ResponseEntity<>(Map.of("message", "Passwords don't match"), HttpStatus.BAD_REQUEST);
        }
        if (userService.findByUsername(dto.getUsername()) != null) {
            return new ResponseEntity<>(Map.of("message", "Username already exists"), HttpStatus.BAD_REQUEST);
        }
        
        if (!dto.getGender().equals("Male") && !dto.getGender().equals("Female") && !dto.getGender().equals("Other")) {
            return new ResponseEntity<>(Map.of("message", "Invalid gender"), HttpStatus.BAD_REQUEST);
        }

        User user = new User();
        UserRole role = UserRole.BUYER;
        user.setAge(dto.getAge());
        user.setEmail(dto.getEmail());
        user.setGender(dto.getGender());
        user.setName(dto.getName());
        user.setUsername(dto.getUsername());
        user.setSurname(dto.getSurname());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole(role);

        userService.save(user);

        UserDetails userDetails = userDetailsService.loadUserByUsername(dto.getUsername());

        Map<String, String> result = new HashMap<>();
        result.put("token", tokenUtils.generateToken(userDetails));
        result.put("role", role.toString());

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    
    
    
    @PreAuthorize("hasRole('BUYER')")
    @RequestMapping(path = "/updateUser", method = RequestMethod.POST)
    public ResponseEntity<Map<String, String>> updateUser(@RequestBody UserDto dto) {
    	
    	User full = userService.findByUsername(dto.getUsername());
    	if(full!=null) {
	        User user = toUser.convert(dto);
	        UserRole role = UserRole.BUYER;
	        user.setId(full.getId());
	        user.setRole(role);
	        userService.save(user);
	       
	        return new ResponseEntity<>(Map.of("message", "Changes saved sucessfully"), HttpStatus.OK);
    	} else {
    		return new ResponseEntity<>(Map.of("message", "User not found"), HttpStatus.BAD_REQUEST);
    	}
    }
    
    
    
    @PreAuthorize("hasRole('SELLER')")
    @RequestMapping(path = "/updateSeller", method = RequestMethod.POST)
    public ResponseEntity<Map<String, String>> updateSeller(@RequestBody UserDto dto) {
    	
    	User full = userService.findByUsername(dto.getUsername());
    	if(full!=null) {
    	 if (!passwordEncoder.matches(dto.getPassword(), full.getPassword())) {
             return new ResponseEntity<>(Map.of("message", "Wrong password"), HttpStatus.BAD_REQUEST);
         }
    	 
    		User user = toUser.convert(dto);
    		user.setId(full.getId());
    		user.setCountry(dto.getCountry());
    		user.setRole(full.getRole());
	        userService.save(user);
	        return new ResponseEntity<>(Map.of("message", "Changes saved sucessfully"), HttpStatus.OK);
    	} else {
    		return new ResponseEntity<>(Map.of("message", "User not found"), HttpStatus.BAD_REQUEST);
    	}
    }
    
    
    
    @PreAuthorize("hasRole('BUYER')")
    @RequestMapping(path = "/becomeSeller", method = RequestMethod.POST)
    public ResponseEntity<Map<String, String>> becomeSeller(@RequestBody UserDto dto) {
    	User user = userService.findByUsername(dto.getUsername());
    	
    	 if (user==null) {
             return new ResponseEntity<>(Map.of("message", "User not found"), HttpStatus.BAD_REQUEST);
         }
    	 if (user.getRole()==UserRole.SELLER) {
             return new ResponseEntity<>(Map.of("message", "User already a seller"), HttpStatus.BAD_REQUEST);
         }
    	 if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
             return new ResponseEntity<>(Map.of("message", "Wrong password"), HttpStatus.BAD_REQUEST);
         }
    	 if ( dto.getCountry()==null || dto.getCountry().isEmpty()) {
             return new ResponseEntity<>(Map.of("message", "Must select a country"), HttpStatus.BAD_REQUEST);
         }
    	 
        User result = toUser.convert(dto);
        UserRole role = UserRole.SELLER;
        result.setId(user.getId());
        result.setRole(role);
        result.setCountry(dto.getCountry());
        
        userService.save(result);
        
        Map<String, String> response = new HashMap<>();
        
        UserDetails userDetails = userDetailsService.loadUserByUsername(dto.getUsername());
        
        response.put("message", "You are now a seller!");
        response.put("role", "ROLE_"+ role.toString());
        response.put("token", tokenUtils.generateToken(userDetails));
       
        
    	return new ResponseEntity<>(response, HttpStatus.OK);
        
     }
}