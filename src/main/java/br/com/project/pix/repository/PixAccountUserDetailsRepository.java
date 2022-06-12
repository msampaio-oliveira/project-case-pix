package br.com.project.pix.repository;

import br.com.project.pix.dto.PixLimitMaxKeyValueDTO;
import br.com.project.pix.dto.projection.PixAccountUserDetailsProjection;
import br.com.project.pix.model.PixAccountUserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PixAccountUserDetailsRepository extends JpaRepository<PixAccountUserDetails, Long> {

    Optional<PixAccountUserDetailsProjection> findByKeyValue(String keyValue);

    @Query(value = "SELECT count(*) AS countKeyValue, P.tipo_pessoa AS personType  " + //
            " FROM tpix_account_user_details AS P " + //
            " WHERE P.numero_agencia = :agencyNumber " + //
            " AND P.numero_conta = :accountNumber " + //
            " AND P.data_hora_inativação_da_chave IS NULL", nativeQuery = true)
    Optional<PixLimitMaxKeyValueDTO> findByNumberKeyPix(Integer agencyNumber, Integer accountNumber);

}
