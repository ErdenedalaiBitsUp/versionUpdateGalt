package mn.example.galttereg.client.util;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Backbase R&D B.V on 23/05/2018.
 * DTO containing the required behaviour of the AssetsFileDBSDataProvider.
 */
public class TestBehavior {
    @SerializedName("given")
    public final BehaviorConditions conditions;
    @SerializedName("then")
    public final List<Behavior> behaviorChain;

    public TestBehavior(BehaviorConditions conditions, List<Behavior> behaviorChain) {
        this.conditions = conditions;
        this.behaviorChain = behaviorChain;
    }

    public static class BehaviorConditions {
        private String url;
        private Map<String, String> params = new HashMap<>(); //defaults to no parameters
        private String method = "GET"; //defaults to GET request

        public String getUrl() {
            return url;
        }

        public Map<String, String> getParams() {
            return params;
        }

        public String getMethod() {
            return method;
        }
    }

    public static class Behavior {
        private String response;
        private int times = Integer.MAX_VALUE;

        public String getResponse() {
            return response;
        }

        public int getTimes() {
            return times;
        }

        public void setTimes(int times) {
            this.times = times;
        }
    }
}
