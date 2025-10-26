package com.solvd.hospital.dao.impl.mybatis;

import com.solvd.hospital.dao.interfaces.IPatientDAO;
import com.solvd.hospital.dao.interfaces.IPersonDAO;
import com.solvd.hospital.model.Patient;
import com.solvd.hospital.service.interfaces.IPatientService;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Reader;

public class PatientMyBatisImpl implements IPatientService {
    private final SqlSessionFactory sqlSessionFactory;

    public PatientMyBatisImpl() {
        try {
            Reader reader = Resources.getResourceAsReader("mybatis-config.xml");
            this.sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void registerNewPatient(Patient patient) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            IPersonDAO iPersonDAO = session.getMapper(IPersonDAO.class);
            iPersonDAO.create(patient);

            IPatientDAO iPatientDAO = session.getMapper(IPatientDAO.class);
            iPatientDAO.create(patient);
            
            session.commit();
        }
    }

    @Override
    public Patient findPatientById(Long id) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            IPatientDAO iPatientDAO = session.getMapper(IPatientDAO.class);
            return iPatientDAO.getById(id);
        }
    }
}
