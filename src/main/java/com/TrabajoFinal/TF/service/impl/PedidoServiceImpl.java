package com.TrabajoFinal.TF.service.impl;

import com.TrabajoFinal.TF.Estado;
import com.TrabajoFinal.TF.entity.DTO.DetallePedidoRequestDto;
import com.TrabajoFinal.TF.entity.DTO.PedidoRequestDto;
import com.TrabajoFinal.TF.entity.DetallePedido;
import com.TrabajoFinal.TF.entity.Pedido;
import com.TrabajoFinal.TF.entity.Producto;
import com.TrabajoFinal.TF.entity.Usuario;
import com.TrabajoFinal.TF.repository.PedidoRepository;
import com.TrabajoFinal.TF.repository.ProductoRepository;
import com.TrabajoFinal.TF.repository.UserRepository;
import com.TrabajoFinal.TF.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PedidoServiceImpl implements PedidoService {
    private PedidoRepository pedidoRepository;
    private ProductoRepository productoRepository;
    private UserRepository userRepository;
    @Autowired
    public PedidoServiceImpl(PedidoRepository pedidoRepository,ProductoRepository productoRepository,UserRepository userRepository){
        this.pedidoRepository=pedidoRepository;
        this.productoRepository=productoRepository;
        this.userRepository=userRepository;
    }
    @Override
    public Pedido create(PedidoRequestDto dto){
        Usuario usuario =userRepository.findById(dto.getUsuarioId())
                .orElseThrow(()-> new RuntimeException("Usuario no Encontrado por id"+ dto.getUsuarioId()));
        Pedido pedido=new Pedido();
        pedido.setUsuario(usuario);
        pedido.setDetalles(new ArrayList<>());
        double totalCalculado=0.0;

        for (DetallePedidoRequestDto detalleDto : dto.getDetalles()) {

            Producto producto = productoRepository.findById(detalleDto.getProductoId())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado con id"+ detalleDto.getProductoId()));
            Integer cantidadSolicitada = detalleDto.getCantidad();
            if (cantidadSolicitada<=0){
                throw new IllegalArgumentException("La cantidad de producto debe ser positiva.");
            }
            if ((producto.getStock() == null) || producto.getStock() < cantidadSolicitada){
                throw new RuntimeException("Stock insuficiente para el producto: "+ producto.getNombre()+". Disponible: "+producto.getStock());
            }
            producto.setStock(producto.getStock()-cantidadSolicitada);
            productoRepository.save(producto);
            DetallePedido detalle = new DetallePedido();
            detalle.setCantidad(cantidadSolicitada);
            detalle.setProducto(producto);
            double subtotal = producto.getPrecio() * detalle.getCantidad();
            detalle.setSubtotal(subtotal);
            detalle.setPedido(pedido);
            pedido.getDetalles().add(detalle);
            totalCalculado += subtotal;
        }
        pedido.setFecha(LocalDate.now());
        pedido.setEstado(Estado.PENDIENTE);
        pedido.setTotal(totalCalculado);
        return pedidoRepository.save(pedido);
    }
    @Override
    public List<Pedido> findAll() {
        return pedidoRepository.findAll();
    }

    @Override
    public Optional<Pedido> findById(Long id) {
        return pedidoRepository.findById(id);
    }

    @Override
    public Pedido updateEstado(Long id, String estadoString) {
        try {
            Estado nuevoEstado = Estado.valueOf(estadoString.toUpperCase());
            return pedidoRepository.findById(id).map(pedido -> {
                pedido.setEstado(nuevoEstado);
                return pedidoRepository.save(pedido);
            }).orElseThrow(() -> new RuntimeException("Pedido no encontrado con ID: " + id));
        }catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Estado de pedido inválido: " + estadoString);
        }
    }

    @Override
    public void deleteById(Long id) {
        pedidoRepository.deleteById(id);
    }
}
