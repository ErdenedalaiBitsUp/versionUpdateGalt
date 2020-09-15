package mn.example.galttereg.client.listener;


import mn.example.galttereg.client.response.Response;

public interface DBSDataProviderListener {
    void onSuccess(Response var1);

    void onError(Response var1);
}
