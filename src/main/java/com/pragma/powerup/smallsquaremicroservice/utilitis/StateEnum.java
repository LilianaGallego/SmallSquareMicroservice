package com.pragma.powerup.smallsquaremicroservice.utilitis;

public enum StateEnum {
    PREPARATION("PREPARACION"),
    EARNING("PENDIENTE"),
    READY("LISTO"),
    DELIVERED("ENTREGADO"),
    CANCELLED("CANCELADO");

    private final String name;
    StateEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
