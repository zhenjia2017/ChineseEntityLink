package org.min.weibos;

import java.util.Map;

public class Weibo {

	private String id;
	private String content;
	private Map<String,LinkData> linkDatas;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Map<String, LinkData> getLinkDatas() {
		return linkDatas;
	}
	public void setLinkDatas(Map<String, LinkData> linkDatas) {
		this.linkDatas = linkDatas;
	}

}
