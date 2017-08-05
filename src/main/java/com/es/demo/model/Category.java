package com.es.demo.model;

/**
 * 
 * <p>
 * Title:Category
 * </p>
 * <p>
 * Description: 产品分类
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author linyb
 * @date 2017年8月5日
 */
public class Category {
	private String productPlanId;
	private String id;
	private String name;
	private String parentId;

	public String getProductPlanId() {
		return productPlanId;
	}

	public void setProductPlanId(String productPlanId) {
		this.productPlanId = productPlanId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

}
