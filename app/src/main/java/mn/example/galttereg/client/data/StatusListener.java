package mn.example.galttereg.client.data;

import mn.example.galttereg.client.response.Response;

public interface StatusListener {
    void onSuccess(StatusResponse var1);
    void onError(Response var2);
}
