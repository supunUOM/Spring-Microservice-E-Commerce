package com.dcbf.apigateway.authfilter;

import com.dcbf.apigateway.exception.DepartmentNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    @Autowired
    private RouteValidator routeValidator;

    @Autowired
    private WebClient.Builder webClientBuilder;

    public AuthenticationFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {

            if (routeValidator.isSecured.test(exchange.getRequest())) {

                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    throw new DepartmentNotFoundException("missing authorization header");
                }

                String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
                String[] parts = authHeader.split(" ");

                if (parts.length != 2 || !"Bearer".equals(parts[0])) {
                    throw new RuntimeException("Incorrect authorization structure");
                }

                try {
                    return webClientBuilder.build()
                            .get()
                            .uri("http://auth-service/auth/hello")
                            .retrieve()
                            .bodyToMono(String.class)
                            .map(res -> {
                                exchange.getRequest()
                                        .mutate()
                                        .header("loggedInUser", res);
                                return exchange;
                            }).flatMap(chain::filter);

                } catch (Exception ex) {
                    throw new RuntimeException("un authorized access");
                }
            }
            throw new RuntimeException("Auth error occurred.");
        });
    }

    public static class Config {

    }
}
