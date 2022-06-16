package br.com.project.pix.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "TPIX_ACCOUNT_USER_DETAILS")
public class PixAccountUserDetails {

    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name = "TIPO_CHAVE", nullable = false, length = 9)
    private String keyType;

    @Column(name = "VALOR_CHAVE", nullable = false, length = 77)
    private String keyValue;

    @Column(name = "TIPO_CONTA", nullable = false, length = 10)
    private String accountType;

    @Column(name = "NUMERO_AGENCIA", nullable = false, length = 4)
    private String agencyNumber;

    @Column(name = "NUMERO_CONTA", nullable = false, length = 8)
    private String accountNumber;

    @Column(name = "NOME_CORRENTISTA", nullable = false, length = 30)
    private String accountHolderName;

    @Column(name = "SOBRENOME_CORRENTISTA", length = 45)
    private String accountHolderLastName;

    @Column(name = "TIPO_PESSOA", nullable = false, length = 1)
    private Character personType;

    @Column(name = "DATA_HORA_INCLUSAO_DA_CHAVE")
    private LocalDateTime inclusionKeyDateTime;

    @Column(name = "DATA_HORA_INATIVAÇÃO_DA_CHAVE")
    private LocalDateTime inactiveKeyDateTime;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        PixAccountUserDetails that = (PixAccountUserDetails) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
