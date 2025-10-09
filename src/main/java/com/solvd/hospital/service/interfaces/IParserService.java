package com.solvd.hospital.service.interfaces;

import com.solvd.hospital.model.Disease;
import com.solvd.hospital.model.Symptom;

import java.util.List;

public interface IParserService {
    List<Disease> parseDiseases(String filePath);
    List<Symptom> parseSymptoms(String filePath);
}
