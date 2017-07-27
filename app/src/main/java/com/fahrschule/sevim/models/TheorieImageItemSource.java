package com.fahrschule.sevim.models;

import java.util.ArrayList;
import java.util.List;

public class TheorieImageItemSource {

    private static List<String> germanPlanList;

    private static List<String> turkishPlanList;

    public static List<String> getGermanPlanList() {
        fillGermanPlanList();
        return germanPlanList;
    }

    public static List<String> getTurkishPlanList() {
        fillTurkishPlanList();
        return turkishPlanList;
    }

    //TODO should be filled later from url
    private static void fillGermanPlanList() {
        germanPlanList = new ArrayList<>();
        germanPlanList.add("http://fahrschule-sevim.ornatesol.com/theory/de/1_Deutsch_Di_Do_Sa.png");
        germanPlanList.add("http://fahrschule-sevim.ornatesol.com/theory/de/2_Deutsch_Nachmittag_Page_1.png");
        germanPlanList.add("http://fahrschule-sevim.ornatesol.com/theory/de/3_Deutsch_Nachmittag_Page_2.png");
        germanPlanList.add("http://fahrschule-sevim.ornatesol.com/theory/de/4_Scharni%20Vormittag%20Deutsch.png");
    }

    private static void fillTurkishPlanList() {
        turkishPlanList = new ArrayList<>();
        turkishPlanList.add("http://fahrschule-sevim.ornatesol.com/theory/tr/1_DI%20DO%20T%C3%9CRKCE%20YEP%20YENI.png");
        turkishPlanList.add("http://fahrschule-sevim.ornatesol.com/theory/tr/2_Scharni%20T%C3%BCrkisch_Page_1.png");
        turkishPlanList.add("http://fahrschule-sevim.ornatesol.com/theory/tr/3_Scharni%20T%C3%BCrkisch_Page_2.png");
    }

    public static String getPlanForWedding() {
        return "http://fahrschule-sevim.ornatesol.com/theory/theory_1.png";
    }

    public static String getPlanForReinickendorf() {
        return "http://fahrschule-sevim.ornatesol.com/theory/theory_1.png";
    }

}
