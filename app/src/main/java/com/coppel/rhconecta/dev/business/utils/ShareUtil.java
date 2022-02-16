package com.coppel.rhconecta.dev.business.utils;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import com.coppel.rhconecta.dev.business.models.Section;
import com.coppel.rhconecta.dev.business.models.SubSection;
import com.coppel.rhconecta.dev.resources.db.RealmHelper;
import com.coppel.rhconecta.dev.resources.db.models.MainSection;
import com.coppel.rhconecta.dev.resources.db.models.SectionDb;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by faust on 1/24/18.
 */

public class ShareUtil {



    public static void shareString(Context context, String url) {
        try {

            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, url);
            sendIntent.setType("text/plain");
            context.startActivity(sendIntent);

        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
            //Alerts.showToast(this, "No es posible compartir el artículo", Toast.LENGTH_SHORT);
        }
    }

    public static synchronized void toSaveMainSection(List<Section> list) {
        List<MainSection> items = new ArrayList<>();
        List<SectionDb> subSections = new ArrayList<>();
        if (list != null && !list.isEmpty()) {
            for (Section sec : list) {
                for (SubSection sub : sec.getSubseccion()) {
                    subSections.add(new SectionDb(sec.getIdu_seccion(), sub.getIdu_subseccion(), sub.getSubseccion_nombre()));
                }
                items.add(new MainSection(sec.getIdu_seccion(), sec.getSeccion_nombre(), subSections));
            }
        }
        RealmHelper.deleteSection();
        RealmHelper.deleteSubSection();
        RealmHelper.insertOrUpdate(items);
        RealmHelper.insertOrUpdate(subSections);
    }
}
