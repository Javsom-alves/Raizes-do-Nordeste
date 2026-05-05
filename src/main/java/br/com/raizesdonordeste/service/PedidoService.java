package br.com.raizesdonordeste.service;

import br.com.raizesdonordeste.model.*;
import br.com.raizesdonordeste.repository.PedidoRepository;
import br.com.raizesdonordeste.repository.ProdutoRepository;

import java.util.List;

public class PedidoService {

    private PedidoRepository pedidoRepository;
    private ProdutoRepository produtoRepository;
    private ClienteService clienteService;

    public PedidoService(PedidoRepository pedidoRepository,
                         ProdutoRepository produtoRepository,
                         ClienteService clienteService) {
        this.pedidoRepository = pedidoRepository;
        this.produtoRepository = produtoRepository;
        this.clienteService = clienteService;
    }

    public Pedido criarPedido(Cliente cliente, Unidade unidade, Canal canal) {
        Pedido pedido = new Pedido(0, cliente, unidade, canal);
        return pedidoRepository.salvar(pedido);
    }

    public void adicionarItem(Pedido pedido, int produtoId, int quantidade) {
        Produto produto = produtoRepository.buscarPorId(produtoId)
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado!"));

        pedido.adicionarItem(new ItemPedido(produto, quantidade));
    }

    public void atualizarStatus(Pedido pedido, StatusPedido novoStatus) {
        pedido.atualizarStatus(novoStatus);
    }

    public void finalizarPedido(Pedido pedido) {
        pedido.atualizarStatus(StatusPedido.FINALIZADO);

        // Adiciona pontos de fidelidade ao cliente ao finalizar
        clienteService.adicionarPontosPorPedido(pedido.getCliente(), pedido.calcularTotal());
    }

    public Pedido buscarPorId(int id) {
        return pedidoRepository.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Pedido não encontrado!"));
    }

    public List<Pedido> listarPorCliente(int clienteId) {
        return pedidoRepository.listarPorCliente(clienteId);
    }

    public List<Pedido> listarPorStatus(StatusPedido status) {
        return pedidoRepository.listarPorStatus(status);
    }
}