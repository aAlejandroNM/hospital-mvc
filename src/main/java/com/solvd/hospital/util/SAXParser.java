package com.solvd.hospital.util;

import org.xml.sax.helpers.DefaultHandler;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;

public class SAXParser {

    public void parse(String filePath, DefaultHandler handler) {
        try {
            File inputFile = new File(filePath);
            javax.xml.parsers.SAXParserFactory factory = SAXParserFactory.newInstance();
            javax.xml.parsers.SAXParser saxParser = factory.newSAXParser();
            saxParser.parse(inputFile, handler);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
