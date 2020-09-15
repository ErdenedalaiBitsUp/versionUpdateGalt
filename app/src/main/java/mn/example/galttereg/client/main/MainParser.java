package mn.example.galttereg.client.main;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;

import mn.example.galttereg.client.entity.User;

public class MainParser {
    public ArrayList<User> parseUsers(String json) {
        String jsonArray = "";
        try {
            JSONObject obj = new JSONObject(json);
            if (obj.get("status").equals("SUCCESS"))
                jsonArray = obj.getJSONObject("items").toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Type listType = (new TypeToken<User>() {
        }).getType();
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        return gson.fromJson(jsonArray, listType);
    }

    public User parseUser(String json) {
//        String jsonObject = "";
//        try {
//            JSONObject obj = new JSONObject(json);
//            if (obj.get("status").equals("SUCCESS"))
//                jsonObject = obj.getJSONObject("data").toString();
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
        Gson gson = new Gson();
        return gson.fromJson(json, User.class);
    }
}
