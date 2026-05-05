package br.com.raizesdonordeste.repository;

import br.com.raizesdonordeste.model.Pedido;
import br.com.raizesdonordeste.model.StatusPedido;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class PedidoRepository {

    private List<Pedido> pedidos = new ArrayList<>();
    private int proximoId = 1;

    public Pedido salvar(Pedido pedido) {
        if (pedido.getId() == 0) {
            pedido.setId(proximoId++);
        }
        pedidos.add(pedido);
        return pedido;
    }

    public Optional<Pedido> buscarPorId(int id) {
        return pedidos.stream()
                .filter(p -> p.getId() == id)
                .findFirst();
    }

    public List<Pedido> listarPorCliente(int clienteId) {
        return pedidos.stream()
                .filter(p -> p.getCliente().getId() == clienteId)
                .collect(Collectors.toList());
    }

    public List<Pedido> listarPorStatus(StatusPedido status) {
        return pedidos.stream()
                .filter(p -> p.getStatus() == status)
                .collect(Collectors.toList());
    }

    public List<Pedido> listarTodos() {
        return new ArrayList<>(pedidos);
    }
}