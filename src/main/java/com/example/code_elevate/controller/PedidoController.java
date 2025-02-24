package com.example.code_elevate.controller;

import com.example.code_elevate.model.Pedido;
import com.example.code_elevate.service.PedidoService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

  private final PedidoService pedidoService;

  public PedidoController(PedidoService pedidoService) {
    this.pedidoService = pedidoService;
  }

  @PostMapping
  public ResponseEntity<Pedido> criarPedido(@RequestBody Pedido pedido) {
    Pedido novoPedido = pedidoService.criarPedido(pedido);
    return new ResponseEntity<>(novoPedido, HttpStatus.CREATED);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Pedido> buscarPedidoPorId(@PathVariable String id) {
    Optional<Pedido> pedido = pedidoService.buscarPedidoPorId(id);
    return pedido.map(ResponseEntity::ok)
      .orElse(ResponseEntity.notFound().build());
  }

  @GetMapping
  public ResponseEntity<List<Pedido>> listarPedidos(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size) {
    List<Pedido> pedidos = pedidoService.listarPedidos(page, size);
    return ResponseEntity.ok(pedidos);
  }

  @PutMapping("/{id}/status")
  public ResponseEntity<Pedido> atualizarPedidoStatus(@PathVariable String id, @RequestParam String status) {
    try {
      Pedido pedidoAtualizado = pedidoService.atualizarPedidoStatus(id, status);
      return ResponseEntity.ok(pedidoAtualizado);
    } catch (RuntimeException e) {
      return ResponseEntity.notFound().build();
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> excluirPedido(@PathVariable String id) {
    pedidoService.excluirPedido(id);
    return ResponseEntity.noContent().build();
  }
}