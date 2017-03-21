package com.fahrschule.sevim.models;

import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import com.fahrschule.sevim.R;
import java.util.ArrayList;

public enum InfoItemsSource {

    SCHALTUNG(R.string.schaltung, R.drawable.schaltung),
    FAHRLEHRER(R.string.fahrlehrer, R.drawable.fahrlehrer),
    UMSCREIBUNG(R.string.ausl_fuehrerschein, R.drawable.ausl_fuehrerschein),
    FUEHRERSCEIN(R.string.fuehrerschein_17, R.drawable.fuehrerschein_17),
    INTENSIVE_AUSBILDUNG(R.string.intensive_ausbildung, R.drawable.intensive_ausbildung),
    THEORIEUNTERRICHT(R.string.theorieunterricht, R.drawable.theorieunterricht),
    MODERNE_FAHRZEUGE(R.string.moderne_fahrzeuge, R.drawable.moderne_fahrzeuge),
    LKW(R.string.lkw, R.drawable.lkw),
    ASF(R.string.asf, R.drawable.asf),
    AUFFRISCHUNG(R.string.auffrischung, R.drawable.auffrischung),
    ANGSTHASEN(R.string.angsthasen, R.drawable.angsthasen),
    SPRITSPAR(R.string.spritspar, R.drawable.spritspar),
    ERSTE_HILFE(R.string.erste_hilfe, R.drawable.erste_hilfe),
    SEETEST(R.string.seetest, R.drawable.seetest),
    THEORIEVORBEREITUNG(R.string.theorievorbereitung, R.drawable.theorievorbereitung),
    KLIMA_RAEUME(R.string.klima_raeume, R.drawable.klima_raeume),
    DEUTSCH_TUERKISCH(R.string.deutsch_tuerkisch, R.drawable.deutsch_tuerkisch),
    LANGE_BUEROZEITEN(R.string.lange_buerozeiten, R.drawable.lange_buerozeiten),
    BERATUNG(R.string.beratung, R.drawable.beratung),
    EXPRESS_FUEHRERSCEIN(R.string.express_fuehrerschein, R.drawable.express_fuehrerschein),
    ANTRAEGE(R.string.antraege, R.drawable.antraege);

    @StringRes
    public final int textResId;

    @DrawableRes
    public final int drawableResId;

    InfoItemsSource(int textResId, int drawableResId) {
        this.textResId = textResId;
        this.drawableResId = drawableResId;
    }

    public static ArrayList<InfoItemsSource> getInfoItems() {
        ArrayList<InfoItemsSource> arrayList = new ArrayList<>();
        for(InfoItemsSource item: values()) {
            arrayList.add(item);
        }
        return arrayList;
    }
}
