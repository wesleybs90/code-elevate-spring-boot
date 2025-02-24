package com.example.code_elevate.service;

import com.example.code_elevate.model.Produto;
import java.util.List;
import java.util.Optional;

public interface ProdutoService {
    Produto criarProduto(Produto produto);
    Optional<Produto> buscarProdutoPorId(String id);
    List<Produto> listarProdutos(int page, int size);
    Produto atualizarProduto(String id, Produto produto);
    void excluirProduto(String id);
}
