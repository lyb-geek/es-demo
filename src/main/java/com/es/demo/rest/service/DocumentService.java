package com.es.demo.rest.service;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.nio.entity.NStringEntity;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.es.demo.model.BulkDocument;
import com.es.demo.model.Document;

@Service
public class DocumentService {

	@Autowired
	private RestClient restClient;

	public String insertSingleDocument(Document document) {
		HttpEntity entity = new NStringEntity(JSON.toJSONString(document.getContent()), ContentType.APPLICATION_JSON);
		String endpoint = "/" + document.getIndex() + "/" + document.getType() + "/" + document.getId();

		try {
			restClient.performRequest("PUT", endpoint, Collections.<String, String> emptyMap(), entity);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "add success";
	}

	public String insertBulkDocuments(List<BulkDocument> docs) {
		// { "index" : { "_index" : "test", "_type" : "type1", "_id" : "1" } }
		// { "field1" : "value1" }
		if (docs == null || docs.size() == 0) {
			return "batch add field";
		}
		StringBuilder sb = new StringBuilder();
		for (BulkDocument doc : docs) {
			sb.append(JSON.toJSONString(doc)).append("\n");
			sb.append(JSON.toJSONString(doc.getDocument().getContent()));
			sb.append("\n");
		}
		Response response = null;
		try {
			HttpEntity entity = new NStringEntity(sb.toString(), ContentType.APPLICATION_JSON);
			String endpoint = "/" + docs.get(0).getDocument().getIndex() + "/" + docs.get(0).getDocument().getType()
					+ "/_bulk";
			response = restClient.performRequest("PUT", endpoint, Collections.<String, String> emptyMap(), entity);
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (response != null && response.getStatusLine().getStatusCode() == 200) {
			System.out.println("保存成功！");
		}

		return "batch add success";
	}

}
