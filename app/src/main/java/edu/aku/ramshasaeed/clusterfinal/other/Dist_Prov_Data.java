package edu.aku.ramshasaeed.clusterfinal.other;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public abstract class Dist_Prov_Data {

    private static Map<String, String> districts = new HashMap<>(), province = new HashMap<>();
    private static ArrayList<String> districtName, provinceName;

    private static void AddDistrictValuesToMap() {
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

    private static void AddProvinceValuesToMap() {
        province.put("KHYBER PAKHTUNKHWA", "1");
        province.put("PUNJAB", "2");
        province.put("SINDH", "3");
        province.put("BALOCHISTAN", "4");
        province.put("FATA", "5");
        province.put("FEDERAL CAPITAL", "6");
        province.put("GILGIT BALTISTAN", "7");
        province.put("AZAD JAMMU AND KASHMIR", "8");
        province.put("ADJACENT AREAS-FR", "9");
    }

    public static ArrayList<String> getDistrictNames() {
        AddDistrictValuesToMap();

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

    public static ArrayList<String> getProvinceNames() {
        AddProvinceValuesToMap();

        provinceName = new ArrayList<>();
        provinceName.add("....");

        Set<String> keys = province.keySet();
        for (String key : keys) {
            provinceName.add(key);
        }

        return provinceName;
    }

    public static String getProvinceCode(String provinceName) {
        return province.get(provinceName);
    }


}
