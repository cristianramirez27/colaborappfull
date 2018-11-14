package com.coppel.rhconecta.dev.visionarios.comunicados.presenters;

import com.coppel.rhconecta.dev.visionarios.comunicados.interfaces.ComunicadosDetalle;
import com.coppel.rhconecta.dev.visionarios.comunicados.models.ComunicadosDetalleModel;
import com.coppel.rhconecta.dev.visionarios.comunicados.objects.Comunicado;
import com.coppel.rhconecta.dev.visionarios.databases.InternalDatabase;
import com.coppel.rhconecta.dev.visionarios.encuestas.objects.Encuesta;

public class ComunicadosDetallePresenter implements ComunicadosDetalle.Presenter {


    private ComunicadosDetalle.View view;
    private ComunicadosDetalle.Model model;

    public ComunicadosDetallePresenter(ComunicadosDetalle.View view) {
        this.view = view;
        InternalDatabase idb = new InternalDatabase(this.view.getContext());
        this.model = new ComunicadosDetalleModel(this, idb);
    }

    @Override
    public void showComunicadosDetalle(Comunicado comunicado) {
        if (view != null) {
            view.showComunicadosDetalle(comunicado);
        }
    }

    @Override
    public void setComunicadoVisto(Comunicado comunicado) {
        if (this.view != null) {
            this.model.setComunicadoVisto(comunicado);
        }
    }

    @Override
    public void getEncuestaLocal() {
        this.model.getEncuestaLocal();
    }

    @Override
    public void showUltimaEncuesta(Encuesta encuesta) {
        if (this.view != null) {
            this.view.showUltimaEncuesta(encuesta);
        }
    }

    @Override
    public void ShowTextoDiccionario(String text, int textView) {
        if (this.view != null) {
            this.view.ShowTextoDiccionario(text, textView);
        }
    }

    @Override
    public void getTextoLabel(String textNode, String defaultText, int textView) {
        this.model.getTextoLabel(textNode, defaultText, textView);
    }
}
