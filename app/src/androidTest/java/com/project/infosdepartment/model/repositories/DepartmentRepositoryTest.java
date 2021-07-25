package com.project.infosdepartment.model.repositories;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class DepartmentRepositoryTest {

    DepartmentRepository departmentRepository;


    @Before
    public void setUp() {
        departmentRepository = new DepartmentRepository(ApplicationProvider.getApplicationContext());
    }

    @Test
    public void testGetters() {
        Assert.assertEquals("https://geo.api.gouv.fr", DepartmentRepository.getUrlPrefix());
        Assert.assertEquals("&format=json", DepartmentRepository.getFormat());
        Assert.assertEquals("?fields=", DepartmentRepository.getFields());
        Assert.assertEquals("/departements/", DepartmentRepository.getDepartmentEndpoint());
        Assert.assertEquals("/communes", DepartmentRepository.getTownEndpoint());
        Assert.assertEquals("https://geo.api.gouv.fr/departements/", DepartmentRepository.getUrlDepartmentEndpoint());
    }

    @Test
    public void testFetchInfo() {
        String code = "78";
        try {
            departmentRepository.getDepartmentInfo(code);
        } catch (RuntimeException e) {
            Assert.fail();
        }

    }

    @Test
    public void testGetDepartmentInfo() {
    }

    @Test
    public void testGetDepartmentsList() {
    }

    @Test
    public void testGetDepartment() {
    }

}