package com.project.infosdepartment.utils;

import android.util.Log;

import com.project.infosdepartment.database.dao.DepartmentsDao;
import com.project.infosdepartment.database.entity.DepartmentEntity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public interface FetchInfoCallback {

    default void onSuccess(JSONArray result, DepartmentsDao departmentsDao, String departmentName, String departmentCode) {

        int nbTowns = result.length();
        int inhabitants = 0;
        for (int i = 0; i < nbTowns; i++) {
            try {
                JSONObject currentObject = result.getJSONObject(i);
                inhabitants += currentObject.getInt("population");
            } catch (JSONException e) {

                Log.e("[ERROR][FetchInfoCallback]", "onSuccess: An error occurred when converting an element of a JsonArray in JsonObject.");
            }

        }
        DepartmentEntity entity = new DepartmentEntity(departmentCode, departmentName, inhabitants, nbTowns);
        departmentsDao.insert(entity);

    }

}
