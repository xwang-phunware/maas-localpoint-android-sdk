package com.example.android.actionbarcompat;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.digby.localpoint.sdk.core.ILPFilter;
import com.digby.localpoint.sdk.core.ILPMessage;
import com.digby.localpoint.sdk.core.impl.LPLocalpointService;

public class LPMessageActivity extends Activity {
	List<ILPMessage> validOffers;
	List<ILPMessage> expiredOffers;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lpmessage);
	}

	@Override
	protected void onResume() {
		super.onResume();

		LPLocalpointService service = LPLocalpointService.getInstance(getApplicationContext());
		// Fetch all valid offers
		ILPFilter validOffersFilter = service.getMessageProvider().getFilterFactory().getValidFilter();
		validOffers = service.getMessageProvider().getMessages(validOffersFilter, null);
		processValidOffers();

		// Fetch all expired offers
		ILPFilter expiredOffersFilter = service.getMessageProvider().getFilterFactory().getExpiredFilter();
		expiredOffers = service.getMessageProvider().getMessages(expiredOffersFilter, null);
		processExpiredOffers();
	}


    /**
     * Process the valid offers
     */
    private void processValidOffers() {
        // Get the list view for the valid offers
        ListView validOffersListView = (ListView) findViewById(R.id.validOffersList);
        // Loop the offers to prepare the ArrayAdapter
        String[] values = new String[validOffers.size()];
        for (int i = 0; i < validOffers.size(); i++) {
            values[i] = validOffers.get(i).getTitle();
        }
        // Instance the ArrayAdapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, values);
        validOffersListView.setAdapter(adapter);
        // Set the click events
        validOffersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent offerDetailIntent = new Intent(view.getContext(), LPMessageDetailActivity.class);
                // Pass the message Id to the next intent view
                ILPMessage msg = (ILPMessage) validOffers.get(position);
                offerDetailIntent.putExtra("messageId", msg.getID().getValue());
                startActivity(offerDetailIntent);
            }
        });
    }

    /**
     * Process the expired offers
     */
    private void processExpiredOffers() {
        // Get the list view for the valid offers
        ListView expiredOffersListView = (ListView) findViewById(R.id.expiredOffersList);
        // Loop the offers to prepare the ArrayAdapter
        String[] values = new String[expiredOffers.size()];
        for (int i = 0; i < expiredOffers.size(); i++) {
            values[i] = expiredOffers.get(i).getTitle();
        }
        // Instance the ArrayAdapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, values);
        expiredOffersListView.setAdapter(adapter);
        // Set the click events
        expiredOffersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent offerDetailIntent = new Intent(view.getContext(), LPMessageDetailActivity.class);
                // Pass the message Id to the next intent view
                ILPMessage msg = (ILPMessage) expiredOffers.get(position);
                offerDetailIntent.putExtra("messageId", msg.getID().getValue());
                startActivity(offerDetailIntent);
            }
        });
    }
}
