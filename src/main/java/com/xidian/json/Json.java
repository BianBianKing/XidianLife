package com.xidian.json;

import java.util.LinkedList;
import java.util.List;

public class Json {
	private String status_code;
	private List<String> result;
	private List<String> ids;
	public Json(String status_code) {
		super();
		this.status_code = status_code;
	}

	public Json() {
		super();
	}

	public String getStatus_code() {
		return status_code;
	}

	public void setStatus_code(String status_code) {
		this.status_code = status_code;
	}

	public List<String> getResult() {
		return result;
	}

	public void setResult(List<String> result) {
		this.result = result;
	}

	public List<String> getIds() {
		return ids;
	}

	public void setIds(List<String> ids) {
		this.ids = ids;
	}

	@Override
	public String toString() {
		return "Json [status_code=" + status_code + "]";
	}
	
}
