package org.apache.camel.learn;

import org.apache.camel.builder.RouteBuilder;

public class MyRouteBuilder extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        // Configuração de logging detalhado
        getContext().setTracing(true);
        getContext().setMessageHistory(true);

        // ===========================
        // Tratamento de exceções apenas para rotas HTTP
        // ===========================
        onException(Exception.class)
                .onWhen(exchange -> exchange.getIn().getHeader("CamelHttpUri") != null) // Apenas para HTTP
                .handled(true)
                .setHeader("CamelHttpResponseCode", constant(500))
                .setBody(simple("{\"error\": \"${exception.message}\"}"))
                .setHeader("Content-Type", constant("application/json"))
                .log("HTTP Error occurred: ${exception.message}");

        // ===========================
        // Rotas HTTP (mantidas como antes)
        // ===========================
        from("netty-http:http://0.0.0.0:8080/test")
                .log("=== TEST ENDPOINT CALLED ===")
                .setBody(constant("{\"status\": \"ok\", \"message\": \"Test successful\"}"))
                .setHeader("Content-Type", constant("application/json"))
                .removeHeaders("*")
                .log("=== TEST RESPONSE SENT ===");

        from("netty-http:http://0.0.0.0:8080/user/{id}?httpMethodRestrict=GET")
                .log("=== GET /user/${header.id} recebido ===")
                .process(exchange -> {
                    String id = exchange.getIn().getHeader("id", String.class);
                    User user = new UserService().getUser(Integer.parseInt(id));
                    String jsonResponse = String.format(
                            "{\"id\": %d, \"username\": \"%s\", \"firstName\": \"%s\", \"lastName\": \"%s\", \"city\": \"%s\"}",
                            user.getId(), user.getUsername(), user.getFirstName(), user.getLastName(), user.getCity()
                    );
                    exchange.getIn().setBody(jsonResponse);
                })
                .setHeader("Content-Type", constant("application/json"))
                .removeHeaders("*")
                .log("=== USER RESPONSE SENT ===");

        from("netty-http:http://0.0.0.0:8080/user/findAll?httpMethodRestrict=GET")
                .log("=== GET /user/findAll recebido ===")
                .process(exchange -> {
                    User[] users = new UserService().listUsers();
                    StringBuilder jsonBuilder = new StringBuilder("[");
                    for (int i = 0; i < users.length; i++) {
                        User user = users[i];
                        jsonBuilder.append(String.format(
                                "{\"id\": %d, \"username\": \"%s\", \"firstName\": \"%s\", \"lastName\": \"%s\", \"city\": \"%s\"}",
                                user.getId(), user.getUsername(), user.getFirstName(), user.getLastName(), user.getCity()
                        ));
                        if (i < users.length - 1) {
                            jsonBuilder.append(",");
                        }
                    }
                    jsonBuilder.append("]");
                    exchange.getIn().setBody(jsonBuilder.toString());
                })
                .setHeader("Content-Type", constant("application/json"))
                .removeHeaders("*")
                .log("=== FINDALL RESPONSE SENT ===");

        from("netty-http:http://0.0.0.0:8080/user?httpMethodRestrict=PUT")
                .log("=== PUT /user recebido ===")
                .log("Body: ${body}")
                .process(exchange -> {
                    String jsonBody = exchange.getIn().getBody(String.class);
                    User user = parseJsonToUser(jsonBody);
                    User updatedUser = new UserService().updateUser(user);
                    String jsonResponse = String.format(
                            "{\"id\": %d, \"username\": \"%s\", \"firstName\": \"%s\", \"lastName\": \"%s\", \"city\": \"%s\"}",
                            updatedUser.getId(), updatedUser.getUsername(), updatedUser.getFirstName(),
                            updatedUser.getLastName(), updatedUser.getCity()
                    );
                    exchange.getIn().setBody(jsonResponse);
                })
                .setHeader("Content-Type", constant("application/json"))
                .removeHeaders("*")
                .log("=== PUT RESPONSE SENT ===");

        // ===========================
        // Fluxo de processamento de arquivos CORRIGIDO
        // ===========================
        from("file:src/data?noop=true")
                .throttle(1).timePeriodMillis(5000)
                .setProperty("originalBody", body()) // Preserva o conteúdo original do arquivo
                .setProperty("timestamp", simple("${date:now:yyyyMMddHHmmss}"))
                .setProperty("city", xpath("/person/city/text()", String.class))
                .setProperty("username", xpath("/person/@user", String.class))
                .setProperty("firstName", xpath("/person/firstName/text()", String.class))
                .setProperty("lastName", xpath("/person/lastName/text()", String.class))
                .process(exchange -> {
                    User user = new User();
                    user.setUsername(exchange.getProperty("username", String.class));
                    user.setFirstName(exchange.getProperty("firstName", String.class));
                    user.setLastName(exchange.getProperty("lastName", String.class));
                    user.setCity(exchange.getProperty("city", String.class));
                    exchange.getIn().setBody(user);
                })
                .process(exchange -> {
                    User user = exchange.getIn().getBody(User.class);
                    User updatedUser = new UserService().updateUser(user);
                    exchange.getIn().setBody(updatedUser);
                })
                .log("Usuário processado: ${body}")
                .log("Arquivo: ${header.CamelFileName}, Cidade: ${exchangeProperty.city}, TS: ${exchangeProperty.timestamp}")
                .setBody(exchangeProperty("originalBody")) // Restaura o conteúdo original do arquivo
                .toD("file:target/messages/${exchangeProperty.city}?fileName=${exchangeProperty.timestamp}_${exchangeProperty.lastName}.xml");
    }

    private User parseJsonToUser(String json) {
        User user = new User();
        try {
            json = json.replaceAll("[{}\"]", "").trim();
            String[] pairs = json.split(",");
            for (String pair : pairs) {
                String[] keyValue = pair.split(":");
                if (keyValue.length == 2) {
                    String key = keyValue[0].trim();
                    String value = keyValue[1].trim();

                    switch (key) {
                        case "id":
                            user.setId(Integer.parseInt(value));
                            break;
                        case "username":
                            user.setUsername(value);
                            break;
                        case "firstName":
                            user.setFirstName(value);
                            break;
                        case "lastName":
                            user.setLastName(value);
                            break;
                        case "city":
                            user.setCity(value);
                            break;
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Invalid JSON format: " + json, e);
        }
        return user;
    }
}