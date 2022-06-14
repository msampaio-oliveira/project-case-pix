package br.com.project.pix.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class PixLimitMaxKeyValueDTO {

    private Integer countKeyValue;

    private String personType;

}
