# Desafio Java Back-end - Softexpert

## Dependências

Certifique-se de que seu ambiente atenda aos seguintes requisitos antes de executar o projeto:

- **Back-end**:
  - [Java 17](https://www.oracle.com/java/technologies/javase-downloads.html)

- **Front-end**:
  - [Node.js 18](https://nodejs.org/)
  - [npm 9](https://www.npmjs.com/)

## Backend

A API deste desafio foi desenvolvida utilizando a linguagem Java com o framework Spring. Ela possui três rotas principais, sendo duas delas destinadas para testes e uma para a aplicação principal.

### Rotas de Teste

#### POST /api/test/split

Esta rota recebe uma requisição com os itens, descontos e adicionais de uma compra e realiza o cálculo das partes de cada participante.

**Requisição** (`Content-Type: application/json`):
```json
{
  "items": {
    "<Nome do participante>": [{"name": "<Nome do item>", "price": <Valor do item>}]
  },
  "discounts": [{"type": "<FIXED|PERCENTAGE>", "amount": <Valor do desconto>}],
  "additions": [{"type": "<FIXED|PERCENTAGE>", "amount": <Valor do adicional>}]
}
```

**Exemplo de requisição**:
```json
{
  "items": {
    "Voce": [{"name": "Hamburguer", "price": 40}, {"name": "Sobremesa", "price": 2}],
    "Amigo": [{"name": "Sanduiche", "price": 5}]
  },
  "discounts": [{"type": "FIXED", "amount": 20}],
  "additions": [{"type": "FIXED", "amount": 8}]
}
```

**Resposta** (`application/json`):
```json
{
  "shares": {
    "<Nome do participante>": <Valor devido>
  }
}
```

**Exemplo de resposta**:
```json
{
  "shares": {
    "Amigo": 3.72,
    "Voce": 31.28
  }
}
```

#### POST /api/test/generate-links

Esta rota recebe as partes de cada participante, o nome do proprietário e sua chave PIX para gerar links de pagamento para os outros participantes.

**Requisição** (`Content-Type: application/json`):
```json
{
  "shares": {
    "<Nome do participante>": <Valor devido>
  },
  "ownerName": "<Nome do proprietário>",
  "ownerPixKey": "<Chave PIX do proprietário>"
}
```

**Exemplo de requisição**:
```json
{
  "shares": {
    "Amigo": 3.72,
    "Voce": 31.28
  },
  "ownerName": "Voce",
  "ownerPixKey": "12345678900"
}
```

**Resposta** (`application/json`):
```json
[
  {
    "name": "<Nome do participante>",
    "amount": "<Valor devido>",
    "pixLink": "<pix_link>",
    "qrCodeBase64": "<imagem_base64>"
  },
  {
    "name": "<Nome do proprietário>",
    "amount": "<Valor devido>",
    "pixLink": "",
    "qrCodeBase64": ""
  }
]
```

**Exemplo de resposta**:
```json
[
  {
    "name": "Amigo",
    "amount": 3.72,
    "pixLink": "https://pix.bcb.gov.br/qr/...<link_completo>...",
    "qrCodeBase64": "iVBORw0KGgoAAAANSUhEUgAAAPoAAAD6AQAAAAC...<base_64_completo>..."
  },
  {
    "name": "Voce",
    "amount": 31.28,
    "pixLink": "",
    "qrCodeBase64": ""
  }
]
```

### Rota Principal

#### POST /api/split

Esta rota é responsável por processar a requisição com os itens, descontos, adicionais, nome do proprietário e chave PIX, e realizar o cálculo das partes de cada participante. Em seguida, gera os links de pagamento para os participantes, exceto o proprietário.

**Requisição** (`Content-Type: application/json`):
```json
{
  "items": {
    "<Nome do participante>": [{"name": "<Nome do item>", "price": <Valor do item>}]
  },
  "discounts": [{"type": "<FIXED|PERCENTAGE>", "amount": <Valor do desconto>}],
  "additions": [{"type": "<FIXED|PERCENTAGE>", "amount": <Valor do adicional>}],
  "ownerName": "<Nome do proprietário>",
  "ownerPixKey": "<Chave PIX do proprietário>"
}
```

**Exemplo de requisição**:
```json
{
  "items": {
    "Voce": [{"name": "Hamburguer", "price": 40}, {"name": "Sobremesa", "price": 2}],
    "Amigo": [{"name": "Sanduiche", "price": 5}]
  },
  "discounts": [{"type": "FIXED", "amount": 20}],
  "additions": [{"type": "FIXED", "amount": 8}],
  "ownerName": "Voce",
  "ownerPixKey": "12345678900"
}
```

**Resposta** (`application/json`):
```json
[
  {
    "name": "<Nome do participante>",
    "amount": "<Valor devido>",
    "pixLink": "<pix_link>",
    "qrCodeBase64": "<imagem_base64>"
  },
  {
    "name": "<Nome do proprietário>",
    "amount": "<Valor devido>",
    "pixLink": "",
    "qrCodeBase64": ""
  }
]
```

**Exemplo de resposta**:
```json
[
  {
    "name": "Amigo",
    "amount": 3.72,
    "pixLink": "https://pix.bcb.gov.br/qr/...<link_completo>...",
    "qrCodeBase64": "iVBORw0KGgoAAAANSUhEUgAAAPoAAAD6AQAAAAC...<base_64_completo>..."
  },
  {
    "name": "Voce",
    "amount": 31.28,
    "pixLink": "",
    "qrCodeBase64": ""
  }
]
```

## Frontend

O frontend foi implementado utilizando apenas HTML, CSS e JavaScript puros, sem o uso de frameworks externos. A aplicação oferece um formulário simples para o usuário inserir os dados necessários, como os itens, descontos, adicionais, nome do proprietário e chave PIX.

---

### Como Executar

1. Clone o repositório:
   ```bash
   git clone https://github.com/bidinpithecus/DesafioBackendSE/
   ```

2. Navegue até o diretório do projeto:
   ```bash
   cd DesafioBackendSE
   ```

3. Entre no diretório da api:
   ```bash
   cd api
   ```

4. Compile e execute o projeto com o Maven:
   ```bash
   ./mvnw spring-boot:run
   ```

5. Em outra instância do terminal, navegue novamente até o diretório do projeto:
   ```bash
   cd DesafioBackendSE
   ```

6. Entre no diretório do frontend:
   ```bash
   cd frontend
   ```

7. Instale as dependências do front:
   ```bash
   npm install
   ```

8. Execute o front:
   ```bash
   npm start
   ```

9. Acesse a API na URL `http://localhost:3000`.

---

### Notas

- As rotas de teste são úteis para simulação e testes dos cálculos de divisão de valores entre participantes.
- A rota principal (`/api/split`) integra o cálculo de divisão de valores e a geração dos links de pagamento, utilizando a chave PIX do proprietário.
- A chave PIX do proprietário é usada apenas para gerar os links de pagamento para os outros participantes.
- Caso queira, é possível alterar o provedor do pagamento dentro do arquivo application.properties dentro do diretório api/src/main
  - As opções implementadas são do Itau e do Mercado Pago;
  - O provedor do Mercado Pago porém, é associado a uma conta do Mercado Pago, sendo assim, as transações feitas utilizando este vão para o mesmo destinatário sempre.
