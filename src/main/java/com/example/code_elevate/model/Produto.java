package com.example.code_elevate.model;

import java.io.Serializable;
import java.math.BigDecimal;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "produtos")
public record Produto(
  @Id String id,
  String nome,
  String descricao,
  BigDecimal preco,
  Integer estoque) implements Serializable {
}