package com.TrabajoFinal.TF.repository;

import com.TrabajoFinal.TF.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByMail(String mail);
    boolean existByMail(String mail);
}

