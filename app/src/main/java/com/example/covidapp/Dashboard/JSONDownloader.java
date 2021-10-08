package com.example.covidapp.Dashboard;

import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import androidx.fragment.app.FragmentActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.URL;
import java.net.URLConnection;

public class JSONDownloader implements Serializable {
    private String[][] regionDoubleArray = new String[22][3];
    private String[][] ageDoubleArray = new String[11][3];

    public JSONDownloader(){

    }

    public void startDownload(FragmentActivity activity){
        Log.i("JSONDownloader", "Starting JSON downloads");
        new Thread(new Runnable() {
            public void run() {
                JSONObject regionJSON = AsyncDownloader.doInBackground("region");

                fillArray(regionJSON, regionDoubleArray, "region");
                //printArrays(); //Används för debugging

                Intent doneIntent = new Intent();
                doneIntent.setAction("JSON_DOWNLOAD_COMPLETE_REGION");
                activity.sendBroadcast(doneIntent);
            }
        }).start();
        new Thread(new Runnable() {
            public void run() {
                JSONObject ageJSON = AsyncDownloader.doInBackground("age");

                fillArray(ageJSON, ageDoubleArray, "age");
                //printArrays(); //Används för debugging

                Intent doneIntent = new Intent();
                doneIntent.setAction("JSON_DOWNLOAD_COMPLETE_AGE");
                activity.sendBroadcast(doneIntent);
            }
        }).start();
    }



    private void fillArray(JSONObject jsonObject, String[][] array, String mode){
        try {
            int i = 0;
            while(i < jsonObject.getJSONArray("features").length()){
                if(mode.equals("region"))
                    array[i][0] = jsonObject.getJSONArray("features").getJSONObject(i).getJSONObject("attributes").getString("Region");
                else
                    array[i][0] = jsonObject.getJSONArray("features").getJSONObject(i).getJSONObject("attributes").getString("Åldersgrupp");

                array[i][1] = jsonObject.getJSONArray("features").getJSONObject(i).getJSONObject("attributes").getString("Totalt_antal_fall");
                array[i][2] = jsonObject.getJSONArray("features").getJSONObject(i).getJSONObject("attributes").getString("Totalt_antal_avlidna");
                i++;
            }

        }catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void printArrays(){
        int i = 0;
        try {
            while(regionDoubleArray[i][0] != null){
                Log.i("Row" + i, regionDoubleArray[i][0] + " " + regionDoubleArray[i][1] + " " + regionDoubleArray[i][2]);
                i++;
            }
            i = 0;
            while(ageDoubleArray[i][0] != null){
                Log.i("Row" + i, ageDoubleArray[i][0] + " " + ageDoubleArray[i][1] + " " + ageDoubleArray[i][2]);
                i++;
            }
        }catch (Exception e){
            Log.e("Error " + i, "Out of bounds error");
        }

    }

    public String[][] getAgeDoubleArray() {
        return ageDoubleArray;
    }

    public String[][] getRegionDoubleArray() {
        return regionDoubleArray;
    }
}

/* https://stackoverflow.com/questions/33229869/get-json-data-from-url-using-android USER: Christpger Bonitz */
abstract class AsyncDownloader extends AsyncTask<Void, Void, JSONObject>
{

    protected static JSONObject doInBackground(String mode)
    {
        String str="";
        if(mode == "region")
            str="https://services5.arcgis.com/fsYDFeRKu1hELJJs/ArcGIS/rest/services/FOHM_Covid_19_FME_1/FeatureServer/0/query?where=1%3D1&objectIds=&time=&geometry=&geometryType=esriGeometryEnvelope&inSR=&spatialRel=esriSpatialRelIntersects&resultType=none&distance=0.0&units=esriSRUnit_Meter&returnGeodetic=false&outFields=Region%2C+Totalt_antal_fall%2C+Totalt_antal_avlidna&returnGeometry=false&returnCentroid=false&featureEncoding=esriDefault&multipatchOption=xyFootprint&maxAllowableOffset=&geometryPrecision=&outSR=&datumTransformation=&applyVCSProjection=false&returnIdsOnly=false&returnUniqueIdsOnly=false&returnCountOnly=false&returnExtentOnly=false&returnQueryGeometry=false&returnDistinctValues=false&cacheHint=false&orderByFields=&groupByFieldsForStatistics=&outStatistics=&having=&resultOffset=&resultRecordCount=&returnZ=false&returnM=false&returnExceededLimitFeatures=true&quantizationParameters=&sqlFormat=none&f=pjson&token=";
        else
            str="https://services5.arcgis.com/fsYDFeRKu1hELJJs/arcgis/rest/services/FOHM_Covid_19_FME_1/FeatureServer/4/query?where=1%3D1&objectIds=&time=&resultType=none&outFields=%C3%85ldersgrupp%2C+Totalt_antal_fall%2C+totalt_antal_avlidna&returnIdsOnly=false&returnUniqueIdsOnly=false&returnCountOnly=false&returnDistinctValues=false&cacheHint=false&orderByFields=&groupByFieldsForStatistics=&outStatistics=&having=&resultOffset=&resultRecordCount=&sqlFormat=none&f=pjson&token=";
        URLConnection urlConn = null;
        BufferedReader bufferedReader = null;
        try
        {
            URL url = new URL(str);
            urlConn = url.openConnection();
            bufferedReader = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));

            StringBuffer stringBuffer = new StringBuffer();
            String line;
            while ((line = bufferedReader.readLine()) != null)
            {
                stringBuffer.append(line);
            }

            return new JSONObject(stringBuffer.toString());
        }
        catch(Exception ex)
        {
            Log.e("App", "yourDataTask", ex);
            return null;
        }
        finally
        {
            if(bufferedReader != null)
            {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    protected void onPostExecute(JSONObject response)
    {
        if(response != null)
        {
            try {
                Log.e("App", "Success: " + response.getString("yourJsonElement") );
            } catch (JSONException ex) {
                Log.e("App", "Failure", ex);
            }
        }
    }
}