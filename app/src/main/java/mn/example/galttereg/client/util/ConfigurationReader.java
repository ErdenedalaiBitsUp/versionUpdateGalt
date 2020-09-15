package mn.example.galttereg.client.util;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;

/**
 * Created by Backbase R&D B.V on 04/04/2018.
 * Configuration reader to read configurations set in app-configuration-local.json.
 */
public class ConfigurationReader {
    public interface ConfigurationProvider {
        String getConfiguration();
    }

    @VisibleForTesting
    static final String DEFAULT_CONFIGURATION_PATH = "backbase/conf/app-configuration.json";
    private static final String TAG = ConfigurationReader.class.getSimpleName();
    private static final String CONFIGURATION_CHARSET = "UTF-8";
    private JsonObject configuration;
    private Context context;

    private ConfigurationProvider configurationProvider;

    public ConfigurationReader(@NonNull final Context context) {
        this.context = context;
        this.configurationProvider = new DefaultConfigurationProvider();
        parseConfiguration();
    }

    public ConfigurationReader(@NonNull final ConfigurationProvider configurationProvider) {
        this.configurationProvider = configurationProvider;
        parseConfiguration();
    }

    private void parseConfiguration() {
        final String configurationString = configurationProvider.getConfiguration();
        configuration = new Gson().fromJson(configurationString, JsonObject.class);
    }

    @NonNull
    private String[] splitPath(String path) {
        return path.split("\\.");
    }

    /**
     * @param path         configuration key, being read as a path. Parent > child relation divided by a ".".
     * @param defaultValue value to default to if not set or JSONException occurs.
     * @return boolean value set in configuration or false if mapping doesn't exist and a JSONException gets thrown.
     */
    public boolean getBooleanValue(String path, boolean defaultValue) {
        String[] split = splitPath(path);
        try {
            JsonObject jsonObject = navigateThrough(split, configuration);
            return jsonObject.get(split[split.length - 1]).getAsBoolean();
        } catch (JSONException | NullPointerException e) {
            return defaultValue;
        }
    }

    /**
     * @param path         configuration key, being read as a path. Parent > child relation divided by a ".".
     * @param defaultValue value to default to if not set or JSONException occurs.
     * @return String value set in configuration or null if mapping doesn't exist and a JSONException gets thrown.
     */
    public String getStringValue(String path, String defaultValue) {
        String[] split = splitPath(path);
        try {
            JsonObject jsonObject = navigateThrough(split, configuration);
            return jsonObject.get(split[split.length - 1]).getAsString();
        } catch (JSONException | NullPointerException e) {
            return defaultValue;
        }
    }

    /**
     * @param path         configuration key, being read as a path. Parent > child relation divided by a ".".
     * @return JsonArray value set in configuration or empty array if mapping doesn't exist and a JSONException gets thrown.
     */
    public JsonArray getJsonArrayValue(String path) {
        String[] split = splitPath(path);
        try {
            JsonObject jsonObject = navigateThrough(split, configuration);
            return jsonObject.get(split[split.length - 1]).getAsJsonArray();
        } catch (JSONException | NullPointerException e) {
            return new JsonArray();
        }
    }

    /**
     * Set value in loaded configuration.
     *
     * @param path  configuration key, being read as a path. Parent > child relation divided by a ".".
     * @param value String value to set.
     */
    public void setValue(String path, String value) {
        String[] split = splitPath(path);
        try {
            JsonObject jsonObject = navigateThrough(split, configuration, true);
            jsonObject.addProperty(split[split.length - 1], value);
        } catch (JSONException e) {
        }
    }

    /**
     * Set value in loaded configuration.
     *
     * @param path  configuration key, being read as a path. Parent > child relation divided by a ".".
     * @param value boolean value to set.
     */
    public void setValue(String path, boolean value) {
        String[] split = splitPath(path);
        try {
            JsonObject jsonObject = navigateThrough(split, configuration, true);
            jsonObject.addProperty(split[split.length - 1], value);
        } catch (JSONException e) {
        }
    }

    @SuppressWarnings("WeakerAccess")
    @VisibleForTesting
    public JsonObject navigateThrough(String[] splitPath, JsonObject configurationObject) throws JSONException {
        return navigateThrough(splitPath, configurationObject, false);
    }


    @SuppressWarnings("WeakerAccess")
    @VisibleForTesting
    public JsonObject navigateThrough(String[] splitPath, JsonObject configurationObject, boolean createIfNotExisting) throws JSONException {
        if (splitPath.length > 1) {
            JsonObject subConfigurationObject = configurationObject.getAsJsonObject(splitPath[0]);
            if (subConfigurationObject == null && createIfNotExisting) {
                subConfigurationObject = new JsonObject();
                configurationObject.add(splitPath[0], subConfigurationObject);
            }
            String[] newSplitPath = Arrays.copyOfRange(splitPath, 1, splitPath.length);
            return navigateThrough(newSplitPath, subConfigurationObject, createIfNotExisting);
        } else {
            return configurationObject;
        }
    }

    private class DefaultConfigurationProvider implements ConfigurationProvider {
        @Override
        public String getConfiguration() {
            InputStream configurationStream = null;
            BufferedReader bufferedReader = null;
            try {
                configurationStream = context.getAssets().open(DEFAULT_CONFIGURATION_PATH);
                bufferedReader = new BufferedReader(new InputStreamReader(configurationStream, CONFIGURATION_CHARSET));
                final StringBuilder stringBuilder = new StringBuilder();
                String str;

                while ((str = bufferedReader.readLine()) != null) {
                    stringBuilder.append(str);
                }

                return stringBuilder.toString();
            } catch (final Exception e) {
            } finally {
                if (configurationStream != null) {
                    try { configurationStream.close(); } catch (final Exception e) {}
                }
                if (bufferedReader != null) {
                    try { bufferedReader.close(); } catch (final Exception e) {}
                }
            }
            return null;
        }
    }
}
