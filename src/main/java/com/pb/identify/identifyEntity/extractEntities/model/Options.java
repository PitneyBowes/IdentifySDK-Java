package com.pb.identify.identifyEntity.extractEntities.model;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

/**
 * Model class for ExtractEntities input options
 *
 */
public class Options {
	
	@SerializedName(value="EntityList")
	private String entityLists;
	@SerializedName(value="OutputEntityCount")
	private String outputEntityCount;
	
	public Options(){
		List<EntityList> entityListSet = new ArrayList<EntityList>(Arrays.asList(EntityList.Person,EntityList.Address));
		this.setEntityLists(entityListSet);
	}
	
	public Options(List<EntityList> entityLists){
		this.setEntityLists(entityLists);
	}

	public String getEntityLists() {
		return entityLists;
	}

	public void setEntityLists(List<EntityList> entityLists) {
		StringBuilder sb = new StringBuilder();
		for(EntityList el: entityLists){
			sb.append(el.toString());
			sb.append(",");
		}
		
		this.entityLists=sb.substring(0, sb.length()-1);
	}

	public String getOutputEntityCount() {
		return outputEntityCount;
	}

	public void setOutputEntityCount(String outputEntityCount) {
		this.outputEntityCount = outputEntityCount;
	}
	
	
	
}
