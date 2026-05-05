package br.com.raizesdonordeste.model;

public class Pagamento {

    private int id;
    private Pedido pedido;
    private double valor;
    private String metodo;
    private StatusPagamento status;
    private String referenciaExterna;

    public Pagamento() {
    }

    public Pagamento(int id, Pedido pedido, double valor, String metodo) {
        this.id = id;
        this.pedido = pedido;
        this.valor = valor;
        this.metodo = metodo;
        this.status = StatusPagamento.PENDENTE;
    }

    public void confirmar(String referenciaExterna) {
        if (this.status != StatusPagamento.PENDENTE) {
            throw new IllegalStateException("Pagamento já foi processado!");
        }
        this.status = StatusPagamento.APROVADO;
        this.referenciaExterna = referenciaExterna;
    }

    public void recusar() {
        if (this.status != StatusPagamento.PENDENTE) {
            throw new IllegalStateException("Pagamento já foi processado!");
        }
        this.status = StatusPagamento.RECUSADO;
    }

    public void estornar() {
        if (this.status != StatusPagamento.APROVADO) {
            throw new IllegalStateException("Só é possível estornar pagamentos aprovados!");
        }
        this.status = StatusPagamento.ESTORNADO;
    }

    public int getId() { return id; }
    public Pedido getPedido() { return pedido; }
    public double getValor() { return valor; }
    public String getMetodo() { return metodo; }
    public StatusPagamento getStatus() { return status; }
    public String getReferenciaExterna() { return referenciaExterna; }
    public void setReferenciaExterna(String referenciaExterna) {
        this.referenciaExterna = referenciaExterna;
    }
}