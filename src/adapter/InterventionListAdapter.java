package adapter;

import java.text.SimpleDateFormat;
import java.util.List;

import notetonsta.client.R;

import entity.Intervention;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class InterventionListAdapter extends BaseAdapter{
	
	private Context context;
	private List<Intervention> listIntervention;
	private LayoutInflater inflater;
	
	public InterventionListAdapter(Context contextIn , List<Intervention> listInterventionIn){
		context = contextIn;
		listIntervention = listInterventionIn;
		inflater = LayoutInflater.from(context);
	}

	public int getCount() {
		return listIntervention.size();
	}

	public Intervention getItem(int pos) {
		return listIntervention.get(pos);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		convertView = inflater.inflate(R.layout.intervention_item, null);
		holder = new ViewHolder();
		holder.subject = (TextView) convertView.findViewById(R.id.intervention_item_subject);
		holder.dateBegin = (TextView) convertView.findViewById(R.id.intervention_item_begin);
		holder.dateEnd = (TextView) convertView.findViewById(R.id.intervention_item_end);
		holder.status = (TextView) convertView.findViewById(R.id.intervention_item_status);
		convertView.setTag(holder);
		
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		
		Intervention intervention = getItem(position);
		holder.subject.setText(intervention.getSubject());
		switch(intervention.getStatus()){
			case 1 :
				holder.status.setText("Not started");
				break;
			case 2 :
				holder.status.setText("In progress");
				break;
			case 3 :
				holder.status.setText("Done");
				break;
			default :
				holder.status.setText("no status");
				break;
		
		}
		holder.dateBegin.setText(df.format(intervention.getBeginDate()));
		holder.dateEnd.setText(df.format(intervention.getEndDate()));

		if(position % 2 == 0) {
			convertView.setBackgroundResource(android.R.color.background_dark);
		} else {
			convertView.setBackgroundResource(android.R.color.white);
		}

		return convertView;
	}
	
	static class ViewHolder {
		TextView subject;
		TextView dateBegin;
		TextView dateEnd;
		TextView status;
	}
}
