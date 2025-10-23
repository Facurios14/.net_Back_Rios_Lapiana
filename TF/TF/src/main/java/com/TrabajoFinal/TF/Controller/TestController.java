package com.TrabajoFinal.TF.Controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*") // permite conexión desde el front (Vite)
public class TestController {

    @GetMapping("/api/hello")
    public String hello() {
        return "✅ Backend conectado correctamente con el Frontend";
    }
}
