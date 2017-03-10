package com.pb.identify.identifyAddress.common;

import com.google.gson.annotations.SerializedName;

/**
 * Base class for ValidateMailingAddress input options
 *
 */
public abstract class BaseOptions {

	@SerializedName(value="OutputCasing")
	private OutputCasing outputCasing;
	
	public OutputCasing getOutputCasing() {
		return outputCasing;
	}

	public void setOutputCasing(OutputCasing outputCasing) {
		this.outputCasing = outputCasing;
	}
}
