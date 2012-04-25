package dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import utility.httpUtility;
import utility.jsonUtility;
import entity.Intervention;

public class InterventionDao {	
	public List<Intervention> getInterventionByCampus(Long campusId){
		List<Intervention> interventionss = new ArrayList<Intervention>();
		
		String json = httpUtility.getInstance().get("/resources/intervention/bycampus/"+campusId);
		if(json == null){return null;}
				
		JSONArray jsonInterventions = jsonUtility.getInstance().getJsonArrayFromString(json,"intervention");
		if(jsonInterventions == null){
			Intervention oneIntervention = jsonUtility.getInstance().getIntervention(jsonUtility.getInstance().getOneItem(jsonUtility.getInstance().getJsonObjectFromString(json), "intervention"));
			if(oneIntervention != null){
				interventionss.add(oneIntervention);
			}else{
				return null;
			}
		}else{
			for (int i = 0; i < jsonInterventions.length(); i++) {
				JSONObject jsonCampus = null;
				try {
					jsonCampus = jsonInterventions.getJSONObject(i);
				} catch (JSONException e) {
					//Nothing;
				}
				interventionss.add(jsonUtility.getInstance().getIntervention(jsonCampus));
			}
		}
		
		Intervention thisIntervention;
		for(int i=0;i<interventionss.size();i++){
			thisIntervention = interventionss.get(i);
			
			Date today = new java.sql.Date(System.currentTimeMillis());
			if(thisIntervention.getBeginDate().after(today)){
				thisIntervention.setStatus(1); //Not started
			}else if(thisIntervention.getEndDate().before(today)){
				thisIntervention.setStatus(3); //Ended
			}else{
				thisIntervention.setStatus(2); //In progress
			}
		}
		
		return interventionss;
	}
	
	public Intervention getOneIntervention(Long interventionId){
		Intervention intervention = new Intervention();
		
		String json = httpUtility.getInstance().get("/resources/intervention/one/"+interventionId);
		
		
		JSONObject jsonIntervention = jsonUtility.getInstance().getJsonObjectFromString(json);
		Intervention oneIntervention = jsonUtility.getInstance().getIntervention(jsonIntervention);
		if(oneIntervention != null){
			intervention = oneIntervention;
		}
		

		Date today = new java.sql.Date(System.currentTimeMillis());
		if(intervention.getBeginDate().after(today)){
			intervention.setStatus(1); //Not started
		}else if(intervention.getEndDate().before(today)){
			intervention.setStatus(3); //Ended
		}else{
			intervention.setStatus(2); //In progress
		}		
		return intervention;
	}
}
