package mn.example.galttereg.client.util;

/**
 * Created by Backbase R&D B.V on 29/06/2018.
 * Enum to help with response codes.
 */
public enum ResponseCodes {
    SUCCESS(200),
    UNAUTHORIZED(401),
    FORBIDDEN(403),
    NOT_FOUND(404),
    METHOD_NOT_ALLOWED(405),
    ERROR(500),
    NO_INTERNET(-10000);

    private final int code;

    private ResponseCodes(int responseCode) {
        this.code = responseCode;
    }

    public final int getCode() {
        return this.code;
    }
}
