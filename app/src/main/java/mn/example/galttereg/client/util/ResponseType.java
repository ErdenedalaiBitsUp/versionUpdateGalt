package mn.example.galttereg.client.util;

/**
 * Created by Backbase R&D B.V on 23/05/2018.
 * Response type coming from TestBehavior defining what kind of response the AssetsFileDBSDataProvider should give.
 */
public enum ResponseType {
    DEFAULT,
    EMPTY,
    FAILED,
    UNAUTHORIZED,
    NO_INTERNET,
    FORBIDDEN
}
