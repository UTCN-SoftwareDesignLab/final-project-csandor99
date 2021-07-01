package com.example.demo.user;

import com.example.demo.user.dto.UserDTO;
import com.example.demo.user.dto.UserListDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demo.UrlMapping.ENTITY;
import static com.example.demo.UrlMapping.USERS;

@RestController
@RequestMapping(USERS)
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public List<UserListDTO> allUsers() {
        return userService.allUsersForList();
    }

    @DeleteMapping(ENTITY)
    public void delete(@PathVariable Long id){userService.delete(id);}

    @PatchMapping
    public void edit(@RequestBody UserDTO userDTO) {
        userService.edit(userDTO);
    }

    @PostMapping
    public void create(@RequestBody UserDTO userDTO){ userService.create(userDTO); }

}
