package com.solvd.hospital.dao.impl.mybatis;

import com.solvd.hospital.dao.interfaces.IDoctorDAO;
import com.solvd.hospital.dao.interfaces.IPersonDAO;
import com.solvd.hospital.model.Doctor;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

public class DoctorMyBatisImpl implements IDoctorDAO {
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
    public void create(Doctor doctor) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            IPersonDAO iPersonDAO = session.getMapper(IPersonDAO.class);
            iPersonDAO.create(doctor);

            IDoctorDAO iDoctorDAO = session.getMapper(IDoctorDAO.class);
            iDoctorDAO.create(doctor);

            session.commit();
        }
    }

    @Override
    public Doctor getById(Long id) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            IDoctorDAO iDoctorDAO = session.getMapper(IDoctorDAO.class);
            return iDoctorDAO.getById(id);
        }
    }

    @Override
    public List<Doctor> getAll() {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            IDoctorDAO iDoctorDAO = session.getMapper(IDoctorDAO.class);
            return iDoctorDAO.getAll();
        }
    }

    @Override
    public void update(Doctor doctor) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            IDoctorDAO iDoctorDAO = session.getMapper(IDoctorDAO.class);
            iDoctorDAO.update(doctor);
            session.commit();
        }
    }

    @Override
    public void delete(Long id) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            IDoctorDAO iDoctorDAO = session.getMapper(IDoctorDAO.class);
            iDoctorDAO.delete(id);
            session.commit();
        }
    }
}
