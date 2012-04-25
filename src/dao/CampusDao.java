package dao;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import utility.httpUtility;
import utility.jsonUtility;

import entity.Campus;

public class CampusDao {

	
	public List<Campus> getAllCampus(){
		List<Campus> campuss = new ArrayList<Campus>();
		
		String json = httpUtility.getInstance().get("/resources/campus/all");
		if(json == null){return null;}
				
		JSONArray jsonCampuss = jsonUtility.getInstance().getJsonArrayFromString(json,"campus");
		if(jsonCampuss == null){
			Campus oneCampus = jsonUtility.getInstance().getCampus(jsonUtility.getInstance().getOneItem(jsonUtility.getInstance().getJsonObjectFromString(json), "campus"));
			if(oneCampus != null){
				campuss.add(oneCampus);
			}
		}else{
			for (int i = 0; i < jsonCampuss.length(); i++) {
				JSONObject jsonCampus = null;
				try {
					jsonCampus = jsonCampuss.getJSONObject(i);
				} catch (JSONException e) {
					//Nothing;
				}
				campuss.add(jsonUtility.getInstance().getCampus(jsonCampus));
			}
		}
		return campuss;
	}
	
}
