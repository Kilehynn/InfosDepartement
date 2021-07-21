package com.project.infosdepartment.model.utils;

import android.util.Log;

import com.project.infosdepartment.model.database.dao.DepartmentsDao;
import com.project.infosdepartment.model.database.entity.DepartmentEntity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class FetchInfoCallback {

    public void onSuccess(JSONArray result, DepartmentsDao departmentsDao, String departmentCode) {
        String departmentName = "";
        int nbTowns = result.length();
        int inhabitants = 0;
        for (int i = 0; i < nbTowns; i++) {
            try {
                JSONObject currentObject = result.getJSONObject(i);
                if (i == 0) {
                    JSONObject departmentInfo = currentObject.getJSONObject("departement");
                    departmentName = departmentInfo.getString("nom");
                }
                inhabitants += currentObject.getInt("population");
            } catch (JSONException e) {

                Log.e("[ERROR][FetchInfoCallback]", "onSuccess: An error occurred when converting an element from a JsonArray to a JsonObject.");
            }

        }
        DepartmentEntity entity = new DepartmentEntity(departmentCode, departmentName, inhabitants, nbTowns);
        departmentsDao.insert(entity);

    }

}
