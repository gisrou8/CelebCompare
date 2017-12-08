package KairosApi;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Gisrou on 8-12-2017.
 */

public interface KairosCallback {
    void onSuccess(JSONObject result) throws JSONException;
}
