package org.fsk.command.util;

import java.util.Random;

import org.springframework.stereotype.Service;

@Service
public class TrackingNumberGenerator {
    
    public String generate(String carrierName) {
        String timestamp = String.valueOf(System.currentTimeMillis());
        String randomPart = String.format("%04d", new Random().nextInt(10000));
        
        String carrierPrefix = getCarrierPrefix(carrierName);
        
        return carrierPrefix + "-" + timestamp.toString() + "-" + randomPart;
    }
    
    private String getCarrierPrefix(String carrierName) {
        switch (carrierName.toUpperCase()) {
            case "ARAS":
                return "AR";
            case "MNG":
                return "MN";
            case "YURTİÇİ":
                return "YK";
            case "UPS":
                return "UP";
            default:
                return "XX";
        }
    }
}
