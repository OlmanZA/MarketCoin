package com.example.Crypto.Repository;

import com.example.Crypto.Entities.BilleteraMoneda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface BilleteraMoneda_Repositorio extends JpaRepository<BilleteraMoneda, Long> {

    boolean existsByBilleteraNumeroBilleteraAndMonedaIdMoneda(Long numero_billetera, Long idMoneda);

    Optional<BilleteraMoneda> findByBilleteraNumeroBilleteraAndMonedaIdMoneda(Long numero_billetera, Long idMoneda);
}
