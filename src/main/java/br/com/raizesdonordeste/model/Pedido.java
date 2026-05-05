package br.com.raizesdonordeste.model;

import java.util.ArrayList;
import java.util.List;

public class Pedido {

    private int id;
    private Cliente cliente;
    private Unidade unidade;
    private Canal canal;
    private List<ItemPedido> itens = new ArrayList<>();
    private StatusPedido status;

    public Pedido() {
    }

    public Pedido(int id, Cliente cliente, Unidade unidade, Canal canal) {
        this.id = id;
        this.cliente = cliente;
        this.unidade = unidade;
        this.canal = canal;
        this.status = StatusPedido.CRIADO;
    }

    public void atualizarStatus(StatusPedido novoStatus) {

        // Pode cancelar de qualquer estado (menos se já finalizou ou cancelou)
        if (novoStatus == StatusPedido.CANCELADO) {
            if (this.status == StatusPedido.FINALIZADO || this.status == StatusPedido.CANCELADO) {
                throw new IllegalStateException("Pedido já finalizado ou cancelado!");
            }
            this.status = novoStatus;
            return;
        }

        if (this.status == StatusPedido.CRIADO && novoStatus == StatusPedido.EM_PREPARO) {
            this.status = novoStatus;
        } else if (this.status == StatusPedido.EM_PREPARO && novoStatus == StatusPedido.PRONTO) {
            this.status = novoStatus;
        } else if (this.status == StatusPedido.PRONTO && novoStatus == StatusPedido.FINALIZADO) {
            if (estaVazio()) {
                throw new IllegalStateException("Pedido não pode ser finalizado vazio!");
            }
            this.status = novoStatus;
        } else {
            throw new IllegalStateException("Não é possível mudar de " + this.status + " para " + novoStatus);
        }
    }

    public boolean estaVazio() {
        return itens.isEmpty();
    }

    public double calcularTotal() {
        double total = 0;
        for (ItemPedido item : itens) {
            total += item.getProduto().getPreco() * item.getQuantidade();
        }
        return total;
    }

    public void adicionarItem(ItemPedido novoItem) {
        if (novoItem.getQuantidade() <= 0) {
            throw new IllegalArgumentException("Quantidade inválida!");
        }
        if (!novoItem.getProduto().isDisponivel()) {
            throw new IllegalArgumentException("Produto indisponível: " + novoItem.getProduto().getNome());
        }

        for (ItemPedido item : itens) {
            if (item.getProduto().getId() == novoItem.getProduto().getId()) {
                item.setQuantidade(item.getQuantidade() + novoItem.getQuantidade());
                return;
            }
        }

        itens.add(novoItem);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Unidade getUnidade() {
        return unidade;
    }

    public void setUnidade(Unidade unidade) {
        this.unidade = unidade;
    }

    public Canal getCanal() {
        return canal;
    }

    public void setCanal(Canal canal) {
        this.canal = canal;
    }

    public List<ItemPedido> getItens() {
        return new ArrayList<>(itens);
    }

    public StatusPedido getStatus() {
        return status;
    }
}