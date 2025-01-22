# Sistema de Gestão de Contas Bancárias

## Descrição
Este projeto é um sistema backend para gerenciar contas bancárias, permitindo operações como depósitos, saques e transferências. Ele utiliza Micronaut, Protocol Buffers (Protobuf), Kafka e integra bancos de dados SQL e NoSQL para atender às necessidades de performance, escalabilidade e auditoria.

### Modelo de Dados
As principais entidades do sistema são:
- **Cliente:** Contém ID, nome, e-mail.
- **Conta Bancária:** Contém ID, número da conta, saldo, tipo de conta (ex: CORRENTE, POUPANÇA).
- **Transação:** Relaciona Contas Bancárias, contendo tipo (DEPÓSITO, SAQUE, TRANSFERÊNCIA), valor, data e status (ex: COMPLETA, PENDENTE).

#### Relações
- Um Cliente pode ter múltiplas Contas Bancárias.
- Uma Conta Bancária pode ter múltiplas Transações associadas.

### Banco de Dados SQL
- Utilize um banco de dados relacional (por exemplo, PostgreSQL ou MySQL) para armazenar os detalhes dos clientes, contas bancárias e transações.
- Implemente operações CRUD para clientes, contas bancárias e transações.

### Banco de Dados NoSQL
- Utilize um banco de dados NoSQL (por exemplo, MongoDB) para armazenar logs de auditoria de transações financeiras.

### Integração com Kafka
- Configure um tópico Kafka para eventos de transações.
- Produza um evento para cada nova transação (depósito, saque, transferência).
- Consuma eventos desse tópico para atualizar relatórios ou sistemas externos.

### Protocol Buffers (Protobuf)
- Defina esquemas Protobuf para serializar e desserializar mensagens de transações ao interagir com Kafka.
- Utilize Protobuf para comunicação entre serviços internos, se aplicável.

## Funcionalidades

### Gerenciamento de Clientes:
- Criar, atualizar, buscar e remover clientes.

### Gerenciamento de Contas Bancárias:
- Criar, atualizar, buscar e remover contas bancárias vinculadas aos clientes.

### Gerenciamento de Transações:
- Realizar depósitos, saques e transferências entre contas.
- Registrar logs de auditoria para cada transação.

### API REST:
- Endpoints para interagir com clientes, contas e transações.

### Integração com Kafka:
- Publicação de eventos de transações.
- Consumo de eventos para atualizações ou relatórios.

### Protocol Buffers (Protobuf):
- Serialização e desserialização de mensagens de transação para comunicação eficiente.

## Tecnologias Utilizadas
- **Framework:** Micronaut
- **Banco de Dados SQL:** PostgreSQL ou MySQL
- **Banco de Dados NoSQL:** MongoDB
- **Mensageria:** Kafka
- **Serialização:** Protocol Buffers (Protobuf)
- **Documentação da API:** Swagger (OpenAPI)
- **Linguagem:** Java 17+

## Endpoints da API

### Transações
- `POST /transacoes/deposito`: Realizar um depósito em uma conta.
- `POST /transacoes/saque`: Realizar um saque de uma conta.
- `POST /transacoes/transferencia`: Realizar uma transferência entre contas.
- `GET /contas/{id}/extrato`: Obter o extrato de uma conta específica.

### Clientes
- `POST /clientes`: Criar um novo cliente.
- `GET /clientes/{id}`: Buscar um cliente pelo ID.
- `PUT /clientes/{id}`: Atualizar dados de um cliente.
- `DELETE /clientes/{id}`: Remover um cliente.

### Contas Bancárias
- `POST /contas`: Criar uma nova conta para um cliente.
- `GET /contas/{id}`: Buscar uma conta pelo ID.
- `PUT /contas/{id}`: Atualizar os dados de uma conta.
- `DELETE /contas/{id}`: Remover uma conta.

## Como Executar o Projeto

### Pré-requisitos
- Java 17+
- Docker (para Kafka e MongoDB, se necessário)
- PostgreSQL ou MySQL
- Protoc (para gerar as classes do Protobuf)

### Configuração
1. Estrutura do Projeto:
```bash
src/
├── main/
│   ├── java/
│   │   ├── app/
│   │   │   ├── controller/
│   │   │   ├── service/
│   │   │   ├── model/
│   │   │   ├── protobuf/
│   │   │   ├── kafka/
│   │   │      ├── consumer/
│   │   │      ├── producer/
|   |   |   ├── repository/
|   |   |   ├── mapper/
│   │   ├── resources/
│   │       ├── application.yml
├── test/
```
