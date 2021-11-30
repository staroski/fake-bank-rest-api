# DESAFIO CISS

Sua tarefa é construir uma API Rest para uma instituição financeira falsa usando Java.

## Apresentação

Embora os bancos modernos tenham evoluído para atender a uma infinidade de funções, em sua essência, os bancos devem fornecer certos recursos básicos.

Hoje, sua tarefa é construir a API Rest básica para um desses bancos!

Imagine que você esteja projetando uma API de back-end para funcionários do banco.

Esta API poderia ser consumida por vários front-ends (web, iOS, Android etc).

## Desenvolvimento

Este projeto foi criado na IDE eclipse como um "Spring Starter Project".

O projeto está configurada para utilizar somente um banco H2 em memória, isso significa que os dados só estão disponíveis enquanto a aplicação estiver em execução.

Para abrir localmente, basta fazer o clone deste projeto e importar para a sua IDE.

As seguintes tecnologias foram utilizadas para o desenvolvimento:

* OpenJDK 16.0.2
* Spring Boot 2.6.1
* JUnit 5
* Maven 3.8.4
* Git 2.34.0
* Eclipse 4.21.0
* Postman 9.2.0

## Estrutura do projeto

Os seguintes pacotes foram criados:

* `br.com.staroski.fakebank.controller` Contém as classes controladoras que processam as requisições;

* `br.com.staroski.fakebank.dto` Implementações de alguns *Data Transfer Objects* para serializar somente o necessário;

* `br.com.staroski.fakebank.model` Implementação das entidades, a representação das tabelas do banco de dados;

* `br.com.staroski.fakebank.repository` Declaração de interfaces do SpringBoot responsáveis pelo acesso ao banco de dados em tempo de execução através de JPA.

## Execução de testes automatizados

Para realizar os testes, basta executar a classe `br.com.staroski.fakebank.FakeBankApplicationTests`.

Esta classe realiza os seguintes testes:

* Criação e listagem de clientes, agências e contas bancárias;

* Operações de depósito, saque e transferências entre contas;

* Listagem de transações.

## Execução de testes manuais

Para realizar os testes manualmente é necessário subir a aplicação, para isto, execute a classe `br.com.staroski.fakebank.FakeBankApplication`.

Para facilitar os testes manuais, há o arquivo `fakebank.postman_collection.json` que pode ser importado no Postman.

A aplicação disponibiliza os seguintes endpoints:

### `/fakebank/clientes/criar`

Método **POST** - Cria um novo cliente, recebe como parâmetro um JSON com a seguinte estrutura:

```
{ "cpf": "12345678901", "nome": "João da Silva", "login": "joaozinho", "senha": "123456" }
```

### `/fakebank/clientes/listar`

Método **GET** - Lista os clientes, não recebe parâmetros.

### `/fakebank/agencias/criar`

Método **POST** - Cria uma nova agência, recebe como parâmetro um JSON com a seguinte estrutura:

```
{ "nome":"Agência 1" }
```

### `/fakebank/agencias/listar`

Método **GET** - Lista as agências, não recebe parâmetros.

### `/fakebank/clientes/listar`

Método **GET** - Lista os clientes, não recebe parâmetros.

### `/fakebank/contas/criar`

Método **POST** - Cria uma nova conta, recebe como parâmetro um JSON com a seguinte estrutura:

```
{ "numeroAgencia": 1, "codigoCliente": 1, "saldo": 0 }
```

### `/fakebank/contas/listar`

Método **GET** - Lista as contas, não recebe parâmetros.

### `/fakebank/transacoes/depositar`

Método **POST** - Realiza um depósito, recebe como parâmetro um JSON com a seguinte estrutura:

```
{ "numeroAgencia": 1, "numeroConta": 1, "valor": 100.0 }
```

### `/fakebank/transacoes/sacar`

Método **POST** - Realiza um saque, recebe como parâmetro um JSON com a seguinte estrutura:

```
{ "numeroAgencia": 1, "numeroConta": 1, "valor": 10.0 }
```

### `/fakebank/transacoes/transferir`

Método **POST** - Realiza uma transferência entre contas, recebe como parâmetro um JSON com a seguinte estrutura:

```
{ "agenciaOrigem": 1, "contaOrigem": 2, "valor": 5.0, "agenciaDestino": 1, "contaDestino": 1 }
```

### `/fakebank/transacoes/listar`
Método **POST** - Realiza uma consulta às transações realizadas em uma conta, recebe como parâmetro um JSON com a seguinte estrutura:

```
{ "numeroAgencia": 1, "numeroConta": 1 }
```
