package br.com.raizesdonordeste.repository;

import br.com.raizesdonordeste.model.Categoria;
import br.com.raizesdonordeste.model.Produto;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ProdutoRepository {

    private List<Produto> produtos = new ArrayList<>();
    private int proximoId = 1;

    public Produto salvar(Produto produto) {
        if (produto.getId() == 0) {
            produto.setId(proximoId++);
        }
        produtos.add(produto);
        return produto;
    }

    public Optional<Produto> buscarPorId(int id) {
        return produtos.stream()
                .filter(p -> p.getId() == id)
                .findFirst();
    }

    public List<Produto> listarDisponiveis() {
        return produtos.stream()
                .filter(Produto::isDisponivel)
                .collect(Collectors.toList());
    }

    public List<Produto> listarPorCategoria(Categoria categoria) {
        return produtos.stream()
                .filter(p -> p.getCategoria() == categoria && p.isDisponivel())
                .collect(Collectors.toList());
    }

    public List<Produto> listarTodos() {
        return new ArrayList<>(produtos);
    }
}