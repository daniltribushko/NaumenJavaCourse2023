package com.example.naumen2023.services.users;

<<<<<<<< HEAD:src/main/java/com/example/naumen2023/services/UserService.java
package com.example.naumen2023.services;
========
package com.example.naumen2023.services.users;
>>>>>>>> 5c42a25f0812efc17363843262ec76e2ebf789f2:src/main/java/com/example/naumen2023/services/users/UserService.java

import com.example.naumen2023.models.enums.Roles;
import com.example.naumen2023.models.entities.UserEntity;
import com.example.naumen2023.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<UserEntity> getAllUsers(){
        return userRepository.findAll();
    }

    public void addUser(UserEntity newUser) throws Exception{
        UserEntity user = userRepository.findByUsername(newUser.getUsername());
        if(user!=null){
            throw new Exception("user already exist!");
        }
        else{
            newUser.setRoles(Collections.singleton(Roles.USER));
            newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
            userRepository.save(newUser);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByUsername(username);
        return new User(user.getUsername(), user.getPassword(), mapRolesToAthorities(user.getRoles()));
    }

    private List<? extends GrantedAuthority> mapRolesToAthorities(Set<Roles> roles){
        return roles.stream().map(r -> new SimpleGrantedAuthority("ROLE_" + r.name())).collect(Collectors.toList());
    }
}
