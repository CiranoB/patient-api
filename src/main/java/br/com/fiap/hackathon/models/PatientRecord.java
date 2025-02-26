package br.com.fiap.hackathon.models;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class PatientRecord {
    private java.util.Date date;
    private Map<String, Object> content;
}
