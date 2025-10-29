package com.example.Crypto.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "billetera_moneda", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"numeroBilletera", "id_moneda"})
})
public class BilleteraMoneda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "numero_Billetera", nullable = false)
    private Billetera billetera;

    @ManyToOne
    @JoinColumn(name = "id_moneda", nullable = false)
    private Moneda moneda;

    @Column(precision = 18, scale = 8)
    private BigDecimal cantidad = BigDecimal.ZERO;
}
