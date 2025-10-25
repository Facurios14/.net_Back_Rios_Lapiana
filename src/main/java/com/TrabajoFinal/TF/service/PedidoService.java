package com.TrabajoFinal.TF.service;

import com.TrabajoFinal.TF.entity.DTO.PedidoRequestDto;
import com.TrabajoFinal.TF.entity.Pedido;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;
@Service
public interface PedidoService {
    Pedido create(PedidoRequestDto dto);
    List<Pedido> findAll();
    Optional<Pedido> findById(Long id);
    Pedido updateEstado(Long id, String estadoString);
    void deleteById(Long id);
}
