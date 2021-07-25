package com.project.infosdepartment.model.repositories;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.project.infosdepartment.model.database.entity.DepartmentEntity;
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
        departmentRepository.resetCache();
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
        String codeYvelines = "78";
        DepartmentEntity departmentEntity;
        try {
            departmentEntity = departmentRepository.getDepartmentEntity(codeYvelines);
            Assert.assertNull(departmentEntity);
            Thread.sleep(1000);
        } catch (RuntimeException | InterruptedException e) {
            Assert.fail();
        }
        Assert.assertEquals(1, departmentRepository.isDataFetched(codeYvelines));

        String invalidCode = "456";
        try {
            departmentEntity = departmentRepository.getDepartmentEntity(invalidCode);
            Assert.assertNull(departmentEntity);

            departmentEntity = departmentRepository.getDepartmentEntity(codeYvelines);
            Assert.assertNotNull(departmentEntity);
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
            DepartmentEntity departmentEntity = departmentRepository.getDepartmentEntity(code);
            Assert.assertNull(departmentEntity);
        } catch (RuntimeException ignored) {
        }
    }

    @Test
    public void testResetCache() {
        Integer zero = 0;
        List<String> departmentCodes = new ArrayList<>(Arrays.asList("08", "09", "10", "11", "12", "13", "14", "15"));
        for (String code : departmentCodes) {
            departmentRepository.getDepartmentEntity(code);
        }
        departmentRepository.resetCache();
        for (int i = 8; i < 15; i++) {
            String code = Integer.toString(i);
            try {
                DepartmentEntity departmentEntity = departmentRepository.getDepartmentEntityFromCache(code);
                Assert.assertNull(departmentEntity);
                DepartmentsListEntity departmentsListEntity = departmentRepository.getDepartment(code);
                Assert.assertEquals(zero, departmentsListEntity.getAreDataFetched());
            } catch (RuntimeException e) {
            }
        }
    }

    @Test
    public void testInsertDepartmentEntity() throws InterruptedException {
        DepartmentEntity fakeDepartmentEntity = new DepartmentEntity("375", "JeN'ExistePas", 1, 2);
        try {
            DepartmentEntity departmentEntity = departmentRepository.getDepartmentEntityFromCache("375");
            Assert.assertNull(departmentEntity);
        } catch (RuntimeException ignored) {

        }
        departmentRepository.insert(fakeDepartmentEntity);
        Thread.sleep(1000);
        Integer one = 1;
        Integer two = 2;
        DepartmentEntity departmentEntity = departmentRepository.getDepartmentEntityFromCache("375");
        Assert.assertNotNull(departmentEntity);
        Assert.assertEquals("375", departmentEntity.getDepartmentCode());
        Assert.assertEquals("JeN'ExistePas", departmentEntity.getDepartmentName());
        Assert.assertEquals(two, departmentEntity.getNbTowns());
        Assert.assertEquals(one, departmentEntity.getInhabitants());
        departmentRepository.resetCache();
    }

    @Test
    public void testSetTrueDataFetched() throws InterruptedException {
        String code = "78";
        Assert.assertEquals(0, departmentRepository.isDataFetched(code));
        departmentRepository.setTrueBoolDepartment(code);
        Thread.sleep(1000);
        Assert.assertEquals(1, departmentRepository.isDataFetched(code));

    }
}