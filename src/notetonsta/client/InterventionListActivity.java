package notetonsta.client;

import java.util.ArrayList;
import java.util.List;

import dao.InterventionDao;

import entity.Intervention;
import adapter.InterventionListAdapter;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class InterventionListActivity extends Activity{
	
	private ProgressDialog dialog;
	private List<Intervention> interventions = new ArrayList<Intervention>();
	private String campusName;
	private Long campusId;
	
	 @Override
	 public void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
	     setContentView(R.layout.listintervention);
	     dialog = new ProgressDialog(this);
	     
	     campusName = getIntent().getExtras().getString("campusName");
	     campusId = getIntent().getExtras().getLong("campusId");
	     setTitle("Interventions in "+campusName);
	     refresh();
	     
	     final ListView myListView = (ListView) findViewById(R.id.myInterventionList);
	        myListView.setOnItemClickListener(new OnItemClickListener() {

				public void onItemClick(AdapterView<?> arg0, View arg1, int pos,
						long arg3) {
					// TODO Auto-generated method stub
					Object thisListItem = myListView.getItemAtPosition(pos);
					if(thisListItem.getClass() == Intervention.class){
						Intervention thisIntervention = (Intervention) thisListItem;
						goPageIntervention(thisIntervention);
					}else{
						dialog.setMessage("Erreur : objet de liste non intervention");
			    		dialog.show();
					}
				}
			});
	 }
	 
	 private void refresh(){
	    	new AsyncTask<Void, Void, String>() {
	            
	    		@Override
	    		protected String doInBackground(Void... params) {
	    			InterventionDao interventionDao = new InterventionDao();
	    			interventions = interventionDao.getInterventionByCampus(campusId);
	    			if(interventions == null){
	    				cancel(true);
	    			}
					return "ok";
	    		}

	    		@Override
	    		protected void onPreExecute() {
	    		dialog.setMessage("Chargement");
	    		dialog.show();
	    		}

	    		@Override
	    		protected void onPostExecute(String result) {
	    			if(isCancelled()){
	    				dialog.setMessage("No intervention to display");
	    			}else{
	    				fillList();
	    				dialog.dismiss();
	    			}
	    		
	    		}
	    		        
	    		}.execute();
	    }
	 
	 private void fillList(){
	    ListView myListView = (ListView) findViewById(R.id.myInterventionList);
		myListView.setAdapter(new InterventionListAdapter(this,interventions));
	  }
	 
	 private void goPageIntervention(Intervention intervention){
		Intent interventionIntent = new Intent(this,InterventionActivity.class);
		interventionIntent.putExtra("campusId", campusId);
    	interventionIntent.putExtra("campusName", campusName);
		interventionIntent.putExtra("interventionId",intervention.getId());
	    startActivity(interventionIntent);
	 }
	 
	  @Override
	    public boolean onCreateOptionsMenu(Menu menu) {
	        MenuInflater inflater = getMenuInflater();
	        inflater.inflate(R.layout.menu_refresh, menu);
	        return true;
	    }
	  
	  @Override
	    public boolean onOptionsItemSelected(MenuItem item) {
	        // Handle item selection
	        switch (item.getItemId()) {
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
}
