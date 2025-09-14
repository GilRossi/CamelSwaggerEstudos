# 🐪 Projeto Apache Camel – User & File Processor

Projeto de integração com **Apache Camel** que combina APIs REST para gerenciamento de usuários com processamento de arquivos XML, aplicando princípios de **integração de sistemas** e **ETL**.

---

## 🚀 Tecnologias Utilizadas

* **Java 17+**
* **Apache Camel** (Netty-HTTP, File, Core)
* **Maven**
* **Linux/Windows** (ambiente de execução)

---

## 📂 Estrutura do Projeto

```
src/main/java/org/apache/camel/learn/
│
├── MyRouteBuilder.java      # Definição das rotas Camel
├── User.java                # Modelo de usuário
└── UserService.java         # Serviço simples de usuários

src/data/                    # Pasta de entrada dos arquivos XML
target/messages/             # Saída de arquivos processados
target/errors/               # Arquivos que falharam no processamento
```

---

## 🛠 Princípios Aplicados

### **Clean Code**

* Rotas bem organizadas e com responsabilidades claras
* Nomenclatura consistente e descritiva
* Separação entre lógica de negócio e configuração de rotas

### **SOLID**

* **S**ingle Responsibility: cada classe tem uma única responsabilidade
* **O**pen/Closed: fácil extensão para novas rotas e processamentos
* **D**ependency Inversion: uso de serviços abstraídos para processamento

### **Design Patterns**

* **Enterprise Integration Patterns**: padrões de integração do Apache Camel
* **Service Layer**: separação clara entre serviços e rotas de integração
* **Exception Handling Pattern**: tratamento consistente de erros

---

## 📌 Funcionalidades Principais

### 🌐 APIs REST
* **GET** `/test` - Teste simples de saúde
* **GET** `/user/{id}` - Consulta usuário por ID
* **GET** `/user/findAll` - Lista todos os usuários
* **PUT** `/user` - Atualiza usuário via JSON

### 📂 Processamento de Arquivos
* Processamento automático de arquivos XML
* Transformação de XML para objetos User
* Roteamento baseado em conteúdo (cidade)
* Tratamento de exceções com movimentação para pasta de errors

---

## 💻 Como Executar

1. **Clonar o repositório**

```bash
git clone https://github.com/GilRossi/apache-camel-processor.git
cd apache-camel-processor
```

2. **Compilar e empacotar**

```bash
mvn clean install
```

3. **Rodar o projeto**

```bash
mvn camel:run
```

---

## 🗄 Exemplo de Requisição

```http
PUT /user
Content-Type: application/json

{
  "id": 1,
  "username": "jdoe",
  "firstName": "John",
  "lastName": "Doe",
  "city": "SP"
}
```

---

## 📊 Fluxo de Processamento

1. Arquivo XML é colocado em `src/data/`
2. Camel detecta e processa automaticamente
3. Transforma XML em objeto User
4. Salva em pasta organizada por cidade: `target/messages/{city}/`
5. Em caso de erro, move para: `target/errors/`

**Exemplo de XML:**
```xml
<person user="jdoe">
    <firstName>John</firstName>
    <lastName>Doe</lastName>
    <city>SP</city>
</person>
```

---

## 🧪 Testando a Aplicação

1. Acesse o endpoint de teste:
   ```
   http://localhost:8080/test
   ```

2. Envie arquivos XML para `src/data/`

3. Verifique os resultados em `target/messages/`

4. Consuma as APIs de usuários para CRUD

---

## 📚 Próximos Passos

* Adicionar persistência em banco de dados
* Melhorar validação de XML com XSD
* Criar testes automatizados com **Camel Test Kit**
* Documentar APIs com **Swagger/OpenAPI**
* Implementar autenticação e autorização
* Adicionar monitoramento e métricas

---

## 👨‍💻 Autor

**Gil Rossi Aguiar**  
📧 [gilrossi.aguiar@live.com](mailto:gilrossi.aguiar@live.com)  
💼 [LinkedIn](https://www.linkedin.com/in/gil-rossi-5814659b/)  
🐙 [GitHub](https://github.com/GilRossi)
