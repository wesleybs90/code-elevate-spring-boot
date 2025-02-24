package com.example.code_elevate.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "pedidos")
public record Pedido(
  @Id String id,
  String idCliente,
  List<ItemPedido> itens,
  Date dataPedido,
  String status,
  BigDecimal valorTotal) implements Serializable {
}