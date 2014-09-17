package com.phunware.localpoint.sample;

import java.util.List;

import android.app.ListActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.digby.localpoint.sdk.core.ILPFilter;
import com.digby.localpoint.sdk.core.ILPLocation;
import com.digby.localpoint.sdk.core.impl.LPLocalpointService;

public class LPLocationActivity extends ListActivity {
	EditText searchView;
	List<ILPLocation> locations;
	String nameSearchType = "name";
	String distanceSearchType = "distance";
	String inLocationSearchType = "deviceIn";
	String searchType = nameSearchType;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(com.phunware.localpoint.sample.R.layout.activity_lplocation);
		this.searchLocations(null);

		// Define the search view
		searchView = (EditText) findViewById(com.phunware.localpoint.sample.R.id.searchKeyword);
		searchView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                searchLocations(v.getText().toString());
                return false;
            }
        });

	}

	/**
	 * Search locations according to search type
	 */
	public void searchLocations(String keyword) {
		// Instance Localpoint Service
		LPLocalpointService lpService = LPLocalpointService
				.getInstance(getApplicationContext());
		// Set the default filter
		ILPFilter filter = lpService.getLocationProvider().getFilterFactory()
				.getAllowsCheckInFilter();
		if (searchType.equals(distanceSearchType)) {
			// Search by distance
			try {
				int distance = Integer.parseInt(keyword);
				filter = lpService.getLocationProvider().getFilterFactory()
						.getWithinDistanceFilter(distance);
			} catch (Exception e) {
			}
		} else if (searchType.equals(nameSearchType)) {
			// Search by name
			if (keyword != null && !keyword.isEmpty())
				filter = lpService.getLocationProvider().getFilterFactory()
						.getHasTagFilter(keyword);
		} else if (searchType.equals(inLocationSearchType)) {
			// Get locations in which device is in
			filter = lpService.getLocationProvider().getFilterFactory()
					.getDeviceIsInFilter();
		}
		// Get filtered locations
		locations = lpService.getLocationProvider().getLocations(
				filter,
				lpService.getLocationProvider().getOrderingFactory()
						.getAscendingDistanceOrdering());
		// Prepare for ArrayAdapter
		String[] values = new String[locations.size()];
		for (int i = 0; i < values.length; i++) {
			values[i] = locations.get(i).getName();
		}
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, values);
		// Set adapter
		setListAdapter(adapter);
	}

	/**
	 * Handle the events when use checks the search type
	 * 
	 * @param view
	 */
	public void onRadioButtonClicked(View view) {
		// Is the button now checked?
		boolean checked = ((RadioButton) view).isChecked();

		// Check which radio button was clicked
		switch (view.getId()) {
		// Click search by name
		case com.phunware.localpoint.sample.R.id.radioSearchByName:
			if (checked) {
				searchType = nameSearchType;
				searchView.setInputType(InputType.TYPE_CLASS_TEXT);
			}
			break;
		// Click search by distance
		case com.phunware.localpoint.sample.R.id.radioSearchByDistance:
			if (checked) {
				searchType = distanceSearchType;
				searchView.setInputType(InputType.TYPE_CLASS_PHONE);
			}
			break;
		// Click 'Within'
		case com.phunware.localpoint.sample.R.id.radioImIn:
			if (checked) {
				searchType = inLocationSearchType;
				searchView.clearFocus();
				
			}
			break;
		}
		searchLocations(searchView.getText().toString());
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// Check in the selected store
		ILPLocation location = locations.get(position);
		location.checkIn();
	}


}