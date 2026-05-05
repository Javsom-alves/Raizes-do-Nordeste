# 🌵 Raízes do Nordeste — Sistema de Gestão de Pedidos

Aluno: Javsom Alves da Silva  
Curso: Análise e Desenvolvimento de Sistemas  
Projeto: Projeto Multidisciplinar — Entrega Final

---

## 📖 Sobre o Projeto

Sistema desenvolvido para gerenciar os pedidos da rede de lanchonetes **Raízes do Nordeste**, contemplando múltiplos canais de atendimento, controle de estoque por unidade, programa de fidelidade com conformidade à LGPD, pagamentos desacoplados e auditoria de operações.

---

##  Arquitetura

O projeto foi organizado em três camadas principais:

```
src/
└── br/com/raizesdonordeste/
    ├── main/          → Ponto de entrada da aplicação (Main.java)
    ├── model/         → Entidades do domínio
    ├── repository/    → Persistência em memória
    └── service/       → Regras de negócio
```

### Por que essa arquitetura?

A separação em camadas foi escolhida para garantir:
- **Manutenibilidade** — cada classe tem uma responsabilidade clara
- **Escalabilidade** — os repositórios em memória podem ser substituídos por banco de dados sem alterar os services
- **Testabilidade** — as regras de negócio ficam isoladas nos services

---

## 📦 Entidades (model)

| Classe | Descrição |
|---|---|
| `Cliente` | Dados do cliente, pontos de fidelidade e consentimento LGPD |
| `Produto` | Produto com categoria e flag de disponibilidade |
| `Pedido` | Pedido com canal, unidade e máquina de estados |
| `ItemPedido` | Item dentro de um pedido |
| `Unidade` | Loja física da rede |
| `Pagamento` | Registro de pagamento integrado com sistema externo |
| `CardapioUnidade` | Cardápio específico por unidade com preço local |
| `EstoqueUnidade` | Controle de estoque por unidade |
| `RegistroAuditoria` | Log de operações sensíveis |
| `Canal` | Enum: APP, TOTEM, BALCAO, RETIRADA |
| `Categoria` | Enum: PRATO_PRINCIPAL, LANCHE, BEBIDA, etc |
| `StatusPedido` | Enum: CRIADO, EM_PREPARO, PRONTO, FINALIZADO, CANCELADO |
| `StatusPagamento` | Enum: PENDENTE, APROVADO, RECUSADO, ESTORNADO |

---

## ⚙️ Serviços (service)

| Service | Responsabilidade |
|---|---|
| `ClienteService` | Cadastro, LGPD e programa de fidelidade |
| `PedidoService` | Criação de pedidos, itens, status e auditoria |
| `PagamentoService` | Integração simulada com sistema externo de pagamento |
| `AuditoriaService` | Registro de cancelamentos e finalizações |
| `RelatorioService` | Relatórios de vendas e produtos mais consumidos para a matriz |

---

## 🔑 Decisões Técnicas

### Programa de Fidelidade + LGPD
O cliente só acumula pontos se der consentimento explícito. O sistema guarda a data do consentimento e permite revogação a qualquer momento, apagando dados pessoais sensíveis (direito ao esquecimento).

### Pagamento Desacoplado
O sistema não processa pagamentos diretamente. O `PagamentoService` simula a integração com um sistema externo, registrando apenas o resultado (aprovado, recusado, estornado) e a referência externa. Isso reduz riscos e melhora a segurança.

### Cardápio por Unidade
Cada unidade tem seu próprio cardápio com preços locais e produtos sazonais. Um produto pode estar disponível em uma unidade e indisponível em outra.

### Máquina de Estados do Pedido
O pedido segue um fluxo controlado: `CRIADO → EM_PREPARO → PRONTO → FINALIZADO`. Cancelamento é permitido em qualquer estado, exceto após finalizado. Transições inválidas lançam exceção.

### Auditoria
Operações sensíveis como cancelamentos e finalizações são registradas automaticamente com data, hora e detalhes, garantindo rastreabilidade para a matriz.

---

## 🚀 Como executar

1. Clone o repositório
2. Importe o projeto no Eclipse como **Java Project**
3. Execute a classe `Main.java` em `br.com.raizesdonordeste.main`
4. Siga o menu interativo no console

---

## 📋 Requisitos Atendidos

- [x] Múltiplos canais de atendimento (App, Totem, Balcão, Retirada)
- [x] Cardápio por unidade com preços locais
- [x] Controle de estoque por unidade
- [x] Programa de fidelidade com pontos
- [x] Conformidade com LGPD (consentimento e revogação)
- [x] Pagamento desacoplado de sistema externo
- [x] Acompanhamento de status do pedido
- [x] Auditoria de operações sensíveis
- [x] Relatórios para a matriz
- [x] Arquitetura em camadas (model, repository, service)
