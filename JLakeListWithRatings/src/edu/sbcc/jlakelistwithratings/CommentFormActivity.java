package edu.sbcc.jlakelistwithratings;

import android.app.*;
import android.os.*;
import android.view.*;
import android.widget.*;

public class CommentFormActivity extends Activity {

	public void onCancelClicked(View v) {
		setResult(RESULT_CANCELED);
		finish();
	}

	public void onOkClicked(View v) {
		RatingBar ratingBar = (RatingBar) findViewById(R.id.ratingBar);
		EditText commentEdit = (EditText) findViewById(R.id.commentEdit);
		getIntent().putExtra("rating", ratingBar.getRating());
		getIntent().putExtra("comment", commentEdit.getText().toString());
		setResult(RESULT_OK, getIntent());
		finish();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.comment_form);
		String itemText = (String) getIntent().getExtras().get("item");
		setTitle("Rate " + itemText);
	}

}
