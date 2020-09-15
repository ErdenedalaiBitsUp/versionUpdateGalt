package mn.example.galttereg.client.data;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

public class Additions implements Parcelable {
    @SerializedName("additions")
    @Expose
    @Nullable
    private Map<String, String> additions;
    public static final Creator<Additions> CREATOR = new Creator<Additions>() {
        public Additions createFromParcel(Parcel source) {
            return new Additions(source);
        }

        public Additions[] newArray(int size) {
            return new Additions[size];
        }
    };

    public Additions() {
    }

    public Additions(Parcel in) {
        int size = in.readInt();
        if (size > -1) {
            for(int i = 0; i < size; ++i) {
                String key = in.readString();
                String value = in.readString();
                this.add(key, value);
            }
        }

    }

    @Nullable
    public Map<String, String> getAdditions() {
        return this.additions;
    }

    public void setAdditions(@Nullable Map<String, String> additions) {
        this.additions = additions;
    }

    public void add(String key, String value) {
        if (this.additions == null) {
            this.additions = new HashMap();
        }

        this.additions.put(key, value);
    }

    public void clear() {
        if (this.additions != null) {
            this.additions.clear();
            this.additions = null;
        }

    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        if (this.additions == null) {
            dest.writeInt(-1);
        } else {
            dest.writeInt(this.additions.size());
            Iterator var3 = this.additions.entrySet().iterator();

            while(var3.hasNext()) {
                Entry<String, String> entry = (Entry)var3.next();
                dest.writeString((String)entry.getKey());
                dest.writeString((String)entry.getValue());
            }
        }

    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (!(o instanceof Additions)) {
            return false;
        } else {
            Additions that = (Additions)o;
            return this.additions == null && that.additions == null || this.additions != null && this.additions.equals(that.additions);
        }
    }

    public int hashCode() {
        return Objects.hash(new Object[]{this.additions});
    }

    public String toString() {
        return Additions.class.getSimpleName() + "{additions=" + this.additions + '}';
    }
}
