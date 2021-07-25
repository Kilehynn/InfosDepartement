package com.project.infosdepartment.model.utils;

import android.util.Log;

import com.project.infosdepartment.model.database.entity.DepartmentEntity;
import com.project.infosdepartment.model.repositories.DepartmentRepository;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class FetchInfoCallback {

    //Unused because as a callback, I can't know when it'll be execute
    public void onSuccess(JSONArray result, DepartmentRepository departmentRepository, String departmentCode) {
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
        Log.d("[DEBUG][fetchInfoCallback]", "onSuccess : Insert new DepartementEntity in Department Database\n" +
                "Department Code : " + departmentCode + "\n" +
                "Department Name : " + departmentName + "\n" +
                "Number of Towns : " + nbTowns + "\n" +
                "Number of inhabitants : " + inhabitants + "\n");
        DepartmentEntity entity = new DepartmentEntity(departmentCode, departmentName, inhabitants, nbTowns);
        departmentRepository.insert(entity);
        departmentRepository.setTrueBoolDepartment(departmentCode);

    }

}
