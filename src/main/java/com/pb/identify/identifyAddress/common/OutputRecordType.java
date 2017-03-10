package com.pb.identify.identifyAddress.common;

/**
 * 
 * Enum to set output record type options for response
 * 
 *
 */
public enum OutputRecordType {

	/**
     * Returns 1 to 4 lines of address data plus city, state, postal code, firm name 
     * and urbanization name information. (default)
     */
    A,
    /**
     * Parsed address elements. Each part of the address, such as house number,
     * street name, street suffix, directional, and so on is returned in a separate field.
     */
    E,
    /**
     * Parsed input. This option returns the input address in parsed form regardless of whether the address is validated.
     * Each part of the input address, such as house number, street name, street suffix, directional,
     * and so on is returned in a separate field.
     */
    I
}
