package notetonsta.client;

import dao.MarkDao;
import entity.Mark;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

public class RatingActivity extends Activity{
	
	private Long interventionId;
	private String interventionName;
	private ProgressDialog dialog;
	
	@Override
	 public void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
	     setContentView(R.layout.rating);
	     dialog = new ProgressDialog(this);
	     interventionId = getIntent().getExtras().getLong("interventionId");
	     interventionName = getIntent().getExtras().getString("interventionName");	     
	     
	     setTitle("Rate "+interventionName);
	     
	     Button button = (Button) findViewById(R.id.rating_button);
	     button.setOnTouchListener(new OnTouchListener() {
			
			public boolean onTouch(View v, MotionEvent event) {
				validate();
				return false;
			}
		});
	 }
	
	public void validate(){
		final MarkDao markDao = new MarkDao();
		final Mark mark = new Mark();
		
		//Recuperation des elements
		EditText idBoosterEdit = (EditText) findViewById(R.id.rating_id_booster);
		EditText commentEdit = (EditText) findViewById(R.id.rating_comment);
		
		RatingBar slideContentBar = (RatingBar) findViewById(R.id.rating_slide_content);
		RatingBar slideExampleBar = (RatingBar) findViewById(R.id.rating_slide_example);
		RatingBar slideFormatBar = (RatingBar) findViewById(R.id.rating_slide_format);
		
		RatingBar speakerAnswersBar = (RatingBar) findViewById(R.id.rating_speaker_answers);
		RatingBar speakerKnowledgeBar = (RatingBar) findViewById(R.id.rating_speaker_knowledge);
		RatingBar speakerTeachingBar = (RatingBar) findViewById(R.id.rating_speaker_teaching);
		
		//Creation de l'objet mark
		mark.setInterventionId(interventionId);
		mark.setIdBooster(Integer.valueOf(idBoosterEdit.getText().toString()));
		mark.setComment(commentEdit.getText().toString());
		
		mark.setSlideContent(slideContentBar.getProgress());
		mark.setSlideExample(slideExampleBar.getProgress());
		mark.setSlideFormat(slideFormatBar.getProgress());
		
		mark.setSpeakerAnswers(speakerAnswersBar.getProgress());
		mark.setSpeakerKnowledge(speakerKnowledgeBar.getProgress());
		mark.setSpeakerTeaching(speakerTeachingBar.getProgress());
		
		new AsyncTask<Void, Void, String>() {
            
    		@Override
    		protected String doInBackground(Void... params) {
    			if(!(markDao.putMark(mark))){
    				cancel(true);
    			}
				return "ok";
    		}

    		@Override
    		protected void onPreExecute() {
    		dialog.setMessage("Envoi");
    		dialog.show();
    		}

    		@Override
    		protected void onPostExecute(String result) {
    			if(isCancelled()){
    				dialog.dismiss();
    				Toast.makeText(RatingActivity.this, "Could not send the mark.", 3000);
    			}else{
    				Toast.makeText(RatingActivity.this, "Mark sent.", 3000);
    				dialog.dismiss();
    				RatingActivity.this.finish();
    			}
    		}
    		        
    		}.execute();
	}
}
