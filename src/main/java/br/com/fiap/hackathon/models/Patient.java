package br.com.fiap.hackathon.models;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document(collection = "patients")
@NoArgsConstructor
@Getter
@Setter
public class Patient {
    @Id
    private String id;
    private String userName;
    private List<PatientRecord> patientRecords;

}
