package br.com.fiap.hackathon.dtos;

import java.util.Map;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserFormDTO {

    @Schema(description = "Mapa de perguntas e respostas", example = "{\"pergunta 1\": true, \"pergunta 2\": false}")
    private Map<String, Boolean> perguntas;
    
}
