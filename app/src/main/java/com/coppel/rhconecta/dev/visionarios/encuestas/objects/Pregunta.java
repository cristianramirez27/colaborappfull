package com.coppel.rhconecta.dev.visionarios.encuestas.objects;

import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;

public class Pregunta implements Serializable {

    private int idcuestionario;
    private int idpregunta;
    private int idpreguntas;
    private String contenido;
    private String respuetascuestionario;
    private ArrayList<Respuesta> respuestas = new ArrayList<Respuesta>();

    public Pregunta() {
    }

    public Pregunta(int idcuestionario, int idpregunta, int idpreguntas, String contenido, String respuetascuestionario) {
        this.idcuestionario = idcuestionario;
        this.idpregunta = idpregunta;
        this.idpreguntas = idpreguntas;
        this.contenido = contenido;
        this.respuetascuestionario = respuetascuestionario;
    }

    public ArrayList<Respuesta> getRespuestas() {
        return respuestas;
    }

    public void setRespuestas(ArrayList<Respuesta> respuestas) {
        this.respuestas = respuestas;
    }

    public int getIdcuestionario() {
        return idcuestionario;
    }

    public void setIdcuestionario(int idcuestionario) {
        this.idcuestionario = idcuestionario;
    }

    public int getIdpregunta() {
        return idpregunta;
    }

    public void setIdpregunta(int idpregunta) {
        this.idpregunta = idpregunta;
    }

    public int getIdpreguntas() {
        return idpreguntas;
    }

    public void setIdpreguntas(int idpreguntas) {
        this.idpreguntas = idpreguntas;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public String getRespuetascuestionario() {
        return respuetascuestionario;
    }

    public void setRespuetascuestionario(String respuetascuestionario) {
        this.respuetascuestionario = respuetascuestionario;
    }

    public void decodingRespuestas() {
        String tmp = this.respuetascuestionario;
        tmp = tmp.replace("||", ";");

        tmp = tmp.replace("^", ",");

        String[] resps = tmp.split(";");

        for (int i = 0; i < resps.length; i++) {

            String[] respuesta = resps[i].split(",");
            try {
                this.respuestas.add(new Respuesta(Integer.parseInt(respuesta[0].trim()), respuesta[1].trim()));

            } catch (Exception e) {
                Log.d("ERRORResp", e.getMessage());
                this.respuestas = new ArrayList<Respuesta>();
            }
        }
    }

    public void resetRespuestas() {
        for (int i = 0; i < this.respuestas.size(); i++) {
            this.respuestas.get(i).setSeleccionado(false);
        }
    }
}