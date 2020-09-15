package mn.example.galttereg.client.manager;

import mn.example.galttereg.client.listener.DBSDataProviderListener;
import mn.example.galttereg.client.request.Request;

public interface DBSDataProvider {

    void execute(Request var1, DBSDataProviderListener var2);

    default boolean cancel(Request request) {
        return false;
    }
}
