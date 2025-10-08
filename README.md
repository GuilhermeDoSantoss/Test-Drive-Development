# 🎓 Sistema de Inscrições em Eventos

Projeto desenvolvido durante o curso do **Professor Isidro** na plataforma **Isiflix**, com foco em arquitetura de software, boas práticas de APIs REST e construção de sistemas baseados em **User Stories (US)**.

O objetivo é criar um **sistema completo de inscrição em eventos**, com controle de usuários, geração de links de indicação e ranking de participantes.

---

## 🚀 Visão Geral do Projeto

O sistema permite que alunos se inscrevam em eventos, gerem links personalizados de indicação e acompanhem o desempenho de suas indicações em um ranking de participantes.  
A aplicação segue uma estrutura orientada a domínio, com endpoints bem definidos e testes integrados.

---

## 🧩 Requisitos Funcionais

1. **Inscrição**  
   O usuário pode se inscrever no evento informando nome e e-mail.

2. **Geração de Link de Indicação**  
   Cada inscrito pode gerar um link exclusivo de indicação.

3. **Ranking de Indicações**  
   O sistema exibe o ranking dos usuários com mais indicações.

4. **Visualização de Indicações**  
   O usuário pode consultar quantas inscrições ocorreram através do seu link.

---

## 🧠 User Stories

### US00 - CRUD de Evento

Funcionalidade base para o gerenciamento de eventos.  
Permite criar, listar e consultar eventos pelo **Pretty Name**.

#### Endpoints

POST /events
Cria um novo evento.

Requisição:


{
  "name": "CodeCraft Summit 2025",
  
  "location": "Online",
  
  "price": 0.0,

  "startDate": "2025-03-16",
  
  "endDate": "2025-03-18",
  
  "startTime": "19:00:00",
  
  "endTime": "21:00:00"
}

Resposta:


{

  "id": 1,
  
  "name": "CodeCraft Summit 2025",
  
  "prettyName": "codecraft-summit-2025",
  
  "location": "Online",
  
  "price": 0.0,
  
  "startDate": "2025-03-16",
  
  "endDate": "2025-03-18",
  
  "startTime": "19:00:00",
  
  "endTime": "21:00:00"
  
}

GET /events

Lista todos os eventos cadastrados.

[

  {
  
    "id": 1,
    
    "name": "CodeCraft Summit 2025",
    
    "prettyName": "codecraft-summit-2025",
    
    "location": "Online",
    
    "price": 0.0,
    
    "startDate": "2025-03-16",
    
    "endDate": "2025-03-18",
    
    "startTime": "19:00:00",
    
    "endTime": "21:00:00"
    
  }
]

GET /events/PRETTY_NAME

Consulta evento pelo Pretty Name

Exemplo: http://localhost:8080/events/codecraft-summit-2025

Casos de Uso
Caso Base:

Evento não existe

Gera o pretty-name

Cadastra o evento

**Caso Alternativo 1:**

Pretty-name duplicado

Lança EventConflictException

US01 - Realizar Inscrição

Endpoint responsável pela inscrição de usuários em eventos.

POST /subscription/PRETTY_NAME

Requisição:

{
  "userName": "John Doe",
  
  "email": "john@doe.com"
}

Resposta:

{
  "subscriptionNumber": 1,
  
  "designation": "https://devstage.com/codecraft-summit-2025/123"
  
}

**Regras de Negócio:**

Um usuário não pode se inscrever duas vezes no mesmo evento.

Caso já exista no banco (por outro evento), apenas associa-se a nova inscrição.

Se o evento não existir → EventNotFound.

Se já houver inscrição → ConflictException.

Se houver indicação → cria vínculo com o usuário que indicou.

US02 - Gerar Ranking de Inscritos

Exibe o ranking de participantes com base no número de indicações.

GET /subscription/PRETTY_NAME/ranking

Exemplo:

[
  { "userName": "John Doe", "subscribers": 1000 },
  
  { "userName": "Mary Page", "subscribers": 873 },
  
  { "userName": "Frank Lynn", "subscribers": 690 }
]

Exibe os Top 3 (Gold, Silver, Bronze).

US03 - Estatísticas por Participante
Mostra o número de inscrições indicadas por um usuário e sua posição no ranking.


GET /subscription/PRETTY_NAME/ranking/USERID

Exemplo:

{
  "rankingPosition": 3,
  
  "user": {
  
    "userId": 123,
    
    "name": "John Doe",
    
    "count": 600
    
  }
  
}
### 🧪 Testes Integrados
Cenário	Endpoint	Esperado	Retorno

Criar evento	/events	✅ Sucesso	201

Evento duplicado	/events	⚠️ Conflito	409 (EventConflictException)

Buscar evento por Pretty Name	/events/{prettyName}	✅ Sucesso	200

Evento inexistente	/events/{prettyName}	❌ Não encontrado	404 (EventNotFound)

### ⚙️ Tecnologias Envolvidas

Java + Spring Boot

Spring Data JPA

PostgreSQL

Lombok

JUnit + MockMvc

Maven

Docker

### 🏗️ Passo a Passo de Execução
Clonar o repositório


git clone https://github.com/GuilhermeDoSantoss/Test-Drive-Development
cd eventos-isidro
Configurar o banco de dados

- Criar um banco PostgreSQL chamado eventdb

- Atualizar o arquivo application.properties:


spring.datasource.url=jdbc:postgresql://localhost:5432/eventdb
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha

**Build e execução**


mvn clean install
mvn spring-boot:run

**Acessar a aplicação**

- Acessar a aplicação
http://localhost:8080

### 🎯 Aprendizados do Curso

Durante o curso do Professor Isidro na Isiflix, foram abordados:

Modelagem de APIs REST com base em User Stories

Criação de endpoints desacoplados

Tratamento de exceções customizadas

Validação de regras de negócio

Testes integrados com MockMvc

Boas práticas de versionamento e documentação

### 👨‍💻 Autor
Guilherme dos Santos
Desenvolvedor Java | Engenheiro de Software

📍 Projeto desenvolvido no curso do Prof. Isidro (Isiflix)

📧 Contato: guilherme.devsoft@gmail.com
