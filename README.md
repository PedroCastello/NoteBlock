# Noteblock API

API REST para gerenciamento de notas pessoais com autenticação JWT.

## Tecnologias

- **Java 21**
- **Spring Boot 4.1.0**
- **Spring Security** + **JWT** (jjwt 0.12.6)
- **Spring Data JPA** + **Hibernate**
- **PostgreSQL**
- **Lombok**
- **Bean Validation**

## Pré-requisitos

- Java 21+
- Maven 3.9+
- PostgreSQL rodando na porta `5432` com um banco chamado `noteblock`

## Configuração

As variáveis de ambiente necessárias são:

| Variável | Descrição | Padrão |
|---|---|---|
| `DB_USERNAME` | Usuário do banco de dados | `postgres` |
| `DB_PASSWORD` | Senha do banco de dados | `postgres` |
| `JWT_SECRET` | Chave secreta para assinar os tokens JWT | *(obrigatória)* |
| `JWT_EXPIRATION_MS` | Tempo de expiração do token em milissegundos | `86400000` (24h) |

Exemplo de configuração via variáveis de ambiente:

```bash
DB_USERNAME=postgres
DB_PASSWORD=postgres
JWT_SECRET=minha-chave-secreta-super-segura
JWT_EXPIRATION_MS=86400000
```

## Como executar

```bash
# Clone o repositório
git clone <url-do-repositorio>
cd noteblock

# Execute com Maven Wrapper
./mvnw spring-boot:run
```

A aplicação sobe em `http://localhost:8080`.

## Endpoints

### Autenticação — `/auth`

| Método | Rota | Descrição | Auth |
|---|---|---|---|
| `POST` | `/auth/registrar` | Registra um novo usuário | Não |
| `POST` | `/auth/login` | Autentica e retorna o token JWT | Não |

#### Registrar
```json
POST /auth/registrar
{
  "nome": "João Silva",
  "email": "joao@email.com",
  "senha": "minhasenha123"
}
```

#### Login
```json
POST /auth/login
{
  "email": "joao@email.com",
  "senha": "minhasenha123"
}
```
**Resposta:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9..."
}
```

---

### Notas — `/notas`

Todos os endpoints abaixo exigem o header:
```
Authorization: Bearer <token>
```

| Método | Rota | Descrição |
|---|---|---|
| `POST` | `/notas` | Cria uma nova nota |
| `GET` | `/notas` | Lista todas as notas do usuário |
| `PUT` | `/notas/{id}` | Atualiza uma nota pelo ID |
| `DELETE` | `/notas/{id}` | Remove uma nota pelo ID |
| `DELETE` | `/notas` | Remove todas as notas do usuário |

#### Corpo da requisição (criar / atualizar)
```json
{
  "titulo": "Minha nota",
  "conteudo": "Conteúdo da nota"
}
```

## Estrutura do projeto

```
src/main/java/com/noteblock/noteblock/
├── config/          # Configuração do Spring Security
├── controller/      # Camada de entrada (REST)
├── dto/             # Objetos de transferência de dados
├── entity/          # Entidades JPA (Usuario, Nota)
├── exception/       # Exceções customizadas e GlobalExceptionHandler
├── repository/      # Interfaces JPA
├── security/        # JWT filter, TokenService, UserDetailsService
└── service/         # Regras de negócio
```

## Coleção Postman

Importe o arquivo `postman/Noteblock.postman_collection.json` no Postman para testar todos os endpoints já configurados.