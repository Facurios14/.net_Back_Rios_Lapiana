package com.TrabajoFinal.TF.Controller;


import com.TrabajoFinal.TF.entity.DTO.UserLoginDto;
import com.TrabajoFinal.TF.entity.DTO.UserRegisterDto;
import com.TrabajoFinal.TF.entity.DTO.UserResponseDto;
import com.TrabajoFinal.TF.entity.Usuario;
import com.TrabajoFinal.TF.entity.mapper.UserMapper;
import com.TrabajoFinal.TF.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin (origins = "http://localhost:5173") // frontend Vite
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private AuthService authService;
    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> register(@Validated @RequestBody UserRegisterDto dto) {

        try {
            Usuario nuevoUsuarioEntidad = authService.register(dto);
            UserResponseDto responseDto = UserMapper.toResponseDto(nuevoUsuarioEntidad);

            return ResponseEntity
                    .status(HttpStatus.CREATED) // 201 Created
                    .body(responseDto);

        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST) // 400 Bad Request
                    .build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponseDto> login(@RequestBody UserLoginDto dto) {
        Usuario usuarioAutenticado = authService.login(dto.getMail(), dto.getContrasena());
        UserResponseDto responseDto = UserMapper.toResponseDto(usuarioAutenticado);
        return ResponseEntity.status(201).body(responseDto);
    }
    @GetMapping("/users")
    public ResponseEntity<List<UserResponseDto>> getUsers(){
        List<Usuario> usuarios = authService.findAllUsers();
        List<UserResponseDto> responseDtoList = UserMapper.toResponseDtoList(usuarios);
        return ResponseEntity.ok(responseDtoList);
    }
}

