package com.TrabajoFinal.TF.Controller;

import com.TrabajoFinal.TF.entity.DTO.PedidoDto;
import com.TrabajoFinal.TF.entity.DTO.PedidoRequestDto;
import com.TrabajoFinal.TF.entity.DTO.PedidoResponseDto;
import com.TrabajoFinal.TF.entity.Pedido;
import com.TrabajoFinal.TF.entity.mapper.PedidoMapper;
import com.TrabajoFinal.TF.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {
    private final PedidoService pedidoService;

    @Autowired
    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @PostMapping
    public ResponseEntity<PedidoResponseDto> createPedido(@RequestBody PedidoRequestDto dto) {
        try {
            Pedido nuevoPedido = pedidoService.create(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(PedidoMapper.toResponseDto(nuevoPedido)); // 201 Created
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build(); // 400 Bad Request
        }
    }

    @GetMapping
    public ResponseEntity<List<PedidoResponseDto>> getAllPedidos() {
        List<Pedido> pedidos=pedidoService.findAll();
        return ResponseEntity.ok(PedidoMapper.toResponseDtoList(pedidos));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponseDto> getPedidoById(@PathVariable Long id) {
        return pedidoService.findById(id)
                .map(PedidoMapper::toResponseDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<PedidoResponseDto> updateEstado(
            @PathVariable Long id,
            @RequestParam String nuevoEstado)
    {
        try {
            Pedido pedidoActualizado = pedidoService.updateEstado(id, nuevoEstado);
            return ResponseEntity.ok(PedidoMapper.toResponseDto(pedidoActualizado));
        } catch (RuntimeException e) {
            // Maneja error si el estado no existe o el ID no se encuentra
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build(); // 400 Bad Request
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePedido(@PathVariable Long id) {
        pedidoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
