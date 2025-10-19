package com.solvd.hospital.service.interfaces;

import com.solvd.hospital.model.MedicalDocument;

import java.util.List;

public interface IJsonService {

    List<MedicalDocument> readMedicalDocuments(String filePath);

    void writeMedicalDocuments(String filePath, List<MedicalDocument> documents);
}
