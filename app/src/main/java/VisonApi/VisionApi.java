package VisonApi;


import android.os.AsyncTask;

import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.services.vision.v1.Vision;
import com.google.api.services.vision.v1.VisionRequestInitializer;
import com.google.api.services.vision.v1.model.AnnotateImageRequest;
import com.google.api.services.vision.v1.model.BatchAnnotateImagesRequest;
import com.google.api.services.vision.v1.model.BatchAnnotateImagesResponse;
import com.google.api.services.vision.v1.model.FaceAnnotation;
import com.google.api.services.vision.v1.model.Feature;
import com.google.api.services.vision.v1.model.Image;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by gisro on 3-11-2017.
 */



public class VisionApi {
    private String apiKey = "AIzaSyDTrSk8Tzyjcq58wWPJ133N_40QBLDXgJs";
    private Vision vision;
    private Image imageinput;
    private InputStream inputStream;
    private byte[] photoData;
    private BatchAnnotateImagesResponse batchResponse;





    public void initializeVision()
    {
        Vision.Builder visionBuilder = new Vision.Builder(
                new NetHttpTransport(),
                new AndroidJsonFactory(),
                null);

        visionBuilder.setVisionRequestInitializer(
                new VisionRequestInitializer(apiKey));

        vision = visionBuilder.build();
    }

    public Vision getVision()
    {
        return vision;
    }

    public void encodeImage()
    {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                // Convert photo to byte array
                try {
                    photoData = IOUtils.toByteArray(inputStream);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

        imageinput.encodeContent(photoData);
    }


    public void makeRequest() throws IOException {
        encodeImage();
        Feature desiredFeature = new Feature();
        desiredFeature.setType("FACE_DETECTION");

        AnnotateImageRequest request = new AnnotateImageRequest();
        request.setImage(imageinput);
        request.setFeatures(Arrays.asList(desiredFeature));


        BatchAnnotateImagesRequest batchRequest = new BatchAnnotateImagesRequest();
        batchRequest.setRequests(Arrays.asList(request));

        batchResponse = vision.images().annotate(batchRequest).execute();

        List<FaceAnnotation> faces = batchResponse.getResponses()
                .get(0).getFaceAnnotations();

        for(int i=0; i<3; i++) {


                    faces.get(i).getLandmarks();

        }
    }














}
