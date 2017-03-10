package com.pb.identify.identifyEntity.extractEntities.model;

import java.util.List;

import com.google.gson.annotations.SerializedName;
import com.pb.identify.common.model.UserField;

/**
 * This class represents a single matched record in the response from ExtractEntities API
 *
 */
public class ExtractEntitiesAPIResponse {

	@SerializedName(value="Text")
	private String text;
	@SerializedName(value="Type")
	private String type;
	@SerializedName(value="Count")
	private String count;
	@SerializedName(value="Status")
	private String status;
	@SerializedName(value="Status.Code")
	private String status_code;
	@SerializedName(value="Status.Description")
	private String status_descriptioin;
	private List<UserField> user_fields;
	
	
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getStatus_code() {
		return status_code;
	}
	public void setStatus_code(String status_code) {
		this.status_code = status_code;
	}
	public String getStatus_descriptioin() {
		return status_descriptioin;
	}
	public void setStatus_descriptioin(String status_descriptioin) {
		this.status_descriptioin = status_descriptioin;
	}
	public List<UserField> getUser_fields() {
		return user_fields;
	}
	public void setUser_fields(List<UserField> user_fields) {
		this.user_fields = user_fields;
	}
	
}
