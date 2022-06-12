package br.com.project.pix.dto.projection;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public interface PixAccountUserDetailsProjection {

    String getId();

    String getKeyType();

    String getKeyValue();

    String getAccountType();

    Integer getAgencyNumber();

    Integer getAccountNumber();

    String getAccountHolderName();

    String getAccountHolderLastLame();

    Character getPersonType();

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime getInclusionKeyDateTime();

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime getInactiveKeyDateTime();
}
