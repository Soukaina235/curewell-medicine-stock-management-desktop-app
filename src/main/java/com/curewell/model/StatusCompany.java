package com.curewell.model;
public enum StatusCompany {
    Normal, VIP;
    public static StatusCompany valueOfIgnoreCase(String value) {
        for (StatusCompany status : values()) {
            if (status.name().equalsIgnoreCase(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("No enum constant " + StatusCompany.class + "." + value);
    }
}
