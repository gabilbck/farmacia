# Testes automatizados da API (Postman)

Os testes automatizados do catálogo estão na pasta [`postman/`](postman/). Eles são **requisições HTTP** à API REST (`/api/laboratorios`, `/api/medicamentos`) com **scripts de teste** no Postman (Chai assertions) que rodam **depois** de cada resposta.

## Como funciona

1. **`Testes-API-Catalogo-Farmacia.postman_collection`** — Coleção Postman em formato Collection v2.1. Cada requisição pode ter uma aba **Tests** com JavaScript (`pm.test`, `pm.expect`, etc.) que valida status HTTP e corpo JSON.
2. **`local.postman_environment.json`** — Ambiente Postman com variáveis (`baseUrl`, `laboratorioId`, `medicamentoId`) usadas nas URLs e no corpo dos pedidos.

**Ordem importa.** O fluxo cria um laboratório, guarda o `id` no ambiente, lista e atualiza; depois faz o mesmo com medicamentos (linkado ao laboratório); por fim remove medicamento e laboratório. Os scripts salvam IDs em variáveis de ambiente para os passos seguintes.

Resumo das requisições com validações típicas:

| Requisição | O que verifica |
|------------|----------------|
| POST LAB | Status 200 ou 201, corpo com `id`, grava `laboratorioId` |
| GET LAB | Status 200, lista é array e contém o laboratório criado |
| PUT LAB | Status 200, mesmo `id`, campos atualizados conforme payload |
| POST MED | Status 200 ou 201, `id`, grava `medicamentoId` |
| GET MED | Status 200, array contém o medicamento criado |
| PUT MED | Status 200, mesmo `id`, campos atualizados |
| DELETE MED / DELETE LAB | Status 200, 202 ou 204 (remoção) |

(O item **New Request** na coleção é placeholder vazio — pode ignorar ou remover.)

## Pré-requisitos

1. **Banco PostgreSQL** acessível conforme [`application.properties`](src/main/resources/application.properties) (variáveis `SPRING_DATASOURCE_*` ou padrões locais).
2. **Aplicação Spring Boot em execução** (mesma porta que você usar na `baseUrl`; o padrão do Spring Boot costuma ser `8080` se não estiver configurado outra coisa).

## Configurar o ambiente

1. Importe no Postman: **Import** → selecione a coleção e `local.postman_environment.json`.
2. Selecione o ambiente **local**.
3. Edite as variáveis do ambiente e defina **`baseUrl`** para a URL base da API **sem barra final**, por exemplo:

   `http://localhost:8080`

   Deixe `laboratorioId` e `medicamentoId` vazios na primeira execução; os scripts dos POST preenchem após criar recurso.

## Executar os testes

### No Postman (interface)

1. Garanta o ambiente **local** selecionado e `baseUrl` correta.
2. Abra a coleção **Testes API Catalogo Farmacia**.
3. Clique nos três pontos da coleção → **Run collection** (Runner).
4. Mantenha a ordem das requisições (a ordem padrão da coleção já segue o fluxo).
5. Inicie a execução e confira na aba de resultados se todos os **`pm.test`** passaram.

