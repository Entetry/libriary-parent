package com.antonklintsevich.controllers;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.antonklintcevich.common.BookDto;
import com.antonklintcevich.common.UserDto;
import com.antonklintsevich.exception.BookNotFoundException;
import com.antonklintsevich.exception.RoleNotFoundException;
import com.antonklintsevich.exception.UserNotFoundException;
import com.antonklintsevich.services.UserService;
import com.antonklintsevich.services.UserServiceIml;

@RestController
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserServiceIml userServiceImpl;
//    @Autowired
//    private SecurityContext context;

    @PutMapping("/users/addbook")
    public void addBook(@RequestParam("userId") String userId, @RequestParam("bookId") String bookId) {
        try {
            userService.addBookToUser(Long.parseLong(userId), Long.parseLong(bookId));
        } catch (UserNotFoundException | BookNotFoundException exc) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exc.getMessage(), exc);
        }

    }
    
    @PutMapping("/users/addrole")
    public void addRole(@RequestParam("userId") String userId, @RequestParam("roleId") String roleId) {
        try {
            userService.addRoleToUser(Long.parseLong(userId), Long.parseLong(roleId));
        } catch (UserNotFoundException | RoleNotFoundException exc) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exc.getMessage(), exc);
        }
    }

    @GetMapping("/users")
    @ResponseBody
    public List<UserDto> getAllUsers() {
        return userService.getAllUserAsUserDTO();
    }

    @DeleteMapping("/users/{userId}")
    public void delete(@PathVariable("userId") Long userId) {
        try {
            userService.delete(userId);
        } catch (UserNotFoundException exc) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exc.getMessage(), exc);
        }
    }

    @PostMapping("/users")
    public void create(@RequestBody UserDto dto) {
        userService.create(dto);

    }

    @GetMapping("/users/{userId}")
    @ResponseBody
    public UserDto getUserbyId(@PathVariable("userId") Long userId) {
        try {
            return userService.getUserById(userId);
        } catch (UserNotFoundException exc) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exc.getMessage(), exc);
        }
    }

    @PutMapping("/users/{userId}")
    public void update(@PathVariable("userId") Long userId, @RequestBody UserDto userDto) {
        try {
            userService.update(userId, userDto);
        } catch (UserNotFoundException exc) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exc.getMessage(), exc);
        }
    }

    @GetMapping("/users/{userId}/books")
    @ResponseBody
    public Set<BookDto> getAllUserBooks(@PathVariable("userId") Long userId) {
        try {
            return userService.getAllUserBooksAsBookDto(userId);
        } catch (UserNotFoundException exc) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exc.getMessage(), exc);
        }
    }
    @GetMapping("/users/userdetails")
    @ResponseBody
    public UserDetails getUserDetails(@RequestParam String username) {
        try {
            return userServiceImpl.loadUserByUsername(username);
        } catch (UserNotFoundException exc) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exc.getMessage(), exc);
        }
    }
}
