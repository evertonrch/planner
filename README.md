# Plann.er - NLW Journey by Rocketseat

## 📌 Descrição

**Plann.er** é uma API REST desenvolvida durante a **NLW Journey** promovida pela Rocketseat. Seu objetivo é facilitar o planejamento de viagens, permitindo organizar itinerários, participantes e atividades diárias de forma prática e eficiente.

O backend é construído com **Java + Spring Boot**, persistência em **PostgreSQL**, versionamento de banco com **Flyway**, e empacotamento via **Maven**.

---

## 🚀 Tecnologias Utilizadas

- Java
- Spring Boot
- PostgreSQL
- Flyway (migrações de banco de dados)
- Maven

---

## ✨ Funcionalidades

- ✅ Criação e gerenciamento de itinerários de viagem
- ✅ Inclusão e controle de participantes
- ✅ Geração de links para confirmação de presença
- ✅ Organização de atividades por dia do itinerário

---

## ⚙️ Como Executar o Projeto

### Pré-requisitos

- Java 17 ou superior
- PostgreSQL instalado e configurado
- Maven instalado

### Passos

1. Clone o repositório:

```bash
git clone https://github.com/evertonrch/planner
cd planner
```

2. Ajuste o arquivo application.properties com suas credenciais de banco:

```bash
spring.datasource.url=jdbc:postgresql://localhost:5432/planner
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
```

3. Construa o projeto:

```bash
mvn package -DskipTests
```

4. Execute a aplicação:

```bash
java -jar target/planner-0.0.1-SNAPSHOT.jar

ou (modo desenvolvimento)

mvn spring-boot:run
```

## 📫 Contato

Se você tiver dúvidas, sugestões ou quiser trocar ideias sobre o projeto, sinta-se à vontade para entrar em contato:

- [Everton Rocha Monteiro no LinkedIn](https://linkedin.com/in/everton-rocha-monteiro)
