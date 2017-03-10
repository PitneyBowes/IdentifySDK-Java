package com.pb.identify.identifyEntity.extractEntities.model;

import java.util.List;

import com.google.gson.annotations.SerializedName;
import com.pb.identify.common.model.UserField;

/**
 * Model class for ExtractEntities input
 *
 */
public class Record {

	@SerializedName(value="PlainText")
	private String plainText;
	@SerializedName(value="user_fields")
	private List<UserField> userFields;
	
	public List<UserField> getUserFields() {
		return userFields;
	}
	public String getPlainText() {
		return plainText;
	}
	public void setPlainText(String plainText) {
		this.plainText = plainText;
	}
	public void setUserFields(List<UserField> userFields) {
		this.userFields = userFields;
	}
	
	
}
