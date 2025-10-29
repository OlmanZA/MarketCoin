package com.example.Crypto.Repository;

import com.example.Crypto.Entities.Billetera_Moneda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface Billetera_Moneda_Repositorio extends JpaRepository<Billetera_Moneda, Long> {

    boolean existsByBilleteraNumeroBilleteraAndMonedaIdMoneda(Long numero_billetera, Long idMoneda);

    Optional<Billetera_Moneda> findByBilleteraNumeroBilleteraAndMonedaIdMoneda(Long numero_billetera, Long idMoneda);
}
