package notetonsta.client;

import java.text.SimpleDateFormat;
import java.util.List;

import dao.InterventionDao;
import dao.MarkDao;
import entity.Intervention;
import entity.Mark;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

public class InterventionActivity extends Activity{
	private Long interventionId;
	private Intervention intervention;
	private List<Mark> marks;
	private ProgressDialog dialog;
	private String campusName;
	
	@Override
	 public void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
	     setContentView(R.layout.intervention);
	     dialog = new ProgressDialog(this);
	     campusName = getIntent().getExtras().getString("campusName");
	     //campusId = getIntent().getExtras().getLong("campusId");
	     interventionId = getIntent().getExtras().getLong("interventionId");
	     
	     setTitle("Intervention page");
	     
	     refresh();
	 }
	
	private void refresh(){
    	new AsyncTask<Void, Void, String>() {
            
    		@Override
    		protected String doInBackground(Void... params) {
    			InterventionDao interventionDao = new InterventionDao();
    			MarkDao markDao = new MarkDao();
    			intervention = interventionDao.getOneIntervention(interventionId);
    			marks = markDao.getMarkByIntervention(interventionId);
				return "ok";
    		}

    		@Override
    		protected void onPreExecute() {
    		dialog.setMessage("Chargement");
    		dialog.show();
    		}

    		@Override
    		protected void onPostExecute(String result) {
    		fillPage();
    		dialog.dismiss();
    		}
    		        
    		}.execute();
    }
	
	private void fillPage(){
		//Recuperation des éléments de la page
		TextView subjectText = (TextView) findViewById(R.id.intervention_subject);
		TextView campusText = (TextView) findViewById(R.id.intervention_campus);
		TextView periodText = (TextView) findViewById(R.id.intervention_period);
		TextView descriptionText = (TextView) findViewById(R.id.intervention_description);
		TextView markNumberText = (TextView) findViewById(R.id.intervention_mark_number);
		TextView markSpeakerText = (TextView) findViewById(R.id.intervention_mark_speaker);
		TextView markSlideText = (TextView) findViewById(R.id.intervention_mark_slide);
		TextView markAverageText = (TextView) findViewById(R.id.intervention_mark_average);
		
		subjectText.setText(intervention.getSubject());
		campusText.setText(campusName);
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		periodText.setText("from "+df.format(intervention.getBeginDate())+" to "+df.format(intervention.getEndDate()));
		descriptionText.setText(intervention.getDescription());
		
		//Calcul note
		
		Integer markNumber=marks.size();
		Integer oneMarkSlide;
		Integer allMarkSlide = 0;
		Integer oneMarkSpeaker;
		Integer allMarkSpeaker = 0;
		Mark thisMark = null;
		for(int i=0;i<markNumber;i++){
			thisMark = marks.get(i);
			//Slide Mark /15
			oneMarkSlide = thisMark.getSpeakerAnswers()+thisMark.getSpeakerKnowledge()+thisMark.getSpeakerTeaching();
			//Speaker Mark /15
			oneMarkSpeaker = thisMark.getSlideContent()+thisMark.getSlideExample()+thisMark.getSlideFormat();
			
			allMarkSlide = allMarkSlide + oneMarkSlide;
			allMarkSpeaker = allMarkSpeaker + oneMarkSpeaker;
		}
		Float slideAverage = Float.valueOf(allMarkSlide) / Float.valueOf(markNumber*3);
		Float speakerAverage= Float.valueOf(allMarkSpeaker) / Float.valueOf(markNumber*3);
		Float generalAverage = Float.valueOf(allMarkSpeaker+allMarkSlide) / Float.valueOf(markNumber*6);
		
		markNumberText.setText(String.valueOf(markNumber));
		markSpeakerText.setText(String.valueOf(speakerAverage));
		markSlideText.setText(String.valueOf(slideAverage));
		markAverageText.setText(String.valueOf(generalAverage));
		
	}
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.layout.menu_rating, menu);
        return true;
    }
  
  @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.evaluate_button:
                goPageRating();
                return true;
            case R.id.refresh_button:
                refresh();
                return true;
            case R.id.about_button:
                Intent goAboutPage = new Intent(this,AboutPageActivity.class);
                startActivity(goAboutPage);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
  
  	private void goPageRating(){
	  Intent ratingIntent = new Intent(this,RatingActivity.class);
	  ratingIntent.putExtra("interventionId",intervention.getId());
	  ratingIntent.putExtra("interventionName",intervention.getSubject());
	  startActivityForResult(ratingIntent,42);
  	}
  	@Override
  	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
  		if(requestCode == 42){
  			refresh();
  		}
  		super.onActivityResult(requestCode, resultCode, data);
  	}
}
