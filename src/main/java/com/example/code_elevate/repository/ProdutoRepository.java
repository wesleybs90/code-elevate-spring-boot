package com.example.code_elevate.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.example.code_elevate.model.Produto;

@Repository
public interface ProdutoRepository extends MongoRepository<Produto, String> {

}
