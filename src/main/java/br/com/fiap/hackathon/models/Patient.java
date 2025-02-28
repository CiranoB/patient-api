package br.com.fiap.hackathon.models;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "patients")
@NoArgsConstructor
@Data
public class Patient {
    @Id
    private String id;
    private String userName;
    private String userIdentifier;
    private List<PatientRecord> patientRecords;

}
