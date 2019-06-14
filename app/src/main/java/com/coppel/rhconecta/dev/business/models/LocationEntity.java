package com.coppel.rhconecta.dev.business.models;

public abstract class LocationEntity{
        protected String nombre;

    protected boolean isSelected;

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}