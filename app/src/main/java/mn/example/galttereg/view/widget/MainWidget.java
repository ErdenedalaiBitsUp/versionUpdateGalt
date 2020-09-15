package mn.example.galttereg.view.widget;

import android.content.Context;
import android.util.Log;

import mn.example.galttereg.client.listener.DBSDataProviderListener;
import mn.example.galttereg.client.main.MainClient;
import mn.example.galttereg.client.main.MainParser;
import mn.example.galttereg.client.main.UserListener;
import mn.example.galttereg.client.response.Response;

public class MainWidget {
    private Context context;
    private MainClient mainClient;
    private MainParser mainParser;

    public MainWidget(Context context) {
        this.context = context;
        this.mainClient = new MainClient(context);
        this.mainParser = new MainParser();
    }

    public void getUserId(String uri, UserListener userListener) {
        this.mainClient.getUserId(uri, new DBSDataProviderListener() {
            @Override
            public void onSuccess(Response var1) {
                userListener.onSuccess(mainParser.parseUser(var1.getStringResponse()));
            }

            @Override
            public void onError(Response var1) {
                Log.e("asd", "onError: " + var1.getErrorMessage());
                userListener.errorResponse(var1);

            }
        });
    }
}
