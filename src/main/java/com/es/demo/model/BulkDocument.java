package com.es.demo.model;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;

public class BulkDocument {
	private Index index;

	@JSONField(serialize = false)
	private Document document;

	public Index getIndex() {
		return index;
	}

	public void setIndex(Index index) {
		this.index = index;
	}

	public Document getDocument() {
		return document;
	}

	public void setDocument(Document document) {
		this.document = document;
	}

	public static void main(String[] args) {
		Index index = new Index();
		index.setId("1");
		index.setType("2");
		index.setIndex("1");

		BulkDocument document = new BulkDocument();
		document.setIndex(index);

		System.out.println(JSON.toJSONString(document));
	}

}
