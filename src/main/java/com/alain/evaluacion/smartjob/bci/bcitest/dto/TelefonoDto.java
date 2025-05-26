package com.alain.evaluacion.smartjob.bci.bcitest.dto;

import com.alain.evaluacion.smartjob.bci.bcitest.validations.IsRequired;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TelefonoDto {
    @Size(min=5, max=20)
    @IsRequired
    private String number;
    @IsRequired
    private String citycode;
    @IsRequired
    private String contrycode;

   
}