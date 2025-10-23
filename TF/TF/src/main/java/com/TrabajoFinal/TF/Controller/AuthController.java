package com.TrabajoFinal.TF.Controller;


import com.TrabajoFinal.TF.entity.User;
import com.TrabajoFinal.TF.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:5173") // frontend Vite
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User userRequest) {
        try {
            User newUser = userService.register(userRequest.getUsername(), userRequest.getPassword(), userRequest.getRole());
            // No devolvemos la contraseña
            newUser.setPassword(null);
            return ResponseEntity.ok(newUser);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User userRequest) {
        try {
            User user = userService.login(userRequest.getUsername(), userRequest.getPassword());
            user.setPassword(null);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

