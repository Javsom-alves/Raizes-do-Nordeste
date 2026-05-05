package br.com.raizesdonordeste.service;

import java.util.List;

import br.com.raizesdonordeste.model.Cliente;
import br.com.raizesdonordeste.repository.ClienteRepository;

public class ClienteService {

    private ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public Cliente cadastrar(String nome, String email, boolean aceitaLgpd) {
        // Verifica se já existe cliente com esse email
        if (clienteRepository.buscarPorEmail(email).isPresent()) {
            throw new IllegalArgumentException("Já existe um cliente com esse email!");
        }

        Cliente cliente = new Cliente(0, nome, email);

        // Registra consentimento LGPD se o cliente aceitou
        if (aceitaLgpd) {
            cliente.registrarConsentimento();
        }

        return clienteRepository.salvar(cliente);
    }

    public Cliente buscarPorId(int id) {
        return clienteRepository.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado!"));
    }

    public List<Cliente> listarTodos() {
        return clienteRepository.listarTodos();
    }

    // Adiciona pontos ao cliente após finalizar um pedido (1 ponto por real gasto)
    public void adicionarPontosPorPedido(Cliente cliente, double totalPedido) {
        if (!cliente.possuiConsentimento()) {
            return; // Sem consentimento LGPD, não participa do programa de fidelidade
        }
        int pontos = (int) totalPedido;
        cliente.adicionarPontos(pontos);
    }

    public void resgatarPontos(Cliente cliente, int pontos) {
        if (!cliente.possuiConsentimento()) {
            throw new IllegalStateException("Cliente não aceitou os termos do programa de fidelidade!");
        }
        cliente.resgatarPontos(pontos);
    }

    public void revogarConsentimento(Cliente cliente) {
        cliente.revogarConsentimento();
    }
}