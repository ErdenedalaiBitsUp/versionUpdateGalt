package mn.example.galttereg.client.util;

import java.net.URI;

import mn.example.galttereg.client.manager.DBSDataProvider;


public interface DBSClient {
    void setBaseURI(URI var1);

    void setDataProvider(DBSDataProvider var1);

    URI getBaseURI();

    DBSDataProvider getDBSDataProvider();
}
