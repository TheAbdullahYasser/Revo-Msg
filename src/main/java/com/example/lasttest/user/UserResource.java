package com.example.lasttest.user;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
public class UserResource {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @GetMapping(path = "/users")
    public MappingJacksonValue getUsers() {
        List<User> us = userRepository.findAll();
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id", "name");
        FilterProvider filters = new SimpleFilterProvider().addFilter("userFiltering", filter);
        MappingJacksonValue mapping = new MappingJacksonValue(us);
        mapping.setFilters(filters);
        return mapping;
    }

    @GetMapping(path = "/users/{id}")
    public User getUserById(@PathVariable Integer id) {
        Optional<User> user = userRepository.findById(id);
        if (!(user.isPresent())) {
            throw new UserNotFoundException("id-" + id);
        }

        return user.get();
    }

    @DeleteMapping(path = "/users/{id}")
    public ResponseEntity<Object> DeleteUserById(@PathVariable Integer id) {
        userRepository.deleteById(id);
        return ResponseEntity.status(202).build();
    }

    @PostMapping(path = "/users")
    public ResponseEntity<Object> addUser(@RequestBody User user) {

        User save = userRepository.save(user);
        System.out.println(user.getId());
//        User SavedUser = userDaoService.SaveUser(user);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(save.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping(path = "/user/{id}/posts")
    public List<Post> getUserPosts(@PathVariable int id) {
        Optional<User> user = userRepository.findById(id);
        if (!(user.isPresent())) {
            throw new UserNotFoundException("id-" + id);
        }
        return user.get().getPosts();
    }

    @PostMapping(path = "/user/{id}/posts")
    public ResponseEntity<Object> makePost(@PathVariable int id, @RequestBody Post post) {
        Optional<User> user = userRepository.findById(id);
        if (!(user.isPresent())) {
            throw new UserNotFoundException("id-" + id);
        }
        User u = user.get();
        post.setUser(u);

        postRepository.save(post);


        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(post.getId()).toUri();
        return ResponseEntity.created(location).build();

    }

}
