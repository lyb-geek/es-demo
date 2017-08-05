package com.es.demo.javaclient;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import com.alibaba.fastjson.JSON;
import com.es.demo.model.Category;
import com.es.demo.model.ProductPlan;

public class EsClient {
	public static void main(String[] args) throws Exception {
		Settings settings = Settings.builder().put("cluster.name", "my-application").build();

		TransportClient client = new PreBuiltTransportClient(settings)
				.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("127.0.0.1"), 9300));
		// .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("host2"), 9300));

		ProductPlan plan = new ProductPlan();
		plan.setId("2");
		plan.setDesc("人生意外保险");
		plan.setName("意外险保障计划");
		plan.setPrice(20);
		plan.setProductId("2");
		plan.setProductName("意外险");
		List<Category> categorys = new ArrayList<Category>();
		Category c = new Category();
		c.setId("1");
		c.setName("意外险");
		c.setParentId("0");
		c.setProductPlanId("2");
		categorys.add(c);
		plan.setCategorys(categorys);
		String json = JSON.toJSONString(plan);

		IndexResponse response = client.prepareIndex("product", "product_plan", "2").setSource(json, XContentType.JSON)
				.get();

		if (response != null) {
			System.out.println(response.getResult());
		}
	}

}
