package com.example.Crypto.Dtos;

import com.example.Crypto.Entities.Billetera;

public class BilleteraResponse {
    private boolean success;
    private String message;
    private Billetera billetera;

    public BilleteraResponse(boolean success, String message, Billetera billetera) {
        this.success = success;
        this.message = message;
        this.billetera = billetera;
    }

    // Getters y Setters
    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public Billetera getBilletera() { return billetera; }
    public void setBilletera(Billetera billetera) { this.billetera = billetera; }
}
