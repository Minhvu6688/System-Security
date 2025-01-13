package com.project.System.Security.service;

import com.project.System.Security.Exception.InvalidPasswordException;
import com.project.System.Security.Exception.ResourceNotFoundException;
import com.project.System.Security.Exception.UserNotFoundException;
import com.project.System.Security.components.JwtTokenUtil;
import com.project.System.Security.controller.UserController;
import com.project.System.Security.dto.UserDto;
import com.project.System.Security.model.Branch;
import com.project.System.Security.model.Role;
import com.project.System.Security.model.User;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.project.System.Security.repositories.BranchRepository;
import com.project.System.Security.repositories.RoleRepository;
import com.project.System.Security.repositories.UserRepository;

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
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

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
        log.info("Creating user with username: {}", userDto.getUsername());

        // Kiểm tra username có tồn tại không
        if (userRepository.existsByUsername(userDto.getUsername())) {
            throw new IllegalArgumentException("Username already exists: " + userDto.getUsername());
        }

        // Kiểm tra roleId
        Role role = roleRepository.findById(Math.toIntExact(userDto.getRoleId()))
                .orElseThrow(() -> new IllegalArgumentException("Role not found with ID: " + userDto.getRoleId()));

        // Kiểm tra branchId
        Branch branch = branchRepository.findById(userDto.getBranchId())
                .orElseThrow(() -> new IllegalArgumentException("Branch not found with ID: " + userDto.getBranchId()));

        log.info("Role found: {}", role.getRoleName());
        log.info("Branch found: {}", branch.getName());

        // Chuyển đổi DTO thành Entity
        User user = modelMapper.map(userDto, User.class);
        user.setRole(role);
        user.setBranch(branch);

        // Mã hóa mật khẩu trước khi lưu
        String encodedPassword = passwordEncoder.encode(userDto.getPassword());
        user.setPassword(encodedPassword);

        // Lưu user vào DB
        user = userRepository.save(user);

        return modelMapper.map(user, UserDto.class);
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
            Role role = roleRepository.findById(Math.toIntExact(userDTO.getRoleId()))
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
    public List<UserDto> getUsersByRoleId(Long roleId) {
        List<User> users = userRepository.findByRoleId(Math.toIntExact(roleId));
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
        log.info("Attempting login for username: {}", username);

        // Tìm user theo username
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isEmpty()) {
            log.warn("Login failed. User not found for username: {}", username);
            throw new Exception("User not found");
        }

        User user = optionalUser.get();
        log.info("User found with username: {}", user.getUsername());

        // Kiểm tra mật khẩu
        if (!passwordEncoder.matches(password, user.getPassword())) {
            log.warn("Login failed. Incorrect password for username: {}", username);
            throw new Exception("Wrong password");
        }

        log.info("Login successful for username: {}", username);

        // Tạo token xác thực
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                user,
                null,
                user.getAuthorities()
        );
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        return JwtTokenUtil.generateToken(user);
    }

    @Override
    public User validateUser(String username, String password) {
        // Tìm người dùng theo tên người dùng
        Optional<User> optionalUser = userRepository.findByUsername(username);

        // Kiểm tra xem người dùng có tồn tại không
        if (optionalUser.isEmpty()) {
            throw new UserNotFoundException("User not found with username: " + username);
        }

        // Lấy người dùng từ Optional
        User user = optionalUser.get();

        // Kiểm tra mật khẩu
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new InvalidPasswordException("Invalid password for username: " + username);
        }
        log.info("Login successful for username: {}", username);
        return user;
    }

    private UserDto convertToDTO(User user) {
        UserDto userDto = new UserDto();
        userDto.setUserId(user.getUserId());
        userDto.setUsername(user.getUsername());
        userDto.setPassword(null);
        userDto.setRoleId(Long.valueOf(user.getRole() != null
                ? user.getRole().getRoleId() : null));
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
//        return convertToDTO(user);