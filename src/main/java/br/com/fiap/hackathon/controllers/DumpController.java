package br.com.fiap.hackathon.controllers;

import java.util.List;
import java.util.Set;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.hackathon.dtos.UserInitialFormDTO;
import br.com.fiap.hackathon.models.Patient;
import br.com.fiap.hackathon.services.DumpService;
import br.com.fiap.hackathon.services.GPTService;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/test")
@AllArgsConstructor
public class DumpController {

    private final GPTService gptService;
    private final DumpService dumpService;

    
    @GetMapping("/company")
    @Deprecated
    public String test(String systemContent, String userContent){
        return this.gptService.run(systemContent, userContent);
    }

    @PostMapping("/test_gemini")
    @Deprecated
    public String gptTest(@RequestBody UserInitialFormDTO message){
        return this.gptService.runGemini(
            "Com base no seguinte formulário, defina se o paciente precisa de atendimento imediato, e qual especialidade seria adequada para tal atendimento" +
            message.getUserFormInput());
    }

    
    @GetMapping("/collections")
    @Deprecated
    public Set<String> getCollections() {
        return dumpService.getAllCollections();
    }

    @GetMapping("/patients")
    @Deprecated
    public List<Patient> getAllPatients() {
        return dumpService.getAllPatients();
    }
        

}
