package com.es.demo.rest.server;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.es.demo.model.BulkDocument;
import com.es.demo.model.Category;
import com.es.demo.model.Document;
import com.es.demo.model.Index;
import com.es.demo.model.ProductPlan;
import com.es.demo.rest.service.DocumentService;

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
		// ProductPlanService service = (ProductPlanService) context.getBean("productPlanRestService");
		//
		// Response response = service.search("1");
		//
		// String str = EntityUtils.toString(response.getEntity());
		//
		// System.out.println(response.getStatusLine());
		//
		// System.out.println(str);

		DocumentService service = context.getBean(DocumentService.class);

		List<BulkDocument> list = new ArrayList<BulkDocument>();

		for (int i = 2; i < 13; i++) {
			ProductPlan plan = new ProductPlan();
			plan.setId(i + "");
			plan.setDesc("人生意外保险" + i);
			plan.setName("意外险保障计划" + i);
			plan.setPrice(20 + i);
			plan.setProductId(i + "");
			plan.setProductName("意外险" + i);
			List<Category> categorys = new ArrayList<Category>();
			Category c = new Category();
			c.setId(i + "");
			c.setName("意外险");
			c.setParentId("0");
			c.setProductPlanId(i + "");
			categorys.add(c);
			plan.setCategorys(categorys);

			Document document = new Document();
			document.setIndex("product");
			document.setType("product_plan");
			document.setId(i + "");
			document.setContent(plan);

			Index index = new Index();
			index.setIndex("product");
			index.setType("product_plan");
			index.setId(i + "");

			BulkDocument bulkDocument = new BulkDocument();
			bulkDocument.setDocument(document);
			bulkDocument.setIndex(index);

			list.add(bulkDocument);
		}

		service.insertBulkDocuments(list);
	}

}
