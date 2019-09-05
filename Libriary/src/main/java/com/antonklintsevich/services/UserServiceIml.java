package com.antonklintsevich.services;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.antonklintcevich.common.UserStatusDto;
import com.antonklintsevich.common.DtoConverter;
import com.antonklintsevich.entity.User;
import com.antonklintsevich.entity.UserStatus;
import com.antonklintsevich.exception.UserNotFoundException;
import com.antonklintsevich.persistense.UserRepository;

@Service
public class UserServiceIml {
    @Autowired
    UserRepository userRepository;
    @Autowired
    private EntityManagerFactory entityManagerFactory;


    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Optional<User> userOptional = userRepository.findByUsername(username, entityManager);
        if (!userOptional.isPresent()) {
            throw new UsernameNotFoundException("invalid username or password");
        }
        User user = userOptional.get();
        Collection<? extends GrantedAuthority> authorities = user.getRoles().stream()
                .flatMap(x -> x.getAuthorities().stream())
                .map(authority -> new SimpleGrantedAuthority(authority.getName())).collect(Collectors.toList());
        entityManager.close();
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                authorities);
    }

    public String getCurrentUserUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }
        return username;
    }

    public User getCurrentUser() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        return userRepository.findByUsername(getCurrentUserUsername(), entityManager)
                .orElseThrow(UserNotFoundException::new);
    }

    public UserStatus getCurrentUserStatus() {
        return getCurrentUser().getStatus();
    }
    public UserStatusDto getCurrentUserStatusDto() {
        return DtoConverter.constructUserStatusDto(getCurrentUser().getStatus());
    }
}
