package mn.example.galttereg.client.data;

public enum ErrorCodes {
    CANNOT_PARSE(1),
    INVALID_MODEL(2),
    INVALID_PARAMETERS(3),
    INVALID_CONFIGURATION(4),
    INVALID_VERSION(5),
    HASH_MISMATCH(-1001),
    SSL_CERTIFICATE_VIOLATION(-1002),
    URL_NOT_IN_WHITELIST(-1003),
    WEBVIEW_REQUEST_BLOCKED(-1004),
    GENERAL_TARGETING_ERROR(-4001),
    SECURITY_BREACH_ACTIVITY_HIJACKING(-5001),
    SECURITY_BREACH_ZIP_PATH_TRAVERSAL(-5002),
    NO_INTERNET(-10000),
    REQUEST_CANCELLED(-999);

    private final int code;

    private ErrorCodes(int responseCode) {
        this.code = responseCode;
    }

    public final int getCode() {
        return this.code;
    }
}
