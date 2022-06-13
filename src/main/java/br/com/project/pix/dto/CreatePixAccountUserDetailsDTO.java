package br.com.project.pix.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@EqualsAndHashCode
@Data
public class CreatePixAccountUserDetailsDTO {

    @NotNull(message = "keyType cannot be null")
    private String keyType;

    @NotNull(message = "keyType cannot be null")
    private String keyValue;

    @NotNull(message = "accountType cannot be null")
    private String accountType;

    @NotNull(message = "agencyNumber cannot be null")
    @Size(max = 4, message = "size should not exceed 4")
    private String agencyNumber;

    @NotNull(message = "accountNumber cannot be null")
    private String accountNumber;

    @NotNull(message = "accountHolderName cannot be null")
    private String accountHolderName;

    @Size(max = 45, message = "size should not exceed 45")
    private String accountHolderLastLame;

    @NotNull(message = "personType cannot be null")
    private Character personType;

}
