package com.coppel.rhconecta.dev.visionarios.firebase;

import java.util.ArrayList;

public class UsuarioFireBase {

    public ArrayList<ComunicadosFirebase> comunicados;
    public ArrayList<VideosFirebase> videos;

    public UsuarioFireBase(ArrayList<ComunicadosFirebase> comunicados, ArrayList<VideosFirebase> videos) {
        this.comunicados = comunicados;
        this.videos = videos;
    }

    public UsuarioFireBase() {
    }
}
