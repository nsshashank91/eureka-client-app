package org.mydemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;

@SpringBootApplication
@EnableDiscoveryClient
public class EurekaClientAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(EurekaClientAppApplication.class, args);
	}
}

@RestController
class ClientController {
//	private String baseUrl = "http://localhost:8091/greet";
	
	@Autowired
	private EurekaClient eurekaClient;
	
	@Autowired
	private RestTemplateBuilder restTemplateBuilder;
	
	@RequestMapping("/")
	public String invokeService() {
		RestTemplate restTemplate = restTemplateBuilder.build();
		
//		using Eureka-Server
//		InstanceInfo instanceInfo = eurekaClient.getNextServerFromEureka("eureka-service-app", false);
//		String baseUrl = instanceInfo.getHomePageUrl();
//		baseUrl = baseUrl + "/greet";
//		return restTemplate.getForObject(baseUrl, String.class);
		
//		using Zuul-Gateway
		InstanceInfo instanceInfo = eurekaClient.getNextServerFromEureka("zuul-gateway", false);
		String baseUrl = instanceInfo.getHomePageUrl();
		baseUrl = baseUrl + "/api/serviceapp/greet";
		return restTemplate.getForObject(baseUrl, String.class);
	}
}
