package com.solvd.hospital.dao.impl.mybatis;

import com.solvd.hospital.dao.interfaces.IDoctorDAO;
import com.solvd.hospital.model.Doctor;
import com.solvd.hospital.service.interfaces.IDoctorService;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

public class DoctorMyBatisImpl implements IDoctorService {
    private final SqlSessionFactory sqlSessionFactory;

    public DoctorMyBatisImpl() {
        try {
            Reader reader = Resources.getResourceAsReader("mybatis-config.xml");
            this.sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Doctor> getAvailableDoctors() {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            IDoctorDAO iDoctorDAO = session.getMapper(IDoctorDAO.class);
            return iDoctorDAO.getAll();
        }
    }
}
