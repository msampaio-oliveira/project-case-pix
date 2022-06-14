package br.com.project.pix.repository;

import br.com.project.pix.dto.projection.PixLimitMaxKeyValueProjection;
import br.com.project.pix.dto.projection.PixAccountUserDetailsProjection;
import br.com.project.pix.model.PixAccountUserDetails;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PixAccountUserDetailsRepository extends JpaRepository<PixAccountUserDetails, Long> {

    @Query(value = "SELECT * " + //
            " FROM tpix_account_user_details P WHERE P.valor_chave = :keyValue " + //
            " AND P.data_hora_inativação_da_chave IS NULL", nativeQuery = true)
    Optional<PixAccountUserDetailsProjection> findByKeyValue(String keyValue);

    @Query(value = "SELECT count(*) AS countKeyValue, P.tipo_pessoa AS personType  " + //
            " FROM tpix_account_user_details AS P " + //
            " WHERE P.numero_agencia = :agencyNumber " + //
            " AND P.numero_conta = :accountNumber " + //
            " AND P.data_hora_inativação_da_chave IS NULL", nativeQuery = true)
    Optional<PixLimitMaxKeyValueProjection> findByNumberKeyPix(Integer agencyNumber, Integer accountNumber);

    @NotNull
    @Override
    @Query(value = "SELECT * " + //
            " FROM tpix_account_user_details P WHERE P.id = :id " + //
            " AND P.data_hora_inativação_da_chave IS NULL", nativeQuery = true)
    Optional<PixAccountUserDetails> findById(@NotNull Long id);

    @Query(value = "SELECT * " + //
            " FROM tpix_account_user_details P WHERE P.id = :id " + //
            " AND P.data_hora_inativação_da_chave IS NULL", nativeQuery = true)
    Optional<PixAccountUserDetailsProjection> findProjectionById(Long id);

    @Query(value = "SELECT P.id AS id, P.tipo_chave AS keyType, P.valor_chave AS keyValue, P.tipo_conta AS accountType," + //
            "P.numero_agencia AS agencyNumber, P.numero_conta AS accountNumber, P.nome_correntista AS accountHolderName," + //
            "P.sobrenome_correntista AS accountHolderLastLame, P.tipo_pessoa AS personType, P.data_hora_inclusao_da_chave AS inclusionKeyDateTime," + //
            "P.data_hora_inativação_da_chave AS inactiveKeyDateTime  " + //
            "FROM tpix_account_user_details P " + //
            " WHERE (:keyValue IS NULL OR P.tipo_chave = :keyValue) " + //
            "   AND (:agencyNumber IS NULL OR P.numero_agencia = :agencyNumber) " + //
            "   AND (:accountNumber  IS NULL OR P.numero_conta  = :accountNumber) " + //
            "   AND (:accountHolderName IS NULL OR P.nome_correntista = :accountHolderName) " + //
            "   AND (:id IS NULL OR P.id = :id) " +
            "   AND (:dateActiveKey IS NULL OR P.data_hora_inclusao_da_chave like :dateActiveKey)" +
            "   AND (:dateInactiveKey IS NULL OR P.data_hora_inativação_da_chave like :dateInactiveKey)", nativeQuery = true)
    List<PixAccountUserDetailsProjection> findAllWithParameters(
            String keyValue,
            String agencyNumber,
            String accountNumber,
            String accountHolderName,
            String id,
            String dateActiveKey,
            String dateInactiveKey);
}
