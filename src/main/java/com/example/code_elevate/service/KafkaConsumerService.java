package com.example.code_elevate.service;

import com.example.code_elevate.model.Pedido;
import com.example.code_elevate.model.Produto;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class KafkaConsumerService {

  private final ProdutoService produtoService;
  private final ObjectMapper objectMapper;

  public KafkaConsumerService(ProdutoService produtoService, ObjectMapper objectMapper) {
    this.produtoService = produtoService;
    this.objectMapper = objectMapper;
  }

  @KafkaListener(topics = "topic-update-stock", groupId = "group-update-stock")
  public void consume(String message) {
    try {
      // Parse do Pedido
      Pedido pedido = objectMapper.readValue(message, Pedido.class);

      // Atualiza estoque dos produtos
      pedido.itens().forEach(item -> {
        System.out.println("Atualizando estoque para o produto ID: " + item.idProduto());
        
        produtoService.buscarProdutoPorId(item.idProduto()).ifPresent(produto -> {
          int novoEstoque = produto.estoque() - item.quantidade();
          Produto produtoAtualizado = new Produto(produto.id(), produto.nome(), produto.descricao(), produto.preco(), novoEstoque);
          produtoService.atualizarProduto(produto.id(), produtoAtualizado);
        });
      });
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}