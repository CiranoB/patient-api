package br.com.fiap.hackathon.controllers;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.hackathon.dtos.UserFormDTO;
import br.com.fiap.hackathon.dtos.UserInitialFormDTO;
import br.com.fiap.hackathon.models.Patient;
import br.com.fiap.hackathon.models.PatientRecord;
import br.com.fiap.hackathon.services.GPTService;
import br.com.fiap.hackathon.services.PatientService;

@RestController
@RequestMapping("/patients")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @Autowired
    private GPTService gptService;

    @PostMapping("/{userName}/add_record")
    public Patient addRecordToPatient(
        @PathVariable String userName,
        @RequestBody PatientRecord newRecord
        ) {
        return this.patientService.addRecortToPatient(userName, newRecord);
    }


    @PostMapping("/{userName}/check_urgent_care_need")
    public String checkUrgentCareNeed(
        @PathVariable String userName,
        @RequestBody UserInitialFormDTO message
        ) {
            return this.gptService.runGemini(
            "Com base no seguinte formulário, defina se o paciente precisa de atendimento imediato, e qual especialidade seria adequada para tal atendimento" +
            message.getUserFormInput());
    }

    @GetMapping("/{userName}/get_records_summary")
    public Object getRecordsSummary(
        @PathVariable String userName
    ){
        Optional<Patient> user = this.patientService.getPatientByUserName(userName);
        if(user.isEmpty()){
            return "Patient not found";
        } else {
            // return formatPatientRecords(user.get().getPatientRecords());
            return this.gptService.runGemini(
            "Com base nos dados do seguinte usuário, faça um breve relatório que será lido por um médico posteriormente acerca do usuario: " +
            formatPatientRecords(user.get().getPatientRecords()));
        }

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
