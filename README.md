# Sevenfood Order API

Essa API serve para criar pedidos baseados nos produtos escolhidos e no cliente que realizou a compra. A API se comunica com outras APIs para buscar dados de produtos, dados de clientes, enviar informações de pagamento e gerenciar a produção e o status dos pedidos via SQS.

## Stack Utilizada

- Java 17
- Spring Boot 3
- Flyway
- IntelliJ IDEA
- PostgreSQL 12 (PGAdmin)
- Docker & Docker Compose
- Nginx como reverse proxy
- Swagger (OpenAPI)
- JUnit 5
- Mockito
- Maven
- Kubernetes (EKS)

## Funcionalidades

1. **Busca de dados**: Comunicação com a API de Cadastros para buscar dados de produtos e clientes.
2. **Pagamento**: Envio de dados de pagamento para a API de Pagamento via REST e recebimento do código do PIX criado.
3. **Produção**: Envio de dados do pedido para a API de produção via SQS.
4. **Controle de Status**: Gerenciamento do status do pedido via outra fila SQS.

## Utilização do SQS

Foi utilizado o SQS para mandar os pedidos para a API de produção (cozinha). A infraestrutura está contida no repositório: [infra-sqs-producao](https://github.com/fiapg70/infra-sqs-producao).

## Utilização do PostgreSQL

Foi utilizado o PostgreSQL para armazenar os dados. A infraestrutura está contida no repositório: [infra-rds-postgresql](https://github.com/fiapg70/infra-rds-postgresql).

## Como Rodar a API

### Pré-requisitos

- Java 17
- Docker & Docker Compose
- Maven
- PostgreSQL 12

### Passo a Passo

1. **Clone o repositório:**

   ```bash
   git clone https://github.com/fiapg70/sevenfood-order-api.git
   cd sevenfood-order-api

### Banco de Dados:
Para rodar o Banco local só rodar o docker compose da pasta postgres, com o comando 
```bash
  docker-compose up -d
Para rodar na AWS, rodar a infra: https://github.com/fiapg70/infra-rds-postgresql

### Antes de Rodar a Aplicação:
As variaves necessárias para rodar a aplicação estão na pasta env

### Rodando a Aplicação:
```bash
  mvn clean install
  mvn spring-boot:run

### Acesso à Documentação:
  Acesse a documentação da API via Swagger em http://localhost:9996/swagger-ui.html.
