package com.TrabajoFinal.TF.entity.mapper;

import com.TrabajoFinal.TF.entity.DTO.DetallePedidoResponseDto;
import com.TrabajoFinal.TF.entity.DTO.PedidoRequestDto;
import com.TrabajoFinal.TF.entity.DTO.PedidoResponseDto;
import com.TrabajoFinal.TF.entity.DetallePedido;
import com.TrabajoFinal.TF.entity.Pedido;
import com.TrabajoFinal.TF.entity.Producto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PedidoMapper {
    private PedidoMapper(){

    }
    public static Pedido toEntity(PedidoRequestDto dto){
        Pedido pedido=new Pedido();
        if (dto.getDetalles() != null) {
            pedido.setDetalles(dto.getDetalles().stream().map(detalleDto -> {
                DetallePedido detalle = new DetallePedido();
                detalle.setCantidad(detalleDto.getCantidad());

                Producto productoParcial = new Producto();
                productoParcial.setId(detalleDto.getProductoId());
                detalle.setProducto(productoParcial);

                detalle.setPedido(pedido); // Relación bidireccional
                return detalle;
            }).collect(Collectors.toList()));
        } else {
            pedido.setDetalles(new ArrayList<>());
        }
        return pedido;
    }
    public static DetallePedidoResponseDto toDetalleResponseDto(DetallePedido detalle){
        DetallePedidoResponseDto dto = new DetallePedidoResponseDto();
        dto.setId(detalle.getId());
        dto.setCantidad(detalle.getCantidad());
        dto.setSubtotal(detalle.getSubtotal());
        if (detalle.getProducto() !=null){
            dto.setProductoNombre(detalle.getProducto().getNombre());
            dto.setPrecioUnitario(detalle.getProducto().getPrecio());
        }
        return dto;
    }

    public static PedidoResponseDto toResponseDto(Pedido pedido) {
        PedidoResponseDto dto =new PedidoResponseDto();
        dto.setId(pedido.getId());
        dto.setFecha(pedido.getFecha());
        dto.setEstado(pedido.getEstado());
        dto.setTotal(pedido.getTotal());
        if (pedido.getUsuario() != null){
            dto.setUsuarioMail(pedido.getUsuario().getMail());
        }
        dto.setDetalles(pedido.getDetalles().stream()
                .map(PedidoMapper::toDetalleResponseDto)
                .collect(Collectors.toList()));
        return dto; // Placeholder
    }
    public static List<PedidoResponseDto> toResponseDtoList(List<Pedido> pedidos){
        return pedidos.stream().map(PedidoMapper::toResponseDto).collect(Collectors.toList());
    }
}
