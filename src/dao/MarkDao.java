package dao;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import utility.httpUtility;
import utility.jsonUtility;

import entity.Mark;

public class MarkDao {	
	public List<Mark> getMarkByIntervention(Long interventionId){
		List<Mark> marks = new ArrayList<Mark>();
		
		String json = httpUtility.getInstance().get("/resources/mark/byintervention/"+interventionId);
		if(json == null){return null;}
		
		JSONArray jsonMarks = jsonUtility.getInstance().getJsonArrayFromString(json,"mark");
		if(jsonMarks == null){
			JSONObject jsonMark =jsonUtility.getInstance().getOneItem(jsonUtility.getInstance().getJsonObjectFromString(json), "mark");
			Mark oneMark = null;
			if(jsonMark != null){oneMark = jsonUtility.getInstance().getMark(jsonMark);}
			if(oneMark != null){
				marks.add(oneMark);
			}
		}else{
			for (int i = 0; i < jsonMarks.length(); i++) {
				JSONObject jsonMark = null;
				try {
					jsonMark = jsonMarks.getJSONObject(i);
				} catch (JSONException e) {
					//Nothing;
				}
				marks.add(jsonUtility.getInstance().getMark(jsonMark));
			}
		}
		return marks;
	}
	
	public Boolean putMark(Mark mark){
		JSONObject jsonMark = jsonUtility.getInstance().convertMarkToJson(mark);
		return httpUtility.getInstance().post("/resources/mark/post",jsonMark.toString());
	}
	
}
