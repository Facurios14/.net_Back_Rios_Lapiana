package com.TrabajoFinal.TF.service;

import com.TrabajoFinal.TF.entity.DTO.UserLoginRequest;
import com.TrabajoFinal.TF.entity.DTO.UserRegisterRequest;
import com.TrabajoFinal.TF.entity.DTO.UserResponse;
import com.TrabajoFinal.TF.entity.User;
import com.TrabajoFinal.TF.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encoded = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder hex = new StringBuilder();
            for (byte b : encoded) {
                String hexByte = Integer.toHexString(0xff & b);
                if (hexByte.length() == 1) hex.append('0');
                hex.append(hexByte);
            }
            return hex.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error al generar hash SHA-256", e);
        }
    }

    public UserResponse register(UserRegisterRequest request) {
        Optional<User> existing = userRepository.findByUsername(request.getUsername());
        if (existing.isPresent()) {
            throw new RuntimeException("El usuario ya existe");
        }
        String hashed = hashPassword(request.getPassword());
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(hashed);
        user.setRole(request.getRole());
        User saved = userRepository.save(user);
        return new UserResponse(saved.getId(), saved.getUsername(), saved.getRole());
    }

    public UserResponse login(UserLoginRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        String hashed = hashPassword(request.getPassword());
        if (!user.getPassword().equals(hashed)) {
            throw new RuntimeException("Contraseña incorrecta");
        }
        return new UserResponse(user.getId(), user.getUsername(), user.getRole());
    }

    public List<UserResponse> listAll() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(u -> new UserResponse(u.getId(), u.getUsername(), u.getRole()))
                .collect(Collectors.toList());
    }

    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("Usuario no encontrado");
        }
        userRepository.deleteById(id);
    }
}

