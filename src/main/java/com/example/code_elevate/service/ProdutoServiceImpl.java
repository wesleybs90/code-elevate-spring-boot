package com.example.code_elevate.service;

import com.example.code_elevate.model.Produto;
import com.example.code_elevate.repository.ProdutoRepository;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoServiceImpl implements ProdutoService {

  private final ProdutoRepository produtoRepository;

  public ProdutoServiceImpl(ProdutoRepository produtoRepository) {
    this.produtoRepository = produtoRepository;
  }

  @Override
  public Produto criarProduto(Produto produto) {
    return produtoRepository.save(produto);
  }

  @Override
  @Cacheable(value = "produtos", key = "#id")
  public Optional<Produto> buscarProdutoPorId(String id) {
    System.out.println("Buscando produto do banco de dados para ID: " + id);
    return produtoRepository.findById(id);
  }

  @Override
  public List<Produto> listarProdutos(int page, int size) {
    return produtoRepository.findAll(PageRequest.of(page, size)).getContent();
  }

  @Override
  @CachePut(value = "produtos", key = "#id")
  public Produto atualizarProduto(String id, Produto produto) {
    produtoRepository.findById(id)
      .orElseThrow(() -> new RuntimeException("Produto não encontrado"));
    Produto produtoAtualizado = new Produto(
      id,
      produto.nome(), 
      produto.descricao(), 
      produto.preco(), 
      produto.estoque()
    );
    return produtoRepository.save(produtoAtualizado);
  }

  @Override
  @CacheEvict(value = "produtos", key = "#id")
  public void excluirProduto(String id) {
    produtoRepository.deleteById(id);
  }
}