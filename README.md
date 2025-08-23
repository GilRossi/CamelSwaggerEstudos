Show! 🎯
Aqui vai uma versão **bem profissional e moderna do README**, já com **badges** para destacar tecnologias e status.

---

# 🐪 Projeto Apache Camel – User & File Processor

![Java](https://img.shields.io/badge/Java-17+-red?style=for-the-badge\&logo=openjdk)
![Maven](https://img.shields.io/badge/Maven-Build-blue?style=for-the-badge\&logo=apachemaven)
![Apache Camel](https://img.shields.io/badge/Apache%20Camel-Integration-orange?style=for-the-badge\&logo=apache)
![Status](https://img.shields.io/badge/Status-Em%20Desenvolvimento-yellow?style=for-the-badge)

---

## 📖 Sobre o Projeto

Este projeto é um **exemplo prático de integração com Apache Camel**, que combina:

* 🌐 **APIs REST** para gerenciamento de usuários (CRUD básico).
* 📂 **Processamento de arquivos XML**, transformando-os em objetos `User` e salvando em pastas dinâmicas.
* ⚡ **Tratamento de exceções**, movendo arquivos problemáticos para uma pasta de erros.
* 📝 **Logs detalhados**, garantindo rastreabilidade de ponta a ponta.

Esse modelo pode ser expandido para casos reais de **ETL, pipelines de integração, microsserviços e mensageria**.

---

## 🛠️ Tecnologias Utilizadas

* ☕ **Java 17+**
* 🐪 **Apache Camel** (Netty-HTTP, File, Core)
* 📦 **Maven**
* 🐧 Linux / 🪟 Windows (ambiente de execução)

---

## 📂 Estrutura do Projeto

```
.
├── src/main/java/org/apache/camel/learn/
│   ├── MyRouteBuilder.java   # Definição das rotas Camel
│   ├── User.java             # Modelo de usuário
│   ├── UserService.java      # Serviço simples de usuários
│
├── src/data/                 # Pasta de entrada dos arquivos XML
├── target/messages/          # Saída de arquivos processados
├── target/errors/            # Arquivos que falharam no processamento
└── pom.xml                   # Configuração Maven
```

---

## 🚀 Como Executar

### 1. Clonar o repositório

```bash
git clone https://github.com/seu-usuario/seu-projeto.git
cd seu-projeto
```

### 2. Compilar e empacotar

```bash
mvn clean install
```

### 3. Rodar o projeto

```bash
mvn camel:run
```

---

## 🌐 Endpoints Disponíveis

| Método | Endpoint        | Descrição                 |
| ------ | --------------- | ------------------------- |
| GET    | `/test`         | Teste simples de saúde    |
| GET    | `/user/{id}`    | Consulta usuário por ID   |
| GET    | `/user/findAll` | Lista todos os usuários   |
| PUT    | `/user`         | Atualiza usuário via JSON |

📌 **Exemplo de requisição PUT**:

```json
{
  "id": 1,
  "username": "jdoe",
  "firstName": "John",
  "lastName": "Doe",
  "city": "SP"
}
```

---

## 📂 Fluxo de Arquivos

1. Adicione um arquivo XML em `src/data`:

   ```xml
   <person user="jdoe">
       <firstName>John</firstName>
       <lastName>Doe</lastName>
       <city>SP</city>
   </person>
   ```

2. O Camel processará e salvará em:

   ```
   target/messages/SP/{timestamp}_message4.xml
   ```

3. Caso haja erro, o arquivo será movido para:

   ```
   target/errors/message4.xml_error
   ```

---

## 🧪 Testando

* Acesse: [http://localhost:8080/test](http://localhost:8080/test)
* Envie arquivos para `src/data` e verifique `target/messages`.
* Consuma as APIs de usuários.

---

## 📈 Próximos Passos

* 🔹 Adicionar persistência em banco de dados.
* 🔹 Melhorar validação de XML (XSD).
* 🔹 Criar testes automatizados com **Camel Test Kit**.
* 🔹 Documentar APIs com **Swagger/OpenAPI**.

---

## 👨‍💻 Autor

Desenvolvido por **Gil Rossi Aguiar**

[![LinkedIn](https://img.shields.io/badge/LinkedIn-Perfil-blue?style=for-the-badge\&logo=linkedin)](https://www.linkedin.com/in/gilrossiaguiar)
[![GitHub](https://img.shields.io/badge/GitHub-Portfólio-black?style=for-the-badge\&logo=github)](https://github.com/GilRossi)
