package mn.example.galttereg.client.storage;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import java.util.Iterator;


public class StorageComponent {

    protected Context context;
    protected SharedPreferences preferences;

    public StorageComponent() {
//        this.context = MainApplication.context;
        this.preferences = context.getSharedPreferences("m-bank.mobile.library.LOCAL_STORAGE", 0);

    }

    public void setItem(String key, String value) {
        Editor var4;
        (var4 = this.getSharedPreferenceEditor(this.preferences)).putString(key, value);
        var4.apply();
    }

    public String getItem(String key) {
        return this.preferences.getString(key, (String) null);
    }

    public void removeItem(String key) {
        Editor var3;
        (var3 = this.getSharedPreferenceEditor(this.preferences)).remove(key);
        var3.apply();

    }

    public void clear() {
        Iterator var1 = this.preferences.getAll().keySet().iterator();

        while (var1.hasNext()) {
            String var2 = (String) var1.next();
            this.removeItem(var2);
        }

    }

    protected Editor getSharedPreferenceEditor(SharedPreferences preferences) {
        return preferences.edit();
    }
}
