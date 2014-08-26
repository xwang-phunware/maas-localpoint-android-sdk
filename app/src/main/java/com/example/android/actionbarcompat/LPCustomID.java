package com.example.android.actionbarcompat;

import com.digby.localpoint.sdk.core.ILPID;


public class LPCustomID implements ILPID {

    public LPCustomID(String sID) {
        this.sid = sID;
    }
    
    @Override
    public String getValue() {
        return sid;
    }

    private String sid;
}