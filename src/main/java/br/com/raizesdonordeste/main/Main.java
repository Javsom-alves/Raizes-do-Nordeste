package br.com.raizesdonordeste.main;

import java.util.Scanner;

import br.com.raizesdonordeste.model.*;
import br.com.raizesdonordeste.repository.*;
import br.com.raizesdonordeste.service.*;

public class Main {

	// REPOSITÓRIOS
	static ClienteRepository clienteRepository = new ClienteRepository();
	static ProdutoRepository produtoRepository = new ProdutoRepository();
	static PedidoRepository pedidoRepository = new PedidoRepository();

	// SERVICES
	static ClienteService clienteService = new ClienteService(clienteRepository);
	static PedidoService pedidoService = new PedidoService(pedidoRepository, produtoRepository, clienteService);
	static PagamentoService pagamentoService = new PagamentoService();

	static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) {

		System.out.println("=== BEM-VINDO À RAÍZES DO NORDESTE ===");

		// CADASTRO DO CLIENTE
		System.out.print("Digite seu nome: ");
		String nome = sc.nextLine();

		System.out.print("Digite seu email: ");
		String email = sc.nextLine();

		System.out.print(
				"Deseja participar do programa de fidelidade? (aceita uso dos seus dados conforme LGPD) [S/N]: ");
		boolean aceitaLgpd = sc.nextLine().equalsIgnoreCase("S");

		Cliente cliente = clienteService.cadastrar(nome, email, aceitaLgpd);

		if (aceitaLgpd) {
			System.out.println("Consentimento registrado! Você acumulará pontos a cada pedido.");
		} else {
			System.out.println("Sem problema! Você pode aceitar futuramente.");
		}

		// UNIDADE E CANAL (fixos para simulação)
		Unidade unidade = new Unidade(1, "Raízes do Nordeste - Centro", "Recife", "Rua do Bom Jesus, 100");
		Canal canal = Canal.BALCAO;

		// PRODUTOS
		Produto cuscuz = produtoRepository.salvar(new Produto(0, "Cuscuz", 10.0, Categoria.PRATO_PRINCIPAL));
		Produto tapioca = produtoRepository.salvar(new Produto(0, "Tapioca", 8.0, Categoria.LANCHE));
		Produto cafe = produtoRepository.salvar(new Produto(0, "Café", 5.0, Categoria.BEBIDA));

		// CRIAÇÃO DO PEDIDO
		Pedido pedido = pedidoService.criarPedido(cliente, unidade, canal);

		// MENU DE PEDIDO
		int opcao;
		do {
			System.out.println("\n=== CARDÁPIO ===");
			System.out.println("1 - Cuscuz (R$10)");
			System.out.println("2 - Tapioca (R$8)");
			System.out.println("3 - Café (R$5)");
			System.out.println("4 - Ver pedido");
			System.out.println("0 - Fechar pedido");

			System.out.print("Escolha: ");
			opcao = Integer.parseInt(sc.nextLine());

			switch (opcao) {
			case 1:
			case 2:
			case 3:
				System.out.print("Quantidade: ");
				int qtd = Integer.parseInt(sc.nextLine());
				try {
					int produtoId = opcao == 1 ? cuscuz.getId() : opcao == 2 ? tapioca.getId() : cafe.getId();
					pedidoService.adicionarItem(pedido, produtoId, qtd);
					System.out.println("Item adicionado!");
				} catch (Exception e) {
					System.out.println("Erro: " + e.getMessage());
				}
				break;
			case 4:
				mostrarPedido(pedido);
				break;
			}

		} while (opcao != 0);

		// VERIFICA SE PEDIDO ESTÁ VAZIO
		if (pedido.estaVazio()) {
			System.out.println("\nPedido vazio. Encerrando...");
			sc.close();
			return;
		}

		// RESUMO FINAL
		System.out.println("\n=== RESUMO FINAL DO PEDIDO ===");
		mostrarPedido(pedido);
		System.out.println("Canal: " + pedido.getCanal());
		System.out.println("Unidade: " + pedido.getUnidade().getNome());

		// PAGAMENTO
		System.out.println("\n=== PAGAMENTO ===");
		System.out.println("1 - PIX");
		System.out.println("2 - Cartão de Crédito");
		System.out.println("3 - Cartão de Débito");
		System.out.println("4 - Dinheiro");
		System.out.print("Escolha a forma de pagamento: ");
		int pagOpcao = Integer.parseInt(sc.nextLine());

		String metodo;
		switch (pagOpcao) {
		case 1:
			metodo = "PIX";
			break;
		case 2:
			metodo = "CARTAO_CREDITO";
			break;
		case 3:
			metodo = "CARTAO_DEBITO";
			break;
		default:
			metodo = "DINHEIRO";
			break;
		}

		Pagamento pagamento = pagamentoService.criarPagamento(pedido, metodo);
		pagamentoService.confirmarPagamento(pagamento);

		if (pagamento.getStatus() != StatusPagamento.APROVADO) {
			System.out.println("Pagamento não aprovado. Tente novamente.");
			sc.close();
			return;
		}

		System.out.println("\nObrigado pelo pedido, " + cliente.getNome() + "!");
		System.out.println("Seu pedido foi enviado para a cozinha!");

		// ACOMPANHAMENTO DE STATUS
		int statusOpcao;
		do {
			System.out.println("\n=== ACOMPANHAR PEDIDO ===");
			System.out.println("Status atual: " + pedido.getStatus());
			System.out.println("1 - Iniciar preparo");
			System.out.println("2 - Marcar como pronto");
			System.out.println("3 - Finalizar pedido");
			System.out.println("4 - Cancelar pedido");
			System.out.println("0 - Sair");

			System.out.print("Escolha: ");
			statusOpcao = Integer.parseInt(sc.nextLine());

			try {
				switch (statusOpcao) {
				case 1:
					pedidoService.atualizarStatus(pedido, StatusPedido.EM_PREPARO);
					break;
				case 2:
					pedidoService.atualizarStatus(pedido, StatusPedido.PRONTO);
					break;
				case 3:
					pedidoService.finalizarPedido(pedido);
					System.out.println("Pedido finalizado!");
					if (cliente.possuiConsentimento()) {
						System.out.println("Pontos acumulados: " + cliente.getPontosFidelidade());
					}
					break;
				case 4:
					pedidoService.atualizarStatus(pedido, StatusPedido.CANCELADO);
					break;
				}
			} catch (Exception e) {
				System.out.println("Erro: " + e.getMessage());
			}

		} while (statusOpcao != 0 && pedido.getStatus() != StatusPedido.FINALIZADO
				&& pedido.getStatus() != StatusPedido.CANCELADO);

		System.out.println("\nSistema encerrado.");
		sc.close();
	}

	private static void mostrarPedido(Pedido pedido) {
		System.out.println("\n=== SEU PEDIDO ===");
		for (ItemPedido item : pedido.getItens()) {
			System.out.printf("%-10s x%d  R$%.2f%n", item.getProduto().getNome(), item.getQuantidade(),
					item.getProduto().getPreco() * item.getQuantidade());
		}
		System.out.printf("Total: R$%.2f%n", pedido.calcularTotal());
	}
}