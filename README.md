# Cleanic - Back-end

Este é o repositório do back-end do sistema de Gestão de Consultas Odontológicas Cleanic. A API foi desenvolvida para gerenciar o agendamento de consultas, cadastro de pacientes, dentistas, especialidades e controle de acesso de usuários.

## Tech Stack

* **Java 21**
* **Spring Boot 3**
* **Spring Security (JWT)**
* **Spring Data JPA**
* **PostgreSQL**
* **Maven**

## Como Executar o Projeto

### Pré-requisitos
* Java 21 instalado
* Maven instalado
* PostgreSQL rodando (localmente ou via Docker) com um banco de dados criado (ex: `dental_clinic_db`).

### Passos para Execução
1.  Clone este repositório.
2.  Abra o projeto na sua IDE (IntelliJ, Eclipse, VS Code).
3.  Configure as variáveis de ambiente no arquivo `application.properties` (ou `.yml`) com as credenciais do seu banco de dados PostgreSQL. Exemplo:
    ```properties
    spring.datasource.url=jdbc:postgresql://localhost:5432/dental_clinic_db
    spring.datasource.username=seu_usuario
    spring.datasource.password=sua_senha
    ```
4.  Execute a aplicação. O Spring Boot irá inicializar as tabelas no banco de dados automaticamente.
5.  A API estará disponível em `http://localhost:8080`.

## Documentação dos Endpoints (API)

Abaixo estão as descrições dos principais endpoints disponíveis no sistema. O sistema utiliza autenticação via JWT, portanto, para a maioria das rotas, é necessário enviar o token no cabeçalho: `Authorization: Bearer <token>`.

### Autenticação

* **Login (Geração de Token JWT)**
    * **Método:** `POST`
    * **Path:** `/oauth2/token`
    * **Corpo da Requisição (URL-encoded):**
        * `username` (email)
        * `password` (senha)
        * `grant_type=password`
    * **Parâmetros de Cabeçalho:** Basic Auth com `client-id` e `client-secret`.
    * **Resposta:** JSON contendo o `access_token` e detalhes do usuário logado.

### Usuários (`/users`)
*Somente perfil ADMIN possui acesso ao gerenciamento de usuários.*

* **Listar Todos:**
    * **Método:** `GET`
    * **Path:** `/users`
    * **Resposta:** Lista de usuários cadastrados.
* **Criar Novo Usuário:**
    * **Método:** `POST`
    * **Path:** `/users`
    * **Corpo:** `{ "name": "...", "cpf": "...", "email": "...", "roles": "ROLE_ADMIN" ou "ROLE_DENTIST" }`
    * **Resposta:** Dados do usuário criado (sem a senha).
* **Atualizar Usuário:**
    * **Método:** `PUT`
    * **Path:** `/users/{id}`
    * **Corpo:** Dados a serem atualizados.
    * **Resposta:** Usuário atualizado.
    * 
### Pacientes (`/patients`)

* **Listar Pacientes (Paginado):**
    * **Método:** `GET`
    * **Path:** `/patients`
    * **Parâmetros (Query):** `page` (int), `size` (int), `name` (string, opcional para filtro).
    * **Resposta:** Página (Page) contendo a lista de pacientes.
* **Criar Paciente:**
    * **Método:** `POST`
    * **Path:** `/patients`
    * **Corpo:** `{ "name": "...", "cpf": "...", "email": "...", "phoneNumber": "..." }`
    * **Resposta:** Paciente criado.
* **Atualizar Paciente:**
    * **Método:** `PUT`
    * **Path:** `/patients/{id}`
    * **Corpo:** Dados do paciente a serem atualizados.
    * **Resposta:** Paciente atualizado.

### Dentistas (`/dentists`)

* **Listar Dentistas:**
    * **Método:** `GET`
    * **Path:** `/dentists`
    * **Resposta:** Lista paginada de dentistas.
* **Criar Dentista:**
    * **Método:** `POST`
    * **Path:** `/dentists`
    * **Corpo:** `{ "name": "...", "cpf": "...", "email": "...", "cro": "...", "active": true }`
    * **Resposta:** Dentista criado.
* **Atualizar Dentista:**
    * **Método:** `PUT`
    * **Path:** `/dentists/{id}`
    * **Corpo:** Dados do dentista atualizados.
    * **Resposta:** Dentista atualizado.
      
### Especialidades (`/specialties`)

* **Listar Especialidades:**
    * **Método:** `GET`
    * **Path:** `/specialties`
    * **Resposta:** Lista paginada de especialidades.
* **Criar Especialidade:**
    * **Método:** `POST`
    * **Path:** `/specialties`
    * **Corpo:** `{ "name": "..." }`
    * **Resposta:** Especialidade criada.
* **Atualizar Especialidade:**
    * **Método:** `PUT`
    * **Path:** `/specialties/{id}`
    * **Corpo:** Dados da especialidade.


### Consultas (`/appointments`)

* **Listar Consultas:**
    * **Método:** `GET`
    * **Path:** `/appointments`
    * **Nota:** Perfis ADMIN veem todas; DENTISTAS veem apenas as suas.
* **Criar/Agendar Consulta:**
    * **Método:** `POST`
    * **Path:** `/appointments`
    * **Corpo:** `{ "patient": { "id": X }, "dentist": { "id": Y }, "description": "...", "startTime": "...", "endTime": "..." }`
    * **Resposta:** Agendamento confirmado. O backend valida sobreposição de horários e datas passadas.
* **Atualizar Consulta:**
    * **Método:** `PUT`
    * **Path:** `/appointments/{id}`
* **Cancelar Consulta:**
    * **Método:** `PATCH`
    * **Path:** `/appointments/{id}`
    * **Corpo:** Necessário informar o motivo do cancelamento (`cancellationReason`).
