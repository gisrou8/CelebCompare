package KairosApi;

import android.content.Context;

import com.android.volley.Request;


import com.android.volley.*;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static android.app.PendingIntent.getActivity;

/**
 * Created by gisro on 8-11-2017.
 */

public class Kairos {

    String url = "https://api.kairos.com/verify";
    final String imageUrl = "https://i.pinimg.com/736x/89/d6/d7/89d6d73d82e497cac0be0246d39f0ed4--square-face-shapes-square-faces.jpg";
    Context context;

    public Kairos(Context context)
    {
        this.context = context;

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            JSONArray jsonarray = jsonResponse.getJSONArray("images");
                            JSONObject network = jsonarray.getJSONObject(0);
                            JSONObject transaction = network.getJSONObject("transaction");
                            String confidence = transaction.getString("confidence");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                }
        ) {


            @Override
            public byte[] getBody() throws AuthFailureError {
                JSONObject params = new JSONObject();
                // the POST parameters:
                try {
                    params.put("image", imageUrl);
                    params.put("gallery_name", "MyGallery");
                    params.put("subject_id", "Brad Pitt");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return params.toString().getBytes();
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Content-Type", "application/json");
                params.put("app_id", "6d5bf9d5");
                params.put("app_key", "b840b0b3af45f1f919c29da422c3aefe");
                return params;
            }



        };


        Request request = Volley.newRequestQueue(context).add(postRequest);


    }


    public void enrollPhotoInGallery()
    {

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            String site = jsonResponse.getString("face_id"),
                                    network = jsonResponse.getString("confidence");
                            System.out.println("Site: "+site+"\nNetwork: "+network);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                }
        ) {


            @Override
            public byte[] getBody() throws AuthFailureError {
                JSONObject params = new JSONObject();
                // the POST parameters:
                try {
                    params.put("image", imageUrl);
                    params.put("subject_id", "Brad Pitt");
                    params.put("gallery_name", "MyGallery");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return params.toString().getBytes();
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Content-Type", "application/json");
                params.put("app_id", "6d5bf9d5");
                params.put("app_key", "b840b0b3af45f1f919c29da422c3aefe");
                return params;
            }



        };


        Request request = Volley.newRequestQueue(context).add(postRequest);
    }




}
