package org.min.entities;

import java.util.Map;

public class Entity {
	
	private String entityId;
	private String name;
	private String category;
	private Map<String,String> property;
	
	
	public Entity(){
		
	}


	public String getEntityId() {
		return entityId;
	}


	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}

	public String getCategory() {
		return category;
	}


	public void setCategory(String category) {
		this.category = category;
	}
	
	public Map<String, String> getProperty() {
		return property;
	}


	public void setProperty(Map<String, String> properties) {
		this.property = properties;
	}

}
