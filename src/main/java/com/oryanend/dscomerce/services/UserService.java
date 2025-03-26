package com.oryanend.dscomerce.services;

import com.oryanend.dscomerce.entities.Role;
import com.oryanend.dscomerce.entities.User;
import com.oryanend.dscomerce.projections.UserDetailsProjection;
import com.oryanend.dscomerce.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository repository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        List<UserDetailsProjection> result = repository.searchUserAndRolesByEmail(username);
        if (result.isEmpty()) {
            throw new UsernameNotFoundException(username);
        }

        User user = new User();
        user.setEmail(result.get(0).getUsername());
        user.setPassword(result.get(0).getPassword());

        for (UserDetailsProjection u : result) {
            user.addRole(new Role(u.getRoleId(), u.getAuthority()));
        }

        return user;
    }
}
