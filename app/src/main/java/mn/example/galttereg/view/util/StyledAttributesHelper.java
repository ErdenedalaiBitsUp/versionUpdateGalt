package mn.example.galttereg.view.util;

import android.content.res.TypedArray;

import androidx.annotation.Nullable;

public class StyledAttributesHelper {
    private StyledAttributesHelper() {
    }

    public static String getStyleableAttributeString(@Nullable TypedArray styledAttributes, int styleable, String defaultValue) {
        if (styledAttributes == null) {
            return defaultValue;
        }
        String retVal = styledAttributes.getString(styleable);
        if (retVal == null) {
            return defaultValue;
        }
        return retVal;
    }
}
