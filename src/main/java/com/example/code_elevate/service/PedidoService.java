package com.example.code_elevate.service;

import com.example.code_elevate.model.Pedido;
import java.util.List;
import java.util.Optional;

public interface PedidoService {
    Pedido criarPedido(Pedido pedido);
    Optional<Pedido> buscarPedidoPorId(String id);
    List<Pedido> listarPedidos(int page, int size);
    Pedido atualizarPedidoStatus(String id, String status);
    void excluirPedido(String id);
}