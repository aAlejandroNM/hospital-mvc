package com.solvd.hospital.util;

import com.solvd.hospital.model.MedicalRecord;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.File;

public class JAXBParser {

    public MedicalRecord parseMedicalRecord(String xmlPath, String xsdPath) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(MedicalRecord.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = schemaFactory.newSchema(new File(xsdPath));
            unmarshaller.setSchema(schema);

            return (MedicalRecord) unmarshaller.unmarshal(new File(xmlPath));
        } catch (JAXBException | SAXException e) {
            e.printStackTrace();
            return null;
        }
    }
}
