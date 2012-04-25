package notetonsta.client;

import java.util.ArrayList;
import java.util.List;

import dao.CampusDao;

import entity.Campus;

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
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class CampusListActivity extends Activity {
    /** Called when the activity is first created. */
	private ProgressDialog dialog;
	private List<Campus> campuss = new ArrayList<Campus>();
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        /*MenuItem refButton = (MenuItem) findViewById(R.id.refresh_button);
        MenuItem helpButton = (MenuItem) findViewById(R.id.help);
        refButton.setIcon(android.R.drawable.ic_menu_rotate);
        helpButton.setIcon(android.R.drawable.ic_menu_help);*/
        
        dialog = new ProgressDialog(this);
        setTitle("Campus list");
        
        refresh();
        
        final ListView myListView = (ListView) findViewById(R.id.myCampusList);
        myListView.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int pos,
					long arg3) {
				// TODO Auto-generated method stub
				Object thisListItem = myListView.getItemAtPosition(pos);
				if(thisListItem.getClass() == Campus.class){
					Campus thisCampus = (Campus) thisListItem;
					goPageListIntervention(thisCampus.getId(), thisCampus.getName());
				}else{
					dialog.setMessage("Erreur : objet de liste non Campus");
		    		dialog.show();
				}
			}
		});
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
    
    private void refresh(){
    	new AsyncTask<Void, Void, String>() {
            
    		@Override
    		protected String doInBackground(Void... params) {
    			CampusDao campusDao = new CampusDao();
    			campuss = campusDao.getAllCampus();
    			if(campuss == null){
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
    				dialog.setMessage("Echec de la connexion");
    			}else{
    				fillList();
    	    		dialog.dismiss();
    			}
    		}
    		        
    		}.execute();
    }
    
    private void fillList(){
    	ListView myListView = (ListView) findViewById(R.id.myCampusList);
		myListView.setAdapter(new ArrayAdapter<Campus>(this, android.R.layout.simple_list_item_1, campuss));
    }
    
    private void goPageListIntervention(long campusId,String campusName){
    	Intent listInterventionIntent = new Intent(this,InterventionListActivity.class);
    	listInterventionIntent.putExtra("campusId", campusId);
    	listInterventionIntent.putExtra("campusName", campusName);
    	startActivity(listInterventionIntent);
    }
}