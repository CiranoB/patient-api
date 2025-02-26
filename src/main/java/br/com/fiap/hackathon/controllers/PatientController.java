package br.com.fiap.hackathon.controllers;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.hackathon.dtos.UserInitialFormDTO;
import br.com.fiap.hackathon.models.Patient;
import br.com.fiap.hackathon.models.PatientRecord;
import br.com.fiap.hackathon.services.GPTService;
import br.com.fiap.hackathon.services.PatientService;

@RestController
@RequestMapping("/patients")
@Tag(name = "Patients", description = "Endpoints para gerenciamento de pacientes")
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;

    private final GPTService gptService;

    @PostMapping("/{userName}/add_record")
    @Operation(summary = "Adicionar registro médico ao paciente",
            description = "Adiciona um novo registro ao paciente identificado pelo userName. Se o paciente não existir, um novo será criado.")
    public ResponseEntity<Patient> addRecordToPatient(
        @PathVariable String userName,
        @RequestBody PatientRecord newRecord
        ) {
        return ResponseEntity.ok(this.patientService.addRecortToPatient(userName, newRecord));
    }


    @PostMapping("/{userName}/check_urgent_care_need")
    @Operation(summary = "Verifica a necessidade de atendimento urgente",
            description = "Com base no formulário inicial preenchido pelo paciente, este endpoint analisa a necessidade de atendimento imediato e recomenda uma especialidade médica apropriada."
    )
    public ResponseEntity<String> checkUrgentCareNeed(
        @PathVariable String userName,
        @RequestBody UserInitialFormDTO message
        ) {
            return ResponseEntity.ok(this.gptService.runGemini(
                    "Com base no seguinte formulário, defina se o paciente precisa de atendimento imediato, e qual especialidade seria adequada para tal atendimento" +
                            message.getUserFormInput()));
    }

    @GetMapping("/{userName}/get_records_summary")
    @Operation(summary = "Obter resumo dos registros médicos",
            description = "Busca os registros médicos de um paciente e gera um resumo que será lido por um médico posteriormente."
    )
    public ResponseEntity<String> getRecordsSummary(
        @PathVariable String userName
    ){
        Optional<Patient> user = this.patientService.getPatientByUserName(userName);
        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Patient not found");
        }

        String summary = this.gptService.runGemini(
                "Com base nos dados do seguinte usuário, faça um breve relatório que será lido por um médico posteriormente acerca do usuário: " +
                        formatPatientRecords(user.get().getPatientRecords())
        );

        return ResponseEntity.ok(summary);

    }

    public static String formatPatientRecords(List<PatientRecord> records) {
        StringBuilder sb = new StringBuilder();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        for (PatientRecord record : records) {
            sb.append("Data: ").append(formatter.format(record.getDate())).append("\n");
            sb.append("Queixa: ").append(record.getContent().get("queixas")).append("\n\n");
        }

        return sb.toString().trim();
    }
}
