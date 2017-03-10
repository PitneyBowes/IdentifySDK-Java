package com.pb.identify.identifyAddress.validateMailingAddressPremium.model;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.google.gson.annotations.SerializedName;
import com.pb.identify.identifyAddress.common.BaseOptions;
import com.pb.identify.identifyAddress.common.KeepMultimatch;
import com.pb.identify.identifyAddress.common.OutputAddressBlocks;
import com.pb.identify.identifyAddress.common.OutputCasing;
import com.pb.identify.identifyAddress.common.OutputCountryFormat;
import com.pb.identify.identifyAddress.common.OutputFieldLevelReturnCodes;
import com.pb.identify.identifyAddress.common.OutputRecordType;
import com.pb.identify.identifyAddress.common.OutputScript;

/**
 * Model class for ValidateMailingAddressPremium input options
 *
 */
public class Options extends BaseOptions {
	
	public Options(OutputCasing outputCasing, KeepMultimatch keepMultimatch, OutputAddressBlocks outputAddressBlocks,
			OutputCountryFormat outputCountryFormat, OutputScript outputScript, Set<OutputRecordType> outputRecordType, OutputFieldLevelReturnCodes outputFieldLevelReturnCodes,int maximumResults){
		this.setOutputCasing(outputCasing);
		this.setKeepMultimatch(keepMultimatch);
		this.setOutputAddressBlocks(outputAddressBlocks);
		this.setOutputCountryFormat(outputCountryFormat);
		this.setOutputScript(outputScript);
		this.setOutputRecordType(outputRecordType);
		this.setOutputFieldLevelReturnCodes(outputFieldLevelReturnCodes);
		this.setMaximumResults(Integer.toString(maximumResults));
	}
	
	public Options()
	{
		Set<OutputRecordType> outputRecordTypeSet = new HashSet<OutputRecordType>(Arrays.asList(OutputRecordType.A));
		this.setOutputCasing(OutputCasing.M);
		this.setKeepMultimatch(KeepMultimatch.N);
		this.setOutputAddressBlocks(OutputAddressBlocks.Y);
		this.setOutputCountryFormat(OutputCountryFormat.E);
		this.setOutputScript(OutputScript.InputScript);
		this.setOutputRecordType(outputRecordTypeSet);
		this.setOutputFieldLevelReturnCodes(OutputFieldLevelReturnCodes.N);
		this.setMaximumResults("10");
	}

	@SerializedName(value="MaximumResults")
	private String maximumResults;
	@SerializedName(value="KeepMultimatch")
	private KeepMultimatch keepMultimatch;
	@SerializedName(value="OutputCountryFormat")
	private OutputCountryFormat outputCountryFormat;
	@SerializedName(value="OutputScript")
	private OutputScript outputScript;
	@SerializedName(value="OutputAddressBlocks")
	private OutputAddressBlocks outputAddressBlocks;
	@SerializedName(value="OutputRecordType")
	private String outputRecordType;
	@SerializedName(value="OutputFieldLevelReturnCodes")
	private OutputFieldLevelReturnCodes outputFieldLevelReturnCodes;
	public String getMaximumResults() {
		return maximumResults;
	}

	public void setMaximumResults(String maximumResults) {
		this.maximumResults = maximumResults;
	}

	public KeepMultimatch getKeepMultimatch() {
		return keepMultimatch;
	}

	public void setKeepMultimatch(KeepMultimatch keepMultimatch) {
		this.keepMultimatch = keepMultimatch;
	}

	public OutputCountryFormat getOutputCountryFormat() {
		return outputCountryFormat;
	}

	public void setOutputCountryFormat(OutputCountryFormat outputCountryFormat) {
		this.outputCountryFormat = outputCountryFormat;
	}

	public OutputScript getOutputScript() {
		return outputScript;
	}

	public void setOutputScript(OutputScript outputScript) {
		this.outputScript = outputScript;
	}

	public OutputAddressBlocks getOutputAddressBlocks() {
		return outputAddressBlocks;
	}

	public void setOutputAddressBlocks(OutputAddressBlocks outputAddressBlocks) {
		this.outputAddressBlocks = outputAddressBlocks;
	}

	public String getOutputRecordType() {
		return outputRecordType;
	}

	public void setOutputRecordType(Set<OutputRecordType> outputRecordType) {
		StringBuilder sOutputRecordType = new StringBuilder();
		for(OutputRecordType o : outputRecordType)
		{
			sOutputRecordType.append(o.toString());
		}
		this.outputRecordType = sOutputRecordType.toString();
	}

	public OutputFieldLevelReturnCodes getOutputFieldLevelReturnCodes() {
		return outputFieldLevelReturnCodes;
	}

	public void setOutputFieldLevelReturnCodes(
			OutputFieldLevelReturnCodes outputFieldLevelReturnCodes) {
		this.outputFieldLevelReturnCodes = outputFieldLevelReturnCodes;
	}
	
}
