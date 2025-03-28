package com.example.code_elevate.service;

import com.example.code_elevate.model.Pedido;
import com.example.code_elevate.repository.PedidoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PedidoServiceImpl implements PedidoService {

  private final PedidoRepository pedidoRepository;
  private final KafkaProducerService kafkaProducerService;
  private final ObjectMapper objectMapper;

  public PedidoServiceImpl(PedidoRepository pedidoRepository, KafkaProducerService kafkaProducerService,
      ObjectMapper objectMapper) {
    this.pedidoRepository = pedidoRepository;
    this.kafkaProducerService = kafkaProducerService;
    this.objectMapper = objectMapper;
  }

  @Override
  public Pedido criarPedido(Pedido pedido) {
    BigDecimal valorTotal = pedido.itens().stream()
      .map(item -> item.precoUnitario().multiply(BigDecimal.valueOf(item.quantidade())))
      .reduce(BigDecimal.ZERO, BigDecimal::add);

    Pedido pedidoComValorTotal = new Pedido(
      pedido.id(),
      pedido.idCliente(),
      pedido.itens(),
      new Date(),
      "PENDENTE",
      valorTotal);

    Pedido novoPedido = pedidoRepository.save(pedidoComValorTotal);

    try {
      // Chamada para enviar mensagem Kafka
      String message = objectMapper.writeValueAsString(novoPedido);
      kafkaProducerService.sendMessage("topic-update-stock", message);
    } catch (Exception e) {
      e.printStackTrace();
    }

    return novoPedido;
  }

  @Override
  @Cacheable(value = "pedidos", key = "#id")
  public Optional<Pedido> buscarPedidoPorId(String id) {
    System.out.println("Buscando pedido do banco de dados para ID: " + id);
    return pedidoRepository.findById(id);
  }

  @Override
  public List<Pedido> listarPedidos(int page, int size) {
    return pedidoRepository.findAll(PageRequest.of(page, size)).getContent();
  }

  @Override
  @CachePut(value = "pedidos", key = "#id")
  public Pedido atualizarPedidoStatus(String id, String status) {
    Pedido pedido = pedidoRepository.findById(id)
      .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));

    Pedido pedidoAtualizado = new Pedido(
      pedido.id(),
      pedido.idCliente(),
      pedido.itens(),
      pedido.dataPedido(),
      status,
      pedido.valorTotal()
    );
    return pedidoRepository.save(pedidoAtualizado);
  }

  @Override
  @CacheEvict(value = "pedidos", key = "#id")
  public void excluirPedido(String id) {
    pedidoRepository.deleteById(id);
  }
}