package com.TrabajoFinal.TF.service.impl;

import com.TrabajoFinal.TF.Rol;
import com.TrabajoFinal.TF.config.Sha256Util;
import com.TrabajoFinal.TF.entity.DTO.UserRegisterDto;
import com.TrabajoFinal.TF.entity.Usuario;
import com.TrabajoFinal.TF.repository.UserRepository;
import com.TrabajoFinal.TF.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    @Autowired
    public AuthServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Usuario register(UserRegisterDto dto) {
        if (userRepository.existByMail(dto.getMail())) {
            throw new RuntimeException("El email ya está registrado.");
        }

        // 1. Convertir DTO a Entidad
        Usuario usuario = new Usuario();
        usuario.setNombre(dto.getNombre());
        usuario.setApellido(dto.getApellido());
        usuario.setMail(dto.getMail());
        usuario.setCelular(dto.getCelular());
        usuario.setRol(Rol.USUARIO); // Asignación de rol por defecto

        // 2. Aplicar la SEGURIDAD: Hashear la contraseña
        String contrasenaHasheada = Sha256Util.hash(dto.getContrasena());
        usuario.setContrasena(contrasenaHasheada);

        // 3. Guardar y retornar la Entidad
        return userRepository.save(usuario);
    }

    @Override
    public Usuario login(String mail, String contrasena) {
        Usuario usuario =userRepository.findByMail(mail)
                .orElseThrow(() ->  new RuntimeException("Credenciales invalidas"));
        String contrasenaHasheada=Sha256Util.hash(contrasena);
        if (contrasenaHasheada.equals(usuario.getContrasena())) {
            return usuario;
        } else {
            throw new RuntimeException("Credenciales inválidas.");
        }
    }
    public List<Usuario> findAllUsers(){
        return userRepository.findAll();
    }
}

