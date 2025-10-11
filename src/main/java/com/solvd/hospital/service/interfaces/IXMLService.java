package com.solvd.hospital.service.interfaces;

import com.solvd.hospital.model.MedicalRecord;

public interface IXMLService {
    MedicalRecord parseMedicalRecord(String xmlPath, String xsdPath);
}
