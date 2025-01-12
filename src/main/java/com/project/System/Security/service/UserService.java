/*
package com.project.System.Security.service;

import com.project.System.Security.Exception.InvalidPasswordException;
import com.project.System.Security.Exception.UserNotFoundException;
import com.project.System.Security.components.JwtTokenUtil;
import com.project.System.Security.dto.UserDto;
import com.project.System.Security.model.Branch;
import com.project.System.Security.model.Role;
import com.project.System.Security.model.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.project.System.Security.repositories.BranchRepository;
import com.project.System.Security.repositories.RoleRepository;
import com.project.System.Security.repositories.UserRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService implements IUserService{

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BranchRepository branchRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final ModelMapper modelMapper;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository,
                       BranchRepository branchRepository, PasswordEncoder passwordEncoder,AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.branchRepository = branchRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = new JwtTokenUtil();
        this.modelMapper = modelMapper;

    }

    @Override
    public UserDto createUser(UserDto userDto) {
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(passwordEncoder.encode(userDto.getPassword())); // Mã hóa password

        if (userDto.getRoleId() != null) {
            Role role = roleRepository.findById(userDto.getRoleId())
                    .orElseThrow(() -> new RuntimeException("Role not found with id: " + userDto.getRoleId()));
            user.setRole(role);
        }

        if (userDto.getBranchId() != null) {
            Branch branch = branchRepository.findById(userDto.getBranchId())
                    .orElseThrow(() -> new RuntimeException("Branch not found with id: " + userDto.getBranchId()));
            user.setBranch(branch);
        }
        user = userRepository.save(user);
        return convertToDTO(user);
    }

    @Override
    public UserDto updateUser(Integer id, UserDto userDTO) {
        User user = userRepository.findById(Long.valueOf(id))
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setUsername(userDTO.getUsername());

        //Kiểm tra và mã hóa password nếu có
        if (userDTO.getPassword() != null && !userDTO.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        }

        if (userDTO.getRoleId() != null) {
            Role role = roleRepository.findById(userDTO.getRoleId())
                    .orElseThrow(() -> new RuntimeException("Role not found"));
            user.setRole(role);
        }

        if (userDTO.getBranchId() != null) {
            Branch branch = branchRepository.findById(userDTO.getBranchId())
                    .orElseThrow(() -> new RuntimeException("Branch not found"));
            user.setBranch(branch);
        }

        user = userRepository.save(user);
        return convertToDTO(user);
    }

    @Override
    public void deleteUser(Integer id) {
        if (!userRepository.existsById(Long.valueOf(id))) {
            throw new RuntimeException("User not found");
        }
        userRepository.deleteById(Long.valueOf(id));
    }

    @Override
    public UserDto getUserById(Integer id) {
        User user = userRepository.findById(Long.valueOf(id))
                .orElseThrow(() -> new RuntimeException("User not found"));
        return convertToDTO(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDto> getUsersByRoleId(Integer roleId) {
        List<User> users = userRepository.findByRoleId(roleId);
        return users.stream()
                .map(user -> modelMapper.map(user, UserDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDto> getUsersByBranchId(Long branchId) {
        List<User> users = userRepository.findByBranchId(branchId);
        return users.stream()
                .map(user -> modelMapper.map(user, UserDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public String login(String username, String password) throws Exception{
//        Optional<User> optionalUser = userRepository.findByUsername(username);
//        if (optionalUser.isEmpty()) {
//            throw new Exception("User not found with username: " + username);
//        }
//
//        User existingUser = optionalUser.get();
//        if (passwordEncoder.matches(password, existingUser.getPassword())) {
//            throw new Exception("Wrong password");
//        }
//        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
//                username, password,
//                existingUser.getAuthorities()
//        );
//        authenticationManager.authenticate(authenticationToken);
//        return jwtTokenUtil.generateToken(existingUser);
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isEmpty()) {
            throw new Exception("User not found");
        }
        User existingUser = optionalUser.get();
        if (passwordEncoder.matches(password, existingUser.getPassword())) {
            throw new Exception("Wrong password");
        }
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                username, password,
                existingUser.getAuthorities()
        );
        authenticationManager.authenticate(authenticationToken);
        return jwtTokenUtil.generateToken(existingUser);
    }


    @Override
    public User validateUser(String username, String password) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found with username: " + username));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new InvalidPasswordException("Invalid password for username: " + username);
        }

        return user;
    }

    private UserDto convertToDTO(User user) {
        UserDto userDto = new UserDto();
        userDto.setUserId(user.getUserId());
        userDto.setUsername(user.getUsername());
        userDto.setPassword(null);
        userDto.setRoleId(user.getRole() != null
                ? user.getRole().getRoleId() : null);
        userDto.setBranchId(user.getBranch() != null
                ? user.getBranch().getId() : null);
        return userDto;
    }
}


//        Optional<User> user = userRepository.findByUsername(username);
//        if (user.isPresent()) {
//            return convertToDTO(user.get());
//        } else {
//            throw new RuntimeException("User not found");
//        }
//        User user = userRepository.findByUsername(username)
//                .orElseThrow(() -> new RuntimeException("User not found"));
//        return convertToDTO(user);*/
