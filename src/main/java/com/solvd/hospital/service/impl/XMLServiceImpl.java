package com.solvd.hospital.service.impl;

import com.solvd.hospital.model.MedicalRecord;
import com.solvd.hospital.service.interfaces.IXMLService;
import com.solvd.hospital.util.JAXBParser;

public class XMLServiceImpl implements IXMLService {

    private final JAXBParser jaxbParser;

    public XMLServiceImpl() {
        this.jaxbParser = new JAXBParser();
    }

    @Override
    public MedicalRecord parseMedicalRecord(String xmlPath, String xsdPath) {
        return jaxbParser.parseMedicalRecord(xmlPath, xsdPath);
    }
}
