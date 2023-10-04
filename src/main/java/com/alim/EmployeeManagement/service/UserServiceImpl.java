package com.alim.EmployeeManagement.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.alim.EmployeeManagement.model.User;
import com.alim.EmployeeManagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;


@Service
public class UserServiceImpl implements IUserService, UserDetailsService{

    private final UserRepository userRepo;

    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepo, BCryptPasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Integer saveUser(@ModelAttribute("user") User user) {
        String passwd= user.getPassword();
        String encodedPasswod = passwordEncoder.encode(passwd);
        user.setPassword(encodedPasswod);
        user = userRepo.save(user);
        return Math.toIntExact(user.getId());
    }

    @Override
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    @Override
    public void saveUsers(User user) {
        this.userRepo.save(user);

    }

    @Override
    public User getUserById(long id) {
        Optional<User> optional = userRepo.findById(id);
        User user= null;
        if(optional.isPresent()){
            user = optional.get();
        }
        else {
            throw new RuntimeException(" User not found : "+ id);
        }
        return user;

    }

    @Override
    public void deleteUserById(long id) {
        this.userRepo.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {

        Optional<User> opt = userRepo.findUserByEmail(email);

        if(opt.isEmpty())
            throw new UsernameNotFoundException("User with email: " +email +" not found !");
        else {
            User user = opt.get();
            return new org.springframework.security.core.userdetails.User(
                    user.getEmail(),
                    user.getPassword(),
                    user.getRoles()
                            .stream()
                            .map(role-> new SimpleGrantedAuthority(role))
                            .collect(Collectors.toSet())
            );
        }

    }



    //Other Approach: Without Using Lambda & Stream API Of Java 8

    /** @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

    Optional<User> opt = userRepo.findUserByEmail(email);

    org.springframework.security.core.userdetails.User springUser=null;

    if(opt.isEmpty()) {
    throw new UsernameNotFoundException("User with email: " +email +" not found");
    }
    User user =opt.get();
    List<String> roles = user.getRoles();
    Set<GrantedAuthority> ga = new HashSet<>();
    for(String role:roles) {
    ga.add(new SimpleGrantedAuthority(role));
    }

    springUser = new org.springframework.security.core.userdetails.User(
    email,
    user.getPassword(),
    ga );
    return springUser;
    } */

}
