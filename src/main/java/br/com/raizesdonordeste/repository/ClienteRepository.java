package br.com.raizesdonordeste.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import br.com.raizesdonordeste.model.Cliente;

public class ClienteRepository {

    private List<Cliente> clientes = new ArrayList<>();
    private int proximoId = 1;

    public Cliente salvar(Cliente cliente) {
        if (cliente.getId() == 0) {
            cliente.setId(proximoId++);
        }
        clientes.add(cliente);
        return cliente;
    }

    public Optional<Cliente> buscarPorId(int id) {
        return clientes.stream()
                .filter(c -> c.getId() == id)
                .findFirst();
    }

    public Optional<Cliente> buscarPorEmail(String email) {
        return clientes.stream()
                .filter(c -> c.getEmail().equalsIgnoreCase(email))
                .findFirst();
    }

    public List<Cliente> listarTodos() {
        return new ArrayList<>(clientes);
    }
}