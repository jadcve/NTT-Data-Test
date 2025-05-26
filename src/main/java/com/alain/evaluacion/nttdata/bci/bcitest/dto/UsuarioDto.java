package com.alain.evaluacion.nttdata.bci.bcitest.dto;

import java.time.LocalDateTime;
import java.util.List;
import com.alain.evaluacion.nttdata.bci.bcitest.entities.Audit;
import com.alain.evaluacion.nttdata.bci.bcitest.validations.ExistsByEmail;
import com.alain.evaluacion.nttdata.bci.bcitest.validations.IsRequired;
import com.alain.evaluacion.nttdata.bci.bcitest.validations.ListNotEmpty;
import com.alain.evaluacion.nttdata.bci.bcitest.validations.PasswordIsValid;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDto {  
    private Long id;

    @IsRequired
    @PasswordIsValid(message = "{Invalid.usuarioDto.email}")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @IsRequired
    private String name;
    
    @IsRequired
    //  Si se quiere dejar el formato aaaaaaa@dominio.cl  ^[a-zA-Z0-9._%+-]+@dominio\\.cl$
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "El formato del correo debe ser mail@dominio.cl")
    //@ExistsByEmail(message = "El correo ya registrado")
    private String email;
    private Audit audit;
    private LocalDateTime lastLogin;

    @ListNotEmpty(message = "debe contener al menos un teléfono")
    private List<TelefonoDto> phones;
    private boolean isActive;

}
