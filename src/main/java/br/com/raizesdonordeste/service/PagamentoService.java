package br.com.raizesdonordeste.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import br.com.raizesdonordeste.model.Pagamento;
import br.com.raizesdonordeste.model.Pedido;

public class PagamentoService {

    private List<Pagamento> pagamentos = new ArrayList<>();
    private int proximoId = 1;

    public Pagamento criarPagamento(Pedido pedido, String metodo) {
        if (pedido.estaVazio()) {
            throw new IllegalStateException("Não é possível pagar um pedido vazio!");
        }

        Pagamento pagamento = new Pagamento(proximoId++, pedido, pedido.calcularTotal(), metodo);
        pagamentos.add(pagamento);
        return pagamento;
    }

    // Simula a confirmação que viria do sistema externo de pagamento
    public void confirmarPagamento(Pagamento pagamento) {
        String referenciaExterna = UUID.randomUUID().toString();
        pagamento.confirmar(referenciaExterna);
        System.out.println("Pagamento confirmado! Referência: " + referenciaExterna);
    }

    // Simula a recusa que viria do sistema externo de pagamento
    public void recusarPagamento(Pagamento pagamento) {
        pagamento.recusar();
        System.out.println("Pagamento recusado pelo sistema externo.");
    }

    public void estornarPagamento(Pagamento pagamento) {
        pagamento.estornar();
        System.out.println("Pagamento estornado com sucesso.");
    }

    public List<Pagamento> listarTodos() {
        return new ArrayList<>(pagamentos);
    }
}