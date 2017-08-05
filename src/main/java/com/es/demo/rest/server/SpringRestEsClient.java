package com.es.demo.rest.server;

import org.apache.http.HttpHost;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.es.demo.rest.service.ProductPlanService;

@Configuration
@ComponentScan(basePackages = "com.es.demo.*")
@PropertySource(value = "classpath:/app.properties")
public class SpringRestEsClient {

	@Value("${es.hosts}")
	private String esHosts;

	@Bean
	public RestClient getRestClientt() {
		String[] hosts = esHosts.split(",");
		RestClient restClient = RestClient.builder(new HttpHost(hosts[0], 9200, "http")).build();
		// new HttpHost("localhost", 9201, "http")).build();
		return restClient;
	}

	public static void main(String[] args) throws Exception {
		ApplicationContext context = new AnnotationConfigApplicationContext(SpringRestEsClient.class);
		ProductPlanService service = (ProductPlanService) context.getBean("productPlanRestService");

		Response response = service.search("1");

		String str = EntityUtils.toString(response.getEntity());

		System.out.println(response.getStatusLine());

		System.out.println(str);
	}

}
