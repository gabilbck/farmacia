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
git clone <URL_DO_SEU_REPOSITORIO>
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

## Teste rápido com curl (1 minuto)

Use os comandos abaixo em outro terminal (com a API já rodando):

```bash
# 1) Criar laboratório
curl -X POST http://localhost:8080/api/laboratorios \
  -H "Content-Type: application/json" \
  -d "{\"cnpj\":\"12345678000190\",\"razaoSocial\":\"Laboratorio Exemplo SA\",\"nomeFantasia\":\"Lab Exemplo\",\"status\":true}"

# 2) Listar laboratórios (pegue o id retornado)
curl http://localhost:8080/api/laboratorios

# 3) Criar medicamento (ajuste laboratorioId conforme o id criado)
curl -X POST http://localhost:8080/api/medicamentos \
  -H "Content-Type: application/json" \
  -d "{\"ean\":\"7891234567890\",\"nome\":\"Dipirona\",\"dosagemValor\":\"500\",\"dosagemUM\":\"mg\",\"categoria\":\"GENERICO\",\"classeTerapeutica\":\"Analgesico\",\"formaFarmaceutica\":\"COMPRIMIDO\",\"prescricao\":false,\"tarja\":\"SEM_TARJA\",\"anvisaRegular\":true,\"pfp\":false,\"precoVenda\":12.90,\"status\":true,\"observacoes\":\"Uso adulto\",\"laboratorioId\":1}"

# 4) Listar medicamentos
curl http://localhost:8080/api/medicamentos
```

No PowerShell, se o alias `curl` conflitar, use `curl.exe` no lugar de `curl`.

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
