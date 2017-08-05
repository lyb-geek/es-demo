package com.es.demo.javaclient.service;

import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductPlanService {

	@Autowired
	private TransportClient client;

	public GetResponse getResponse(String id) {
		GetResponse response = client.prepareGet("product", "product_plan", id).get();

		return response;
	}

}
