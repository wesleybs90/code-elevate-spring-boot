package com.example.code_elevate.model;

import java.math.BigDecimal;

public record ItemPedido(
  String idProduto,
  Integer quantidade,
  BigDecimal precoUnitario) {
}