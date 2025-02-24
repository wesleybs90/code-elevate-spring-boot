package com.example.code_elevate.controller;

import com.example.code_elevate.model.Produto;
import com.example.code_elevate.service.ProdutoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

  private final ProdutoService produtoService;

  public ProdutoController(ProdutoService produtoService) {
    this.produtoService = produtoService;
  }

  @PostMapping
  public ResponseEntity<Produto> criarProduto(@RequestBody Produto produto) {
    Produto novoProduto = produtoService.criarProduto(produto);
    return new ResponseEntity<>(novoProduto, HttpStatus.CREATED);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Produto> buscarProdutoPorId(@PathVariable String id) {
    Optional<Produto> produto = produtoService.buscarProdutoPorId(id);
    return produto.map(ResponseEntity::ok)
      .orElse(ResponseEntity.notFound().build());
  }

  @GetMapping
  public ResponseEntity<List<Produto>> listarProdutos(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size) {
    List<Produto> produtos = produtoService.listarProdutos(page, size);
    return ResponseEntity.ok(produtos);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Produto> atualizarProduto(@PathVariable String id, @RequestBody Produto produto) {
    try {
      Produto produtoAtualizado = produtoService.atualizarProduto(id, produto);
      return ResponseEntity.ok(produtoAtualizado);
    } catch (RuntimeException e) {
      return ResponseEntity.notFound().build();
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> excluirProduto(@PathVariable String id) {
    produtoService.excluirProduto(id);
    return ResponseEntity.noContent().build();
  }
}
