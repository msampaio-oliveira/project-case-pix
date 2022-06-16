package br.com.project.pix.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CreatePixAccountUserDetailsDTO {

    @NotNull(message = "keyType cannot be null")
    private String keyType;

    @NotNull(message = "keyType cannot be null")
    private String keyValue;

    @NotNull(message = "accountType cannot be null")
    private String accountType;

    @NotNull(message = "agencyNumber cannot be null")
    private String agencyNumber;

    @NotNull(message = "accountNumber cannot be null")
    private String accountNumber;

    @NotNull(message = "accountHolderName cannot be null")
    private String accountHolderName;

    private String accountHolderLastName;

    @NotNull(message = "personType cannot be null")
    private Character personType;

}
