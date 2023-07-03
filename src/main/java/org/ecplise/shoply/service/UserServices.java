package org.ecplise.shoply.service;

import java.util.List;
import java.util.Optional;

import org.eclipse.shoply.model.User;

public interface UserServices {

    Optional<User> findById(Long id);

    User findByUsername(String username);
    
    List<User> findAll();

    User save(User user);

    void delete(Long id);
    
    boolean saveUser(User user);
   

}
