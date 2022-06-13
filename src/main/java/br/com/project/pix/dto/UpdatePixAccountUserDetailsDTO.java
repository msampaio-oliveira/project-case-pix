package br.com.project.pix.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

@EqualsAndHashCode
@Data
public class UpdatePixAccountUserDetailsDTO {

    @NotNull(message = "accountType cannot be null")
    private String accountType;

    @NotNull(message = "agencyNumber cannot be null")
    private String agencyNumber;

    @NotNull(message = "accountNumber cannot be null")
    private String accountNumber;

    @NotNull(message = "accountHolderName cannot be null")
    private String accountHolderName;

    private String accountHolderLastLame;

    @NotNull(message = "personType cannot be null")
    private Character personType;
}
