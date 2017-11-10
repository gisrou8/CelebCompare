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
import java.util.Timer;

import static android.app.PendingIntent.getActivity;
import static java.lang.Thread.sleep;

/**
 * Created by gisro on 8-11-2017.
 */

public class Kairos {

    private String getAllFromGalleryurl = "https://api.kairos.com/gallery/view";
    private String enrollPhotoInGalleryUrl = "https://api.kairos.com/enroll";
    private String verifyImageUrl = "https://api.kairos.com/verify";
    private String deleteFromGalleryUrl = "https://api.kairos.com/gallery/remove_subject";
    private final String imageUrl = "https://i.pinimg.com/736x/89/d6/d7/89d6d73d82e497cac0be0246d39f0ed4--square-face-shapes-square-faces.jpg";
    Context context;

    JSONArray malecelebs;
    JSONArray femalecelebs;
    boolean finished;

    public Kairos(Context context)
    {
        this.context = context;

    }


    public JSONArray getMalecelebs()
    {
        return malecelebs;
    }

    public JSONArray getFemalecelebs()
    {
        return femalecelebs;
    }



    public void deleteFromGallery()
    {
        StringRequest postRequest = new StringRequest(Request.Method.POST, deleteFromGalleryUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
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


    public void getAllFromGallery() throws InterruptedException {
            StringRequest postRequest = new StringRequest(Request.Method.POST, getAllFromGalleryurl,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                malecelebs = jsonResponse.getJSONArray("subject_ids");

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
                        params.put("gallery_name", "MyGalleryMale");
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

    public void getAllFromGalleryFemale() throws InterruptedException {
        StringRequest postRequest = new StringRequest(Request.Method.POST, getAllFromGalleryurl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            femalecelebs = jsonResponse.getJSONArray("subject_ids");

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


    public void enrollPhotoInGallery(final String name, final String URL)
    {

        StringRequest postRequest = new StringRequest(Request.Method.POST, enrollPhotoInGalleryUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            String id = jsonResponse.getString("face_id"),
                                    network = jsonResponse.getString("confidence");
                            System.out.println("Site: "+id+"\nNetwork: "+network);
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
                    params.put("image", URL);
                    params.put("subject_id", name);
                    params.put("gallery_name", "MyGalleryMale");
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






    public Double[] verifyImage(final String celeb, final String image, final String gallery)
    {
        final Double[] confidence = new Double[1];
        StringRequest postRequest = new StringRequest(Request.Method.POST, verifyImageUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            JSONArray jsonarray = jsonResponse.getJSONArray("images");
                            JSONObject network = jsonarray.getJSONObject(0);
                            JSONObject transaction = network.getJSONObject("transaction");
                            confidence[0] = transaction.getDouble("confidence");
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
                    params.put("image", image);
                    params.put("gallery_name", gallery);
                    params.put("subject_id", celeb);

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
        return confidence;



    }




}
