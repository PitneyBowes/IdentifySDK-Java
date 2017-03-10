package com.pb.identify.identifyAddress.common;

/**
 * 
 * Enum to set output script options for response
 * 
 */
public enum OutputScript {

	/**
	 * Do not perform transliteration and provide output in the same script as the input (default).
	 */
	InputScript,
	/**
	 * Output in the native script for the selected country wherever possible.
	 */
	Native,
	/**
	 * Use English values.
	 */
	Latn
}
