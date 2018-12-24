package edu.aku.ramshasaeed.clusterfinal.other;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public abstract class DistrictsData {

    private static Map<String, String> districts = new HashMap<>();
    private static ArrayList<String> districtName;

    private static void AddValuesToMap() {
        districts.put("JACOBABAD", "311");
        districts.put("KASHMOR", "312");
        districts.put("SHIKARPUR", "313");
        districts.put("LARKANA", "314");
        districts.put("KAMBAR SHAHDAD KOT", "315");
        districts.put("SUKKUR", "321");
        districts.put("GHOTKI", "322");
        districts.put("KHAIRPUR", "323");
        districts.put("NAUSHAHRO FEROZE", "324");
        districts.put("SHAHEED BENAZIRABAD", "325");
        districts.put("DADU", "331");
        districts.put("JAMSHORO", "332");
        districts.put("HYDERABAD", "333");
        districts.put("TANDO ALLAHYAR", "334");
        districts.put("TANDO MUHAMMAD KHAN", "335");
        districts.put("MATIARI", "336");
        districts.put("BADIN", "337");
        districts.put("THATTA", "338");
        districts.put("SUJAWAL", "339");
        districts.put("SANGHAR", "341");
        districts.put("MIRPUR KHAS", "342");
        districts.put("UMER KOT", "343");
        districts.put("THARPARKAR", "344");
        districts.put("KARACHI WEST", "351");
        districts.put("MALIR", "352");
        districts.put("KARACHI SOUTH DISTRICT", "353");
        districts.put("KARACHI EAST", "354");
        districts.put("KARACHI CENTRAL", "355");
        districts.put("KORANGI DISTRICT", "356");
    }

    public static ArrayList<String> getDistrictNames() {
        AddValuesToMap();

        districtName = new ArrayList<>();
        districtName.add("....");

        Set<String> keys = districts.keySet();
        for (String key : keys) {
            districtName.add(key);
        }

        return districtName;
    }

    public static String getDistrictCode(String districtName) {
        return districts.get(districtName);
    }


}
