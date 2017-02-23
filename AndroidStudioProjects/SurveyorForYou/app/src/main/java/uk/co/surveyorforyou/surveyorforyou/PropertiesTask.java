package uk.co.surveyorforyou.surveyorforyou;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Ruwan on 16/02/2017.
 */

public class PropertiesTask extends AsyncTask<Void,Void,Void> {

    String json_file = "http://www.surveyor-for-you.co.uk/Android/getProperties.php";
    Context context;
    ProgressDialog progressDialog;

    public PropertiesTask(){}

    public PropertiesTask(Context context){
        this.context = context;
    }


    @Override
    protected void onPreExecute() {
        //super.onPreExecute();
        progressDialog = new ProgressDialog(context);
        progressDialog.setIndeterminate(true);
        progressDialog.setTitle("Please wait...");
        progressDialog.setMessage("Download in progress...");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            URL url = new URL(json_file);
            HttpURLConnection httpurlcon = (HttpURLConnection) url.openConnection();
            InputStream streaminput = httpurlcon.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(streaminput));
            StringBuilder stringBuilder = new StringBuilder();
            String line;

            while ((line = bufferedReader.readLine()) != null){

                stringBuilder.append(line+"\n");
                Thread.sleep(500);
            }

            String json_data = stringBuilder.toString().trim();
            JSONObject jsonObject = new JSONObject(json_data);
            JSONArray jsonArray = jsonObject.getJSONArray("server_response");
            PropertiesDbHelper propertiesDbHelper = new PropertiesDbHelper(context);
            SQLiteDatabase db = propertiesDbHelper.getWritableDatabase();

            int count = 0;

            while (count < jsonArray.length()){

                JSONObject jo = new JSONArray().getJSONObject(count);
                count++;

                propertiesDbHelper.putInformation(jo.getString("job_number"),jo.getString("first_name"),jo.getString("phone"),jo.getString("survey_postcode"),jo.getString("date_quoted"),jo.getString("date_due"),db);
            }

            propertiesDbHelper.close();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        //super.onPostExecute(aVoid);
        progressDialog.dismiss();
    }
}
