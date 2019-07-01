package com.example.bookdetails;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Loader;

import java.util.ArrayList;
import java.util.List;

class bookloader extends AsyncTaskLoader<ArrayList<DataModel>> {
    private String mUrl;

    public bookloader(Context context,String url) {
        super(context);
        mUrl=url;
    }
    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public ArrayList<DataModel> loadInBackground() {
        if (mUrl == null) {
            return null;
        }

        // Perform the network request, parse the response, and extract a list of earthquakes.
        ArrayList<DataModel> earthquakes = Utils.fetchEarthquakeData(mUrl);
        return earthquakes;    }
}
