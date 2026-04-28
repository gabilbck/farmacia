# Farmacia API

API REST para cadastro e gestão de medicamentos e laboratórios, construída com Spring Boot e PostgreSQL.

## O que o projeto faz

O sistema permite:
- Cadastrar, listar, buscar, atualizar e remover laboratórios.
- Cadastrar, listar, buscar, atualizar e remover medicamentos.
- Relacionar medicamentos a laboratórios.
- Validar dados de entrada e retornar erros HTTP padronizados.

## Tecnologias e dependências

- Java 21
- Spring Boot (Web, Validation, Data JPA)
- PostgreSQL
- Maven
- Docker e Docker Compose

As dependências Java estão no `pom.xml`.

## Como instalar e rodar localmente

### Opção 1 (recomendada): com Docker

Pré-requisitos:
- Docker Desktop em execução
- Git

Comandos:

```bash
git clone https://github.com/gabilbck/farmacia
cd farmacia
docker compose down
docker compose up -d --build
docker compose logs -f app
```

Quando aparecer `Started FarmaciaApplication`, a API estará disponível em `http://localhost:8080`.

### Opção 2: sem Docker

Pré-requisitos:
- Java 21 instalado
- Maven (ou usar `./mvnw` / `mvnw.cmd`)
- PostgreSQL rodando localmente

Configuração padrão do banco (em `application.properties`):
- URL: `jdbc:postgresql://localhost:54321/farmacia`
- Usuário: `postgres`
- Senha: `5432`

Execução:

```bash
./mvnw spring-boot:run
```

No Windows:

```bash
mvnw.cmd spring-boot:run
```

## Como usar a aplicação

- Interface inicial: [http://localhost:8080](http://localhost:8080)
- Endpoints REST base:
  - `http://localhost:8080/api/laboratorios`
  - `http://localhost:8080/api/medicamentos`

A documentação completa da API está em `API.md`.

## Instruções Docker

Arquivos disponíveis:
- `Dockerfile`
- `docker-compose.yml`

Comandos úteis:

```bash
docker compose ps
docker compose logs -f db
docker compose restart app
docker compose down
```

Persistência dos dados:
- `docker compose down` mantém os dados (volume `postgres_data`)
- `docker compose down -v` remove os dados do banco
