package mn.example.galttereg.client.response;


import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.Nullable;

import com.google.gson.GsonBuilder;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mn.example.galttereg.client.data.ErrorCodes;


public class Response implements Parcelable {
    private int responseCode;
    private int requestCode;
    private String errorMessage;
    private Map<String, List<String>> headers;
    protected byte[] byteResponse;
    private String statusText;
    public static final Creator<Response> CREATOR = new Creator<Response>() {
        @Override
        public Response createFromParcel(Parcel source) {
            return null;
        }

        @Override
        public Response[] newArray(int size) {
            return new Response[0];
        }
    };

    public Response() {
        this(0, 0, "", new HashMap(), (byte[])null);
    }

    public Response(Exception exception) {
        this(0, 0, exception.getLocalizedMessage(), new HashMap(), (byte[])null);
    }

    public Response(String errorMessage, int responseCode, Map<String, List<String>> headers) {
        this.errorMessage = errorMessage;
        this.responseCode = responseCode;
        this.headers = headers;
    }

    public Response(Response response) {
        this(response.getErrorMessage(), response.getResponseCode(), response.getHeaders());
    }

    public Response(String errorMessage, ErrorCodes errorCode) {
        this(errorMessage, errorCode.getCode(), new HashMap());
    }

    public Response(String errorMessage, int responseCode) {
        this(errorMessage, responseCode, new HashMap());
    }

    public Response(int responseCode, int requestCode, String errorMessage, Map<String, List<String>> headers, byte[] byteResponse) {
        this.responseCode = responseCode;
        this.requestCode = requestCode;
        this.errorMessage = errorMessage;
        this.headers = headers;
        this.byteResponse = byteResponse;
    }

    public Response(int responseCode, int requestCode, String errorMessage, Map<String, List<String>> headers, byte[] byteResponse, String statusText) {
        this.responseCode = responseCode;
        this.requestCode = requestCode;
        this.errorMessage = errorMessage;
        this.headers = headers;
        this.byteResponse = byteResponse;
        this.statusText = statusText;
    }

    public Response(Parcel in) {
        this.responseCode = in.readInt();
        this.errorMessage = in.readString();
        in.createByteArray();
        this.headers = in.readHashMap(List.class.getClassLoader());
    }

    @Deprecated
    public void setResponse(byte[] byteResponse) {
        this.byteResponse = byteResponse;
    }

    @Deprecated
    public byte[] getResponse() {
        return this.byteResponse;
    }

    public byte[] getByteResponse() {
        return this.byteResponse;
    }

    public void setByteResponse(byte[] byteResponse) {
        this.byteResponse = byteResponse;
    }

    public <T> T getResponse(Class<T> type) {
        return (new GsonBuilder()).create().fromJson(StringUtils.getString(this.byteResponse), type);
    }

    @Nullable
    public String getStringResponse() {
        return StringUtils.getString(this.byteResponse);
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public int getResponseCode() {
        return this.responseCode;
    }

    public void setHeaders(Map<String, List<String>> headers) {
        this.headers = headers;
    }

    public Map<String, List<String>> getHeaders() {
        return this.headers;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public boolean isErrorResponse() {
        return this.responseCode >= 400 || this.responseCode < 200;
    }

    public boolean isRedirect() {
        return this.responseCode < 400 && this.responseCode >= 300;
    }

    public int getRequestCode() {
        return this.requestCode;
    }

    public void setRequestCode(int requestCode) {
        this.requestCode = requestCode;
    }

    public String getStatusText() {
        return this.statusText;
    }

    public void setStatusText(String statusText) {
        this.statusText = statusText;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o != null && this.getClass() == o.getClass()) {
            Response var2 = (Response)o;
            return ((Response)o).isErrorResponse() ? (new EqualsBuilder()).append(this.responseCode, var2.responseCode).append(this.errorMessage, var2.errorMessage).isEquals() : (new EqualsBuilder()).append(this.responseCode, var2.responseCode).append(this.byteResponse, var2.byteResponse).isEquals();
        } else {
            return false;
        }
    }

    public int hashCode() {
        return (new HashCodeBuilder(17, 37)).append(this.errorMessage).append(this.byteResponse).append(this.responseCode).toHashCode();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.responseCode);
        dest.writeString(this.errorMessage);
        dest.writeByteArray(this.byteResponse);
        dest.writeMap(this.headers);
    }
}
