package com.coppel.rhconecta.dev.data.rooms.dto

data class Reservation(val estadoReservacion : String,
                       val fechaFin : String,
                       val fechaInicio : String,
                       val iduReservacion : Int,
                       val isOrganizador : Boolean,
                       val isTiempoCheckIn : Boolean,
                       val isTiempoCheckOut : Boolean,
                       val nombreSala: String,
                       val tema: String,
                       val ubicacion: String) {
}