package com.es.demo.javaclient.server;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.es.demo.javaclient.service.ProductPlanService;

@Configuration
@ComponentScan(basePackages = "com.es.demo.*")
@PropertySource(value = "classpath:/app.properties")
public class SpringEsClient {

	@Value("${es.hosts}")
	private String esHosts;
	@Value("${cluster.name}")
	private String clusterName;

	@Bean
	public TransportClient getTransportClient() {
		String[] hosts = esHosts.split(",");
		Settings settings = Settings.builder().put("cluster.name", clusterName).build();

		TransportClient client = null;
		try {
			client = new PreBuiltTransportClient(settings)
					.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(hosts[0]), 9300));
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("host2"), 9300));

		return client;
	}

	public static void main(String[] args) {
		ApplicationContext context = new AnnotationConfigApplicationContext(SpringEsClient.class);
		ProductPlanService service = context.getBean(ProductPlanService.class);

		GetResponse response = service.getResponse("1");

		System.out.println(response.getSourceAsMap());
	}

}
