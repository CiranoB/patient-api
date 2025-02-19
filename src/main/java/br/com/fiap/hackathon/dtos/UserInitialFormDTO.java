package br.com.fiap.hackathon.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInitialFormDTO {
    @Schema(example = """
            Sintomas Atuais:
            [ ] Febre
            [ ] Tosse
            [ ] Dificuldade para respirar
            [ ] Dor de garganta
            [ ] Dor no corpo
            [ ] Diarreia
            [ ] Outros (especifique):
            Há quanto tempo apresenta os sintomas?
            [ ] Menos de 24 horas
            [ ] 1-3 dias
            [ ] 4-7 dias
            [ ] Mais de 7 dias

            Histórico de Viagens Recentes:
            [ ] Sim (especifique destino e data):
            [ ] Não

            Contato com Casos Confirmados de Doenças Infecciosas:
            [ ] Sim
            [ ] Não
            [ ] Não sei

            Condições de Saúde Preexistentes:
            [ ] Diabetes
            [ ] Hipertensão
            [ ] Doenças cardíacas
            [ ] Asma/Bronquite
            [ ] Outros (especifique)

            Uso de Medicamentos Contínuos:
            [ ] Sim (especifique):
            [ ] Não

            Vacinação Recente:
            [ ] Sim (especifique vacina e data)
            [ ] Não

            Observações Adicionais:
                            """)
    private String userFormInput;

}
