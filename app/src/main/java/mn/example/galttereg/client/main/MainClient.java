package mn.example.galttereg.client.main;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import mn.example.galttereg.client.listener.DBSDataProviderListener;
import mn.example.galttereg.client.manager.CustomAssetsFileDBSDataProvider;
import mn.example.galttereg.client.manager.DBSDataProvider;
import mn.example.galttereg.client.request.Request;
import mn.example.galttereg.client.response.Response;
import mn.example.galttereg.client.util.DBSClient;

public class MainClient implements DBSClient {
    private URI baseUri;
    private DBSDataProvider dataProvider;
    private Context context;

    public MainClient(Context context) {
        this.context = context;
    }

    @Override
    public void setBaseURI(URI var1) {

    }

    @Override
    public void setDataProvider(DBSDataProvider var1) {

    }

    @Override
    public URI getBaseURI() {
        return this.baseUri;
    }

    @Override
    public DBSDataProvider getDBSDataProvider() {
        return this.dataProvider;
    }

    public void getUserId(String uri, DBSDataProviderListener dbsDataProviderListener) {
        CustomAssetsFileDBSDataProvider customAssetsFileDBSDataProvider = new CustomAssetsFileDBSDataProvider(this.context);
        try {
            customAssetsFileDBSDataProvider.execute(buildRequestPost(new URI(uri), new HashMap<>()), new DBSDataProviderListener() {
                @Override
                public void onSuccess(Response var1) {
                    Log.e("asd", "onSuccess: " + var1.getStringResponse());
                    dbsDataProviderListener.onSuccess(var1);
                }

                @Override
                public void onError(Response var1) {
                    Log.e("asd", "onError: " + var1.getResponseCode());
                    Log.e("asd", "onError: " + var1.getStringResponse());
                    Log.e("asd", "onError: " + var1.getErrorMessage());
                    dbsDataProviderListener.onError(var1);
                }
            });
        } catch (URISyntaxException e) {
            Log.e("asd", "URISyntaxException: " + e.getReason());
            Log.e("asd", "URISyntaxException: " + e.getMessage());
            Log.e("asd", "URISyntaxException: " + e.toString());
            e.printStackTrace();
            Response errorResponce = new Response();
            errorResponce.setErrorMessage("server-тэй холбогдоход алдаа гарлаа");
            errorResponce.setResponseCode(500);
            dbsDataProviderListener.onError(errorResponce);
        }

    }

    private Request buildRequest(URI baseUri, String param) throws URISyntaxException {
        Request request = new Request();
        request.setRequestMethod("GET");
        request.setUri(new URI(baseUri + param));
        request.setHeaders(null);
        return request;
    }

    private Request buildRequestPost(URI baseUri, Map<String, String> queryParams) throws URISyntaxException {
        Request request = new Request();
        request.setRequestMethod("POST");
        String url = baseUri.toString();
        request.setUri(new URI(url));
        request.setBody((new Gson()).toJson(queryParams));
        request.setHeaders(null);
        return request;
    }
}
