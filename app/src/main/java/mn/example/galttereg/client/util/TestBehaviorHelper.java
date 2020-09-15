package mn.example.galttereg.client.util;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import mn.example.galttereg.client.listener.DBSDataProviderListener;
import mn.example.galttereg.client.request.Request;
import mn.example.galttereg.client.response.Response;


/**
 * Created by Backbase R&D B.V on 23/05/2018.
 * Class to help with getting both the TestBehavior list from configuration and test Response from TestBehavior list.
 * <p>
 * Extend this class if there is a need to override the getEmptyResponse method.
 */
public class TestBehaviorHelper {

    private static final String RESPONSE_BEHAVIOR_FAILED = "failed";
    private static final String RESPONSE_BEHAVIOR_NO_INTERNET = "noInternet";
    private static final String RESPONSE_BEHAVIOR_FORBIDDEN = "forbidden";
    private static final String RESPONSE_BEHAVIOR_EMPTY = "empty";
    private static final String RESPONSE_BEHAVIOR_UNAUTHORIZED = "unauthorized";
    private static final String RESPONSE_BEHAVIOR_DEFAULT = "default";

    /**
     * @return List with TestBehavior or empty if not set in configuration.
     */
    @NonNull
    public List<TestBehavior> getTestBehaviors(@NonNull final Context context) {
        String testConfigurationJson = new ConfigurationReader(context)
                .getJsonArrayValue(ConfigurationConstants.TEST_BEHAVIOR).toString();
        Type listType = new TypeToken<ArrayList<TestBehavior>>() {}.getType();
        return new Gson().fromJson(testConfigurationJson, listType);
    }

    /**
     * @param request       Request to use to trigger the right request
     * @param testBehaviors List of all TestResponses set in configuration
     */
    public void triggerTestBehavior(Request request, final List<TestBehavior> testBehaviors, TestBehaviorListener testBehaviorListener) {
        TestBehavior testBehavior = getTestBehavior(request, testBehaviors);
        ResponseType responseType = getResponseType(testBehavior);
        if (responseType == ResponseType.DEFAULT) {
            testBehaviorListener.continueNormalFlow();
        } else if (responseType == ResponseType.EMPTY) {
            Response emptyResponse = createEmptyResponse(request);
            if (emptyResponse != null) {
                testBehaviorListener.callOnSuccessWith(emptyResponse);
            } else {
                testBehaviorListener.callOnErrorWith(createErrorResponse(responseType));
            }
        } else {
            testBehaviorListener.callOnErrorWith(createErrorResponse(responseType));
        }
    }

    /**
     * @param request       Request to get the TestResponse for
     * @param testBehaviors List of all TestResponses set in configuration
     * @return TestResponse if different than default behaviour
     */
    private TestBehavior getTestBehavior(Request request, final List<TestBehavior> testBehaviors) {
        String path = request.getUri().getPath();
        String query = request.getUri().getQuery();
        String method = request.getRequestMethod();
        for (TestBehavior testBehavior : testBehaviors) {
            if (testBehavior.conditions.getUrl().equals(path) && method.equals(testBehavior.conditions.getMethod())) {
                boolean containsAllParams = true;
                for (String key : testBehavior.conditions.getParams().keySet()) {
                    String paramValue = QueryHelper.getParam(query, key);
                    if (paramValue == null || !paramValue.equalsIgnoreCase(testBehavior.conditions.getParams().get(key))) {
                        containsAllParams = false;
                    }
                }
                if (containsAllParams) {
                    return testBehavior;
                }
            }
        }
        return null;
    }

    private ResponseType getResponseType(TestBehavior testBehavior) {
        TestBehavior.Behavior behavior = getNextBehavior(testBehavior);
        if (behavior == null) {
            return ResponseType.DEFAULT;
        }
        behavior.setTimes(behavior.getTimes() - 1);
        switch (behavior.getResponse()) {
            case RESPONSE_BEHAVIOR_FAILED:
                return ResponseType.FAILED;
            case RESPONSE_BEHAVIOR_NO_INTERNET:
                return ResponseType.NO_INTERNET;
            case RESPONSE_BEHAVIOR_FORBIDDEN:
                return ResponseType.FORBIDDEN;
            case RESPONSE_BEHAVIOR_EMPTY:
                return ResponseType.EMPTY;
            case RESPONSE_BEHAVIOR_UNAUTHORIZED:
                return ResponseType.UNAUTHORIZED;
            case RESPONSE_BEHAVIOR_DEFAULT:
            default:
                return ResponseType.DEFAULT;
        }
    }

    /**
     * Gets the latest testBehavior that has a value for times that is higher than 0.
     *
     * @param testBehavior testBehavior to get the behavior from.
     * @return behavior or null if behavior chain is empty.
     */
    private TestBehavior.Behavior getNextBehavior(TestBehavior testBehavior) {
        if (testBehavior == null || testBehavior.behaviorChain.isEmpty()) {
            return null;
        }
        TestBehavior.Behavior behavior;
        behavior = testBehavior.behaviorChain.get(0);
        if (behavior.getTimes() == 0) {
            testBehavior.behaviorChain.remove(0);
            return getNextBehavior(testBehavior);
        }
        return behavior;
    }

    private Response createErrorResponse(ResponseType responseType) {
        switch (responseType) {
            case NO_INTERNET:
                return createNoInternetResponse();
            case UNAUTHORIZED:
                return createUnauthorizedResponse();
            case FORBIDDEN:
                return createForbiddenResponse();
            case FAILED:
            default:
                return createFailedResponse();
        }
    }

    private Response createNoInternetResponse() {
        Response errorResponse = new Response();
        errorResponse.setResponseCode(ResponseCodes.NO_INTERNET.getCode());
        errorResponse.setErrorMessage("No internet connection.");
        return errorResponse;
    }

    private Response createUnauthorizedResponse() {
        Response errorResponse = new Response();
        errorResponse.setResponseCode(ResponseCodes.UNAUTHORIZED.getCode());
        errorResponse.setErrorMessage("Unauthorized.");
        return errorResponse;
    }

    private Response createForbiddenResponse() {
        Response errorResponse = new Response();
        errorResponse.setResponseCode(ResponseCodes.FORBIDDEN.getCode());
        errorResponse.setErrorMessage("Forbidden.");
        return errorResponse;
    }

    protected Response createFailedResponse() {
        Response errorResponse = new Response();
        errorResponse.setResponseCode(ResponseCodes.ERROR.getCode());
        errorResponse.setErrorMessage("Something went wrong on the server.");
        return errorResponse;
    }

    /**
     * This method can be overridden in order to create a custom empty error response.
     *
     * @param request
     * @return Response that mimics an empty error response.
     */
    @Nullable
    protected Response createEmptyResponse(Request request) {
        Response response = new Response();
        response.setResponseCode(ResponseCodes.SUCCESS.getCode());
        response.setResponse("[]".getBytes());
        return response;
    }

    public interface TestBehaviorListener {
        void callOnSuccessWith(Response response);

        void callOnErrorWith(Response errorResponse);

        void continueNormalFlow();
    }

    public interface NormalFlowHandler {
        void continueNormalFlow(final Request request, final DBSDataProviderListener listener);
    }
}
