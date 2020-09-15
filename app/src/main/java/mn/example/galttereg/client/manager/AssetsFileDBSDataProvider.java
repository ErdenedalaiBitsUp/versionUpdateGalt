package mn.example.galttereg.client.manager;

import android.content.Context;
import android.util.Log;

import com.apptakk.http_request.HttpRequest;
import com.apptakk.http_request.HttpRequestTask;

import java.util.Map;

import mn.example.galttereg.client.listener.DBSDataProviderListener;
import mn.example.galttereg.client.request.Request;
import mn.example.galttereg.client.response.Response;

public class AssetsFileDBSDataProvider implements DBSDataProvider {
    private Context context;

    public AssetsFileDBSDataProvider(Context context) {
        this.context = context;
    }

    public void execute(Request request, DBSDataProviderListener listener) {
        requestWorker(request, listener);
    }


    private void requestWorker(Request request, DBSDataProviderListener listener) {

        Log.e("BitUp", "request url: " + request.getUri().toString());
        Log.e("BitUp", "request body: " + request.getBody());
        Log.e("BitUp", "request body: " + request.getRequestMethod());

        try {
            if (request.getRequestMethod().equals("GET")) {
                getRequest(request, listener);
            } else if (request.getRequestMethod().equals("POST")) {
                postRequest(request, listener);
            }

        } catch (Exception var3) {
            this.buildErrorResponse(request, "Алдаа гарлаа.", listener, 405);
        }
    }

    private void getRequest(Request request, DBSDataProviderListener listener) {
        new HttpRequestTask(
                new HttpRequest(request.getUri().toString(), HttpRequest.GET, null, null, getAuthHeaders(request.getHeaders())),
                response -> {
                    Log.e("BitUp", "response code: " + response.code);
                    Log.e("BitUp", "response: " + response.body);
                    if (response.code == 200) {
                        buildAndPassResponse(response.body, listener, 200);
                    } else {
                        buildErrorResponse(request, response.body, listener, response.code);
                    }
                }).execute();

    }

    private void postRequest(Request request, DBSDataProviderListener listener) {
        new HttpRequestTask(
                new HttpRequest(request.getUri().toString(), HttpRequest.POST, request.getBody(), null, getAuthHeaders(request.getHeaders())),
                response -> {
                    Log.e("BitUp", "response code: " + response.code);
                    Log.e("BitUp", "response: " + response.body);

//                    if (response.code == 200) {
                    if (response.code >= 200 && response.code < 400) {
                        buildAndPassResponse(response.body, listener, 200);
                    } else {
                        buildErrorResponse(request, response.body, listener, response.code);
                    }
                }).execute();
    }

    //    @NonNull
    private Map<String, String> getAuthHeaders(Map<String, String> headers) {
//        StorageComponent storageComponent = new StorageComponent();
//        headers.put(AuthClient.AUTH_HEADER, "Bearer "+ storageComponent.getItem(AuthClient.ACCESS_TOKEN));
//        headers.put(AuthClient., null);
        return headers;
    }

    protected void buildAndPassResponse(String body, DBSDataProviderListener listener, int code) {
        Log.e("BitUp", "response body: " + body);
        Response var4;
        (var4 = new Response()).setResponseCode(code);
        var4.setByteResponse(body.getBytes());
        listener.onSuccess(var4);
    }

    protected void buildErrorResponse(Request request, String body, DBSDataProviderListener listener, int code) {
        Response errorResponse = new Response();
        errorResponse.setErrorMessage(body);
        errorResponse.setResponseCode(code);
        String stringUrl = request.getUri().toString();

        if (!stringUrl.matches(".*carouselLayout.*") && !stringUrl.matches(".*anonymous.*") && !stringUrl.matches(".*login.*") && code == 401) {
            Log.e("asd", "buildErrorResponse: specaial case");
        } else {
            listener.onError(errorResponse);
        }
    }

}
