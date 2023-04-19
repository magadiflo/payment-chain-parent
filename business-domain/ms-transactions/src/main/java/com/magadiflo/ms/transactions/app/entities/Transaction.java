package com.magadiflo.ms.transactions.app.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String reference; //Identificador único de la transacción para el negocio
    private String accountIban; //Número de cuenta bancaria del cliente
    @Temporal(TemporalType.TIMESTAMP)
    private Date date; //Fecha en que se realizó la transacción
    private Double amount; //Monto de la transacción. Si el valor es negativo, será un débito (disminuye el saldo); si es positivo, será un crédito (aumenta el saldo)
    private Double fee; //Comisión de la transacción
    private String descripcion; //Descripción breve de la transacción
    @Enumerated(EnumType.STRING)
    private Status status; //Guarda el estado de la transacción y podrá ser uno de los siguientes códigos numéricos definidos ene l enum que representa los estados descritos
    @Enumerated(EnumType.STRING)
    private Channel channel; //Indica el canal por el que se realiza la transacción y debe ser uno de los valores definidos en el enum
}
