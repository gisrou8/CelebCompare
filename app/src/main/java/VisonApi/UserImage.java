package VisonApi;

import com.google.api.services.vision.v1.model.Landmark;

import java.util.List;

/**
 * Created by gisro on 3-11-2017.
 */

public class UserImage {

    private List<Landmark> landmarks;


    public UserImage(List<Landmark> landmarks)
    {
        this.landmarks = landmarks;
    }
}
