package com.gamebox.util;

import java.io.File;
import java.io.IOException;

import com.gamebox.util.geoip.LookupService;


public class CountryUtils {
    public static LookupService IpFromCountry;
    static {
        if (IpFromCountry == null) {
            try {
                IpFromCountry = new LookupService(new File(FileUtils.rootPath
                        + "ip/GeoIP.dat"), LookupService.GEOIP_MEMORY_CACHE
                        | LookupService.GEOIP_CHECK_CACHE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    public static String getCountry(String ip) {
        return IpFromCountry.getCountry(ip).getName();
    }
}
