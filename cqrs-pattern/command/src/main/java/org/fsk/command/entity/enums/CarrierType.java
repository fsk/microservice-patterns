package org.fsk.command.entity.enums;

public enum CarrierType {

    ARAS("ARAS"),
    MNG("MNG"),
    YURTICI("YURTICI"),
    UPS("UPS"),
    UNKNOWN("UNKNOWN");

    private final String prefix;

    CarrierType(String prefix) {
        this.prefix = prefix;
    }

    public String getPrefix() {
        return prefix;
    }

    public static CarrierType fromString(String carrierName) {
        try {
            return valueOf(carrierName.toUpperCase());
        } catch (IllegalArgumentException e) {
            return UNKNOWN;
        }
    }
}
