package com.fahrschule.sevim.models;

import java.util.ArrayList;
import java.util.List;

public class TheorieImageItemSource {

    private List<String> deWeddingGalleryPagesList;

    private List<String> trWeddingGalleryPagesList;

    private List<String> deReinickendorfGalleryPagesList;

    private List<String> trReinickendorfGalleryPagesList;

    private String weddingPlanImageUrl;

    private String reinickendorfPlanImageUrl;

    private String selectedLanguage = "de"; //using german as default

    public TheorieImageItemSource() {
        fillWeddingData();
        fillReinickendorfData();
    }

    public TheorieImageItemSource(String selectedLanguage) {
        this();
        this.selectedLanguage = selectedLanguage;
    }

    private void fillWeddingData() {
        weddingPlanImageUrl = "http://fahrschule-sevim.ornatesol.com/theory/Wedding.png";

        deWeddingGalleryPagesList = new ArrayList<>();
        deWeddingGalleryPagesList.add("http://fahrschule-sevim.ornatesol.com/theory/de/wedding/1_de_Di_Do_Sa.png");
        deWeddingGalleryPagesList.add("http://fahrschule-sevim.ornatesol.com/theory/de/wedding/2_de_vormittags_abends.png");

        trWeddingGalleryPagesList = new ArrayList<>();
        trWeddingGalleryPagesList.add("http://fahrschule-sevim.ornatesol.com/theory/tr/wedding/1_tr_en_vormittags_abends.png");
    }

    private void fillReinickendorfData() {
        reinickendorfPlanImageUrl = "http://fahrschule-sevim.ornatesol.com/theory/Reinickendorf.png";

        deReinickendorfGalleryPagesList = new ArrayList<>();
        deReinickendorfGalleryPagesList.add("http://fahrschule-sevim.ornatesol.com/theory/de/reinickendorf/1_abend.png");
        deReinickendorfGalleryPagesList.add("http://fahrschule-sevim.ornatesol.com/theory/de/reinickendorf/2_vormittag.png");

        trReinickendorfGalleryPagesList = new ArrayList<>();
        trReinickendorfGalleryPagesList.add("http://fahrschule-sevim.ornatesol.com/theory/tr/reinickendorf/1_tr_en_di_do.png");
    }

    public List<String> getDeWeddingGalleryPagesList() {
        return deWeddingGalleryPagesList;
    }

    public List<String> getTrWeddingGalleryPagesList() {
        return trWeddingGalleryPagesList;
    }

    public List<String> getDeReinickendorfGalleryPagesList() {
        return deReinickendorfGalleryPagesList;
    }

    public List<String> getTrReinickendorfGalleryPagesList() {
        return trReinickendorfGalleryPagesList;
    }

    public String getWeddingPlanImageUrl() {
        return weddingPlanImageUrl;
    }

    public String getReinickendorfPlanImageUrl() {
        return reinickendorfPlanImageUrl;
    }
}
