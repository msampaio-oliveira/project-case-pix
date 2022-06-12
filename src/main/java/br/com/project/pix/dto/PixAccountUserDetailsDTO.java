package br.com.project.pix.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@EqualsAndHashCode
@Data
public class PixAccountUserDetailsDTO {

    @NotNull(message = "keyType cannot be null")
    @Size(max = 9, message = "size should not exceed 9")
    private String keyType;

    @NotNull(message = "keyType cannot be null")
    private String keyValue;

    @NotNull(message = "accountType cannot be null")
    @Size(max = 10, message = "size should not exceed 10")
    private String accountType;

    @NotNull(message = "agencyNumber cannot be null")
    @Size(max = 4, message = "size should not exceed 4")
    private String agencyNumber;

    @NotNull(message = "accountNumber cannot be null")
    @Size(max = 8, message = "size should not exceed 8")
    private String accountNumber;

    @NotNull(message = "accountHolderName cannot be null")
    @Size(max = 30, message = "size should not exceed 30")
    private String accountHolderName;

    @Size(max = 45, message = "size should not exceed 45")
    private String accountHolderLastLame;

    @NotNull(message = "personType cannot be null")
    @Size(max = 1, message = "size should not exceed 1")
    private Character personType;

}
