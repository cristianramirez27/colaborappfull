package com.coppel.rhconecta.dev.views.modelview;

import java.util.List;

public class GasUnit {

    private String unitNumber;
    private List<GasTicket> tickets;

    public GasUnit(String unitNumber) {
        this.unitNumber = unitNumber;
    }

    public String getUnitNumber() {
        return unitNumber;
    }

    public void setUnitNumber(String unitNumber) {
        this.unitNumber = unitNumber;
    }

    public List<GasTicket> getTickets() {
        return tickets;
    }

    public void setTickets(List<GasTicket> tickets) {
        this.tickets = tickets;
    }

    public class GasTicket {

        private String ticketNumber;
        private String date;
        private String amount;
        private String provider;
        private String invoice;
        private String city;
        private String dateDetail;
        private String amountDetail;

        public GasTicket(String ticketNumber, String date, String amount, String provider,
                         String invoice, String city, String dateDetail, String amountDetail) {
            this.ticketNumber = ticketNumber;
            this.date = date;
            this.amount = amount;
            this.provider = provider;
            this.invoice = invoice;
            this.city = city;
            this.dateDetail = dateDetail;
            this.amountDetail = amountDetail;
        }

        public String getTicketNumber() {
            return ticketNumber;
        }

        public void setTicketNumber(String ticketNumber) {
            this.ticketNumber = ticketNumber;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getProvider() {
            return provider;
        }

        public void setProvider(String provider) {
            this.provider = provider;
        }

        public String getInvoice() {
            return invoice;
        }

        public void setInvoice(String invoice) {
            this.invoice = invoice;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getDateDetail() {
            return dateDetail;
        }

        public void setDateDetail(String dateDetail) {
            this.dateDetail = dateDetail;
        }

        public String getAmountDetail() {
            return amountDetail;
        }

        public void setAmountDetail(String amountDetail) {
            this.amountDetail = amountDetail;
        }
    }
}
