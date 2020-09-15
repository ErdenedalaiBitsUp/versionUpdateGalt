package mn.example.galttereg.client.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StatusResponse extends Additions implements Parcelable {
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("cif")
    @Expose
    private String cif;

    public static final Creator<StatusResponse> CREATOR = new Creator<StatusResponse>() {
        public StatusResponse createFromParcel(Parcel source) {
            return new StatusResponse(source);
        }

        public StatusResponse[] newArray(int size) {
            return new StatusResponse[size];
        }
    };

    public StatusResponse() {
    }

    private StatusResponse(Parcel in) {
        this.code = in.readString();
        this.status = in.readString();
        this.message = in.readString();
        this.cif = in.readString();
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.code);
        dest.writeString(this.status);
        dest.writeString(this.message);
        dest.writeString(this.cif);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCif() {
        return cif;
    }

    public void setCif(String cif) {
        this.cif = cif;
    }
}
