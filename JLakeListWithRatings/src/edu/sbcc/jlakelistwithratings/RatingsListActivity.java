package edu.sbcc.jlakelistwithratings;

import android.app.*;
import android.content.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import android.widget.AdapterView.OnItemClickListener;

public class RatingsListActivity extends Activity implements OnItemClickListener {
	private ListView theList;
	private ArrayAdapter<String> cityAdapter;
	private String[] nebraskaCity;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ratings_list);
		nebraskaCity = getResources().getStringArray(R.array.nebraskaCity);
		cityAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, nebraskaCity);
		theList = (ListView) findViewById(R.id.listView1);
		theList.setAdapter(cityAdapter);
		theList.setOnItemClickListener(this);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		// super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			float rating = data.getExtras().getFloat("rating");
			String comment = data.getExtras().getString("comment");
			int position = data.getExtras().getInt("position");
			nebraskaCity[position] = nebraskaCity[position] + " - " + rating + "stars. " + comment;
			cityAdapter.notifyDataSetChanged();
		}
	}

	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Intent intent = new Intent(this, CommentFormActivity.class);
		intent.putExtra("position", position);
		intent.putExtra("item", cityAdapter.getItem(position));
		startActivityForResult(intent, 0);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_ratings_list, menu);
		return true;
	}
}
