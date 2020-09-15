package mn.example.galttereg.client.request;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class Request {
    private Map<String, String> headers;
    private String body;
    private String requestMethod;
    private URI uri;

    public Request(Map<String, String> headers, String body, String requestMethod, URI uri) {
        this.headers = headers;
        this.body = body;
        this.requestMethod = requestMethod;
        this.uri = uri;
    }

    public Request() {
        this(new HashMap(), "", "", (URI)null);
    }

    public Map<String, String> getHeaders() {
        return this.headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public String getBody() {
        return this.body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getRequestMethod() {
        return this.requestMethod;
    }

    public void setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
    }

    public URI getUri() {
        return this.uri;
    }

    public void setUri(URI uri) {
        this.uri = uri;
    }
}
