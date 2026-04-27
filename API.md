# Documentacao da API

Base URL local: `http://localhost:8080`

## Padrao de erro

Quando ocorre erro, a API retorna JSON no formato:

```json
{
  "status": 400,
  "erro": "Requisicao invalida",
  "mensagem": "Descricao do erro"
}
```

Codigos comuns:
- `200 OK`: requisicao concluida com sucesso
- `204 No Content`: exclusao realizada sem corpo de resposta
- `400 Bad Request`: validacao ou JSON invalido
- `404 Not Found`: recurso nao encontrado
- `500 Internal Server Error`: erro inesperado no servidor

---

## Laboratorios

### POST `/api/laboratorios`
Cria um laboratorio.

Request body:

```json
{
  "cnpj": "12345678000190",
  "razaoSocial": "Laboratorio Exemplo SA",
  "nomeFantasia": "Lab Exemplo",
  "status": true
}
```

Response `200`:

```json
{
  "id": 1,
  "cnpj": "12345678000190",
  "razaoSocial": "Laboratorio Exemplo SA",
  "nomeFantasia": "Lab Exemplo",
  "status": true
}
```

Erros:
- `400` para campos obrigatorios ausentes ou invalidos
- `500` para erro inesperado

### POST `/api/laboratorios/lote`
Cria varios laboratorios em lote.

Request body:

```json
[
  {
    "cnpj": "12345678000190",
    "razaoSocial": "Laboratorio Exemplo SA",
    "nomeFantasia": "Lab Exemplo",
    "status": true
  },
  {
    "cnpj": "00987654000112",
    "razaoSocial": "Laboratorio Sul SA",
    "nomeFantasia": "Lab Sul",
    "status": true
  }
]
```

Response `200`: lista de laboratorios criados.

Erros:
- `400` para JSON invalido ou itens invalidos
- `500` para erro inesperado

### GET `/api/laboratorios`
Lista todos os laboratorios.

Response `200`:

```json
[
  {
    "id": 1,
    "cnpj": "12345678000190",
    "razaoSocial": "Laboratorio Exemplo SA",
    "nomeFantasia": "Lab Exemplo",
    "status": true
  }
]
```

Erros:
- `500` para erro inesperado

### GET `/api/laboratorios/{id}`
Busca laboratorio por ID.

Response `200`: laboratorio encontrado.

Erros:
- `404` se o ID nao existir
- `500` para erro inesperado

### PUT `/api/laboratorios/{id}`
Atualiza laboratorio por ID (campos enviados sao aplicados).

Request body (exemplo parcial):

```json
{
  "nomeFantasia": "Lab Exemplo Atualizado",
  "status": false
}
```

Response `200`: laboratorio atualizado.

Erros:
- `404` se o ID nao existir
- `500` para erro inesperado

### DELETE `/api/laboratorios/{id}`
Remove laboratorio por ID.

Response `204` sem corpo.

Erros:
- `404` se o ID nao existir
- `500` para erro inesperado

---

## Medicamentos

### POST `/api/medicamentos` (tambem aceita `/api/medicamentos/cadMed`)
Cria um medicamento.

Request body:

```json
{
  "ean": "7891234567890",
  "nome": "Dipirona",
  "dosagemValor": "500",
  "dosagemUM": "mg",
  "categoria": "GENERICO",
  "classeTerapeutica": "Analgesico",
  "formaFarmaceutica": "COMPRIMIDO",
  "prescricao": false,
  "tarja": "SEM_TARJA",
  "anvisaRegular": true,
  "pfp": false,
  "precoVenda": 12.9,
  "status": true,
  "observacoes": "Uso adulto",
  "laboratorioId": 1
}
```

Response `200`:

```json
{
  "id": 10,
  "ean": "7891234567890",
  "nome": "Dipirona",
  "dosagemValor": "500",
  "dosagemUM": "mg",
  "categoria": "GENERICO",
  "classeTerapeutica": "Analgesico",
  "prescricao": false,
  "tarja": "SEM_TARJA",
  "formaFarmaceutica": "COMPRIMIDO",
  "anvisaRegular": true,
  "pfp": false,
  "precoVenda": 12.9,
  "status": true,
  "observacoes": "Uso adulto",
  "laboratorioId": 1,
  "laboratorioRazaoSocial": "Laboratorio Exemplo SA",
  "laboratorioNomeFantasia": "Lab Exemplo"
}
```

Erros:
- `400` para campos invalidos, enums invalidos ou JSON malformado
- `404` se `laboratorioId` nao existir
- `500` para erro inesperado

### POST `/api/medicamentos/lote`
Cria varios medicamentos em lote.

Request body: array de objetos no mesmo formato do POST simples.

Response `200`: lista de medicamentos criados.

Erros:
- `400`, `404`, `500` nas mesmas condicoes do cadastro unitario

### GET `/api/medicamentos` (tambem aceita `/api/medicamentos/listMed`)
Lista todos os medicamentos.

Response `200`: lista de medicamentos.

Erros:
- `500` para erro inesperado

### GET `/api/medicamentos/{id}`
Busca medicamento por ID.

Response `200`: medicamento encontrado.

Erros:
- `404` se o ID nao existir
- `500` para erro inesperado

### PUT `/api/medicamentos/{id}` (tambem aceita `/api/medicamentos/updMed/{id}`)
Atualiza medicamento por ID (apenas campos enviados sao alterados).

Request body (exemplo parcial):

```json
{
  "precoVenda": 15.5,
  "status": false
}
```

Response `200`: medicamento atualizado.

Erros:
- `404` se o ID nao existir ou `laboratorioId` informado nao existir
- `500` para erro inesperado

### DELETE `/api/medicamentos/{id}` (tambem aceita `/api/medicamentos/delMed/{id}`)
Remove medicamento por ID e retorna o registro removido.

Response `200`: medicamento removido.

Erros:
- `404` se o ID nao existir
- `500` para erro inesperado

---

## Validacoes importantes

### Laboratorio
- `cnpj`: obrigatorio, somente numeros, exatamente 14 digitos
- `razaoSocial`: obrigatoria
- `nomeFantasia`: obrigatorio
- `status`: obrigatorio

### Medicamento
- `ean`: obrigatorio, somente numeros, 8 a 14 digitos
- `nome`: obrigatorio
- `dosagemValor`: obrigatorio, numerico
- `dosagemUM`: obrigatorio
- `categoria`: obrigatoria (`GENERICO`, `SIMILAR`, `REFERENCIA`)
- `classeTerapeutica`: obrigatoria
- `formaFarmaceutica`: obrigatoria (`COMPRIMIDO`, `CAPSULA`, `XAROPE`, `INJETAVEL`, `POMADA`, `CREME`, `GEL`, `GOTAS`, `SUPOSITORIO`, `INALADOR`, `ADESIVO`)
- `prescricao`: obrigatoria
- `tarja`: obrigatoria (`TARJA_PRETA`, `TARJA_VERMELHA`, `TARJA_AMARELA`, `SEM_TARJA`)
- `anvisaRegular`: obrigatorio
- `pfp`: obrigatorio
- `precoVenda`: obrigatorio, maior ou igual a zero
- `status`: obrigatorio
- `laboratorioId`: opcional, mas quando informado deve ser maior que zero e existir no banco
