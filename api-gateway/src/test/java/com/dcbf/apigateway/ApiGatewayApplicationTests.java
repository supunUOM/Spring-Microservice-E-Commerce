package com.dcbf.apigateway;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@SpringBootTest
class ApiGatewayApplicationTests {

	@Autowired
	private EmployeeClient employeeClient;

	@Autowired
	private AuthClient authClient;

	@Test
	void restCallTest() {
		var res = employeeClient.findByDepartment(1L);
		System.out.println(res);
	}

	@Test
	void helloTest(){
		var res = authClient.hello();
		System.out.println(res);
	}

	@Test
	void webClientBlockingRestCallTest(){
		WebClient client = WebClient.builder().build();

		Mono<String> product = client.get()
				.uri("https://jsonplaceholder.typicode.com/todos/1")
				.retrieve()
				.bodyToMono(String.class);
		System.out.println(product.block());
	}

}
