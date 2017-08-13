package com.es.demo.rest.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("productPlanRestService")
public class ProductPlanService {

	@Autowired
	private RestClient client;

	public Response search(String id) {

		Map<String, String> params = new HashMap<String, String>();
		params.put("q", "id:" + id);
		params.put("pretty", "true");
		try {
			Response response = client.performRequest("GET", "/product/product_plan/_search", params);
			return response;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;

	}

}
