package com.project.infosdepartment.model.repositories;

import android.app.Application;

import junit.framework.TestCase;

import org.junit.Assert;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DepartmentRepositoryTest extends TestCase {


    @Mock
    Application mockApplication;

    DepartmentRepository departmentRepository;


    @Before
    public void setUp() {
        departmentRepository = new DepartmentRepository(mockApplication);
    }

    public void testGetters() {
        assertEquals("https://geo.api.gouv.fr", DepartmentRepository.getUrlPrefix());
        assertEquals("&format=json", DepartmentRepository.getFormat());
        assertEquals("?fields=", DepartmentRepository.getFields());
        assertEquals("/departements/", DepartmentRepository.getDepartmentEndpoint());
        assertEquals("/communes", DepartmentRepository.getTownEndpoint());
        assertEquals("https://geo.api.gouv.fr/departements/", DepartmentRepository.getUrlDepartmentEndpoint());
    }

    public void testFetchInfo() {
        String code = "78";
        try {
            departmentRepository.getDepartmentInfo(code);
        } catch (RuntimeException e) {
            Assert.fail();
        }

    }

    public void testGetDepartmentInfo() {
    }

    public void testGetDepartmentsList() {
    }

    public void testGetDepartment() {
    }
}