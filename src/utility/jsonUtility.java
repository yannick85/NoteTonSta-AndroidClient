package utility;


import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import entity.Campus;
import entity.Intervention;
import entity.Mark;

public class jsonUtility {
	static jsonUtility instance = null;
	
	public static jsonUtility getInstance(){
		if(instance == null){
			instance = new jsonUtility();
		}
		return instance;
	}
	public Campus getCampus(JSONObject jsonCampus){
		Campus campus = new Campus();
		try {
			campus.setId(Long.valueOf(jsonCampus.getString("id")));
			campus.setName(jsonCampus.getString("name"));
		} catch (NumberFormatException e) {
			return null;
		} catch (JSONException e) {
			return null;
		}
		return campus;
	}
	public Mark getMark(JSONObject jsonMark){
		Mark mark = new Mark();
		try {
			mark.setId(Long.valueOf(jsonMark.getString("id")));
			mark.setIdBooster(Integer.valueOf(jsonMark.getString("idBooster")));
			mark.setInterventionId(Long.valueOf(jsonMark.getString("interventionId")));
			mark.setComment(jsonMark.getString("comment"));
			mark.setSlideContent(Integer.valueOf(jsonMark.getString("slideContent")));
			mark.setSlideExample(Integer.valueOf(jsonMark.getString("slideExample")));
			mark.setSlideFormat(Integer.valueOf(jsonMark.getString("slideFormat")));
			mark.setSpeakerAnswers(Integer.valueOf(jsonMark.getString("speakerAnswers")));
			mark.setSpeakerKnowledge(Integer.valueOf(jsonMark.getString("speakerKnowledge")));
			mark.setSpeakerTeaching(Integer.valueOf(jsonMark.getString("speakerTeaching")));
		} catch (NumberFormatException e) {
			return null;
		} catch (JSONException e) {
			return null;
		}
		return mark;
	}
	public Intervention getIntervention(JSONObject jsonIntervention){
		Intervention intervention = new Intervention();
		if(jsonIntervention == null){return null;}
		try {
			intervention.setId(Long.valueOf(jsonIntervention.getString("id")));
			intervention.setCampus(Long.valueOf(jsonIntervention.getString("campus")));
			intervention.setDescription(jsonIntervention.getString("description"));
			intervention.setSubject(jsonIntervention.getString("subject"));
			intervention.setSpeaker(Long.valueOf(jsonIntervention.getString("speaker")));
			intervention.setStatus(Integer.valueOf(jsonIntervention.getString("status")));
			
			SimpleDateFormat myformat = new SimpleDateFormat("yyyy-MM-dd");
			try {
				intervention.setBeginDate(myformat.parse(jsonIntervention.getString("beginDate").substring(0, 10)));
				intervention.setEndDate(myformat.parse(jsonIntervention.getString("endDate").substring(0, 10)));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (NumberFormatException e) {
			return null;
		} catch (JSONException e) {
			return null;
		}
		return intervention;
	}
	public JSONObject getJsonObjectFromString(String json){
		try {
			JSONObject jsonObject = new JSONObject(json);
			return jsonObject;
		} catch (JSONException e) {
			return null;
		}
	}
	public JSONObject getOneItem(JSONObject jsonObject,String itemName){
		if(jsonObject != null){
			try {
				return jsonObject.getJSONObject(itemName);
			} catch (JSONException e) {
				return null;
			}
		}else{
			return null;
		}
	}
	public JSONArray getJsonArrayFromString(String json,String itemName){
		JSONObject jsonObject = getJsonObjectFromString(json);
		if(jsonObject != null){
			try {
				return jsonObject.getJSONArray(itemName);
			} catch (JSONException e) {
				return null;
			}
		}else{
			return null;
		}
	}
	
	public JSONObject convertMarkToJson(Mark mark){
		JSONObject jsonMark = new JSONObject();
		try {
			jsonMark.put("idBooster", String.valueOf(mark.getIdBooster()));
			jsonMark.put("interventionId", String.valueOf(mark.getInterventionId()));
			jsonMark.put("slideContent", String.valueOf(mark.getSlideContent()));
			jsonMark.put("slideExample", String.valueOf(mark.getSlideExample()));
			jsonMark.put("slideFormat", String.valueOf(mark.getSlideFormat()));
			jsonMark.put("speakerAnswers", String.valueOf(mark.getSpeakerAnswers()));
			jsonMark.put("speakerKnowledge", String.valueOf(mark.getSpeakerKnowledge()));
			jsonMark.put("speakerTeaching", String.valueOf(mark.getSpeakerTeaching()));
			jsonMark.put("comment", mark.getComment());
		} catch (JSONException e) {
			return null;
		}
		return jsonMark;
	}
}
