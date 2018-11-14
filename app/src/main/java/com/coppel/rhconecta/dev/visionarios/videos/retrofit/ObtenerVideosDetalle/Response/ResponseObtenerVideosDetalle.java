package com.coppel.rhconecta.dev.visionarios.videos.retrofit.ObtenerVideosDetalle.Response;

public class ResponseObtenerVideosDetalle {


    public class Encuestas {
        public int idencuesta;
        public int estatus;

        public Encuestas(int idencuesta, int estatus) {
            this.idencuesta = idencuesta;
            this.estatus = estatus;
        }

        public Encuestas() {
        }

        public int getIdencuesta() {
            return idencuesta;
        }

        public void setIdencuesta(int idencuesta) {
            this.idencuesta = idencuesta;
        }

        public int getEstatus() {
            return estatus;
        }

        public void setEstatus(int estatus) {
            this.estatus = estatus;
        }
    }

    public class Videos {
        public int idglosario;
        public String contenido;

        public Videos(int idglosario, String contenido) {
            this.idglosario = idglosario;
            this.contenido = contenido;
        }

        public int getIdglosario() {
            return idglosario;
        }

        public void setIdglosario(int idglosario) {
            this.idglosario = idglosario;
        }

        public String getContenido() {
            return contenido;
        }

        public void setContenido(String contenido) {
            this.contenido = contenido;
        }

        public Videos() {
        }
    }

    public static class LogAction {
        public int idlogaction;
        public int tipo_log;

        public LogAction(int idlogaction, int tipo_log) {
            this.idlogaction = idlogaction;
            this.tipo_log = tipo_log;
        }

        public int getIdlogaction() {
            return idlogaction;
        }

        public void setIdlogaction(int idlogaction) {
            this.idlogaction = idlogaction;
        }

        public int getTipo_log() {
            return tipo_log;
        }

        public void setTipo_log(int tipo_log) {
            this.tipo_log = tipo_log;
        }

        public LogAction() {
        }
    }

    public Encuestas encuestas;
    public Videos videos;
    public LogAction logaction;

    public ResponseObtenerVideosDetalle(Encuestas encuestas, Videos videos, LogAction logaction) {
        this.encuestas = encuestas;
        this.videos = videos;
        this.logaction = logaction;
    }

    public ResponseObtenerVideosDetalle() {

    }

    public Encuestas getEncuestas() {
        return encuestas;
    }

    public void setEncuestas(Encuestas encuestas) {
        this.encuestas = encuestas;
    }

    public Videos getVideos() {
        return videos;
    }

    public void setVideos(Videos videos) {
        this.videos = videos;
    }

    public LogAction getLogaction() {
        return logaction;
    }

    public void setLogaction(LogAction logaction) {
        this.logaction = logaction;
    }
}
