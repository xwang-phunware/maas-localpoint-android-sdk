package com.phunware.localpoint.sample;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

import com.digby.localpoint.sdk.core.ILPMessage;
import com.digby.localpoint.sdk.core.ILPMessageProvider;
import com.digby.localpoint.sdk.core.impl.LPLocalpointService;
import com.digby.mm.android.library.utils.Logger;

public class LPMessageDetailActivity extends Activity {

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(com.phunware.localpoint.sample.R.layout.activity_lpmessage_detail);
		// Set the default string to display where there is invalid message
		String msgBody = "The message you selected is a invalid or expired.";
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			// Get the message ID parameter
			String msgId = extras.getString("messageId");
			Logger.Debug("Notification extras: messageId=" + msgId, this);
			if (msgId != null) {
				// Construct the LPID for the message
				LPCustomID sid = new LPCustomID(msgId);
				ILPMessageProvider msgProvider = LPLocalpointService
						.getInstance(this).getMessageProvider();
				// Get message
				ILPMessage msg = msgProvider.getMessage(sid);
				// Convert the special character '%'
				msgBody = msg.getBody().replaceAll("\\%", "%25");
			}
		}
		// Display the message body in a web view
		((WebView) findViewById(com.phunware.localpoint.sample.R.id.message_body)).loadData(msgBody,
				"text/html", "UTF-8");
	}


}
