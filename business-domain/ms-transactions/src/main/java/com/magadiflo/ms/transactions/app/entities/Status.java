package com.magadiflo.ms.transactions.app.entities;

public enum Status {
    PENDIENTE("01"),
    LIQUIDADA("02"),
    RECHAZADA("03"),
    CANCELADA("04");

    private final String code;

    Status(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
