package com.project.infosdepartment.model.repositories;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.project.infosdepartment.model.database.entity.DepartmentsListEntity;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    public void testGetDepartmentInfo() {
        String code = "78";
        try {
            departmentRepository.getDepartmentInfo(code);
        } catch (RuntimeException e) {
            Assert.fail();
        }
        if (departmentRepository.getIfDataFetched(code) == 0) {
            Assert.fail();
        }
        code = "456";
        try {
            departmentRepository.getDepartmentInfo(code);
        } catch (RuntimeException ignored) {
        }

    }


    @Test
    public void testGetDepartmentsList() {
        List<DepartmentsListEntity> list = departmentRepository.getDepartmentsList();
        Assert.assertEquals(101, list.size());
        DepartmentsListEntity previous = list.get(0);
        for (int i = 1; i < list.size(); i++) {
            DepartmentsListEntity current = list.get(i);
            if (current.getDepartmentCode().equals("2A") || current.getDepartmentCode().equals("2B") || previous.getDepartmentCode().equals("2B")) {
                continue;
            }
            int currentCode = Integer.parseInt(current.getDepartmentCode());
            int previousCode = Integer.parseInt(previous.getDepartmentCode());
            if (currentCode < previousCode) {
                Assert.fail();
            }
            previous = current;
        }
    }

    @Test
    public void testGetDepartment() {
        departmentRepository.resetCache();
        String code = "78";
        Integer zero = 0;
        try {
            DepartmentsListEntity departmentsListEntity = departmentRepository.getDepartment(code);
            Assert.assertEquals(code, departmentsListEntity.getDepartmentCode());
            Assert.assertEquals("Yvelines", departmentsListEntity.getDepartmentName());
            Assert.assertEquals(zero, departmentsListEntity.getAreDataFetched());
        } catch (RuntimeException e) {
            Assert.fail();
        }
        code = "456";
        try {
            departmentRepository.getDepartmentInfo(code);
        } catch (RuntimeException ignored) {
        }
    }

    @Test
    public void testResetCache() {
        Integer zero = 0;
        List<String> departmentCodes = new ArrayList<>(Arrays.asList("08", "09", "10", "11", "12", "13", "14", "15"));
        for (String code : departmentCodes) {
            departmentRepository.getDepartmentInfo(code);
        }
        departmentRepository.resetCache();
        for (int i = 8; i < 15; i++) {
            String code = Integer.toString(i);
            try {
                departmentRepository.getDepartmentInfoFromCache(code);
                DepartmentsListEntity departmentsListEntity = departmentRepository.getDepartment(code);
                Assert.assertEquals(zero, departmentsListEntity.getAreDataFetched());
            } catch (RuntimeException ignored) {
            }
        }
    }
}