package mn.example.galttereg.client.response;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.commons.lang3.StringEscapeUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;

public final class StringUtils {
    private static final String LOGTAG = StringUtils.class.getSimpleName();
    private static final String FILE_PREFIX = "file:";
    private static final String UTF_8 = "UTF-8";
    private static final String REGEX_DOT = "\\.";

    private StringUtils() {
    }

    @Nullable
    public static String getString(InputStream in) {
        if (in == null) {
            return null;
        } else {
            BufferedReader in1 = new BufferedReader(new InputStreamReader(in));
            StringBuilder var1 = new StringBuilder();

            try {
                String var2;
                try {
                    while((var2 = in1.readLine()) != null) {
                        var1.append(var2);
                    }
                } catch (IOException var9) {
                }
            } finally {
                try {
                    in1.close();
                } catch (IOException var8) {
                }

            }

            return var1.toString();
        }
    }

    public static String decodeXmlEntity(byte[] bytes) {
        return StringEscapeUtils.unescapeXml(getString(bytes));
    }

    @Nullable
    public static String getString(byte[] bytes) {
        try {
            return new String(bytes, "UTF-8");
        } catch (NullPointerException | UnsupportedEncodingException var1) {
            return null;
        }
    }

    public static String getItemRefFromLink(String link) {
        String var1 = link.replaceFirst("file:", "").split(Pattern.quote("?"))[0];
        if (link.endsWith("/")) {
            var1 = var1.substring(0, var1.length() - 1);
        }

        return var1;
    }

    public static String getJsDictionaryFrom(Map<String, String> valuesMap) {
        StringBuilder var1 = new StringBuilder();
        Iterator valuesMap1 = valuesMap.entrySet().iterator();
        var1.append("{");

        while(valuesMap1.hasNext()) {
            Entry var2 = (Entry)valuesMap1.next();
            var1.append("'").append((String)var2.getKey()).append("':'").append(((String)var2.getValue()).replace("'", "\\'")).append("'");
            if (valuesMap1.hasNext()) {
                var1.append(",");
            }
        }

        var1.append("}");
        return var1.toString();
    }

    public static String replaceLast(String string, String substring, String replacement) {
        int substring1;
        return (substring1 = string.lastIndexOf(substring)) == -1 ? string : string.substring(0, substring1) + replacement;
    }

    public static String getHandshakeHost(URL url) {
        StringBuilder var1 = new StringBuilder(url.getProtocol() + "://" + url.getHost());
        if (url.getPort() != -1) {
            var1.append(":");
            var1.append(url.getPort());
        }

        return var1.toString();
    }

    public static int compareVersion(String baseVersion, String toCompareVersion) {
        if (baseVersion == null) {
            return 1;
        } else if (toCompareVersion == null) {
            return -1;
        } else {
            ArrayList toCompareVersion1 = new ArrayList(Arrays.asList(toCompareVersion.split("\\.")));
            ArrayList baseVersion1 = new ArrayList(Arrays.asList(baseVersion.split("\\.")));
            if (toCompareVersion1.size() > baseVersion1.size()) {
                a(toCompareVersion1, baseVersion1);
            } else if (toCompareVersion1.size() < baseVersion1.size()) {
                a(baseVersion1, toCompareVersion1);
            }

            int var2;
            for(var2 = 0; a(toCompareVersion1, baseVersion1, var2) && ((String)toCompareVersion1.get(var2)).equals(baseVersion1.get(var2)); ++var2) {
            }

            return a(toCompareVersion1, baseVersion1, var2) ? Integer.signum(Integer.valueOf((String)toCompareVersion1.get(var2)).compareTo(Integer.valueOf((String)baseVersion1.get(var2)))) : Integer.signum(toCompareVersion1.size() - baseVersion1.size());
        }
    }

    private static boolean a(List<String> var0, List<String> var1, int var2) {
        return var2 < var0.size() && var2 < var1.size();
    }

    public static String composeListOfParams(Map<String, String> parms) {
        boolean var1 = true;
        StringBuilder var2 = new StringBuilder();
        Iterator parms1 = parms.entrySet().iterator();

        while(parms1.hasNext()) {
            Entry var3 = (Entry)parms1.next();
            if (var1) {
                var1 = false;
            } else {
                var2.append('&');
            }

            try {
                var2.append(URLEncoder.encode((String)var3.getKey(), "UTF-8")).append("=").append(URLEncoder.encode((String)var3.getValue(), "UTF-8"));
            } catch (UnsupportedEncodingException var4) {
            }
        }

        return var2.toString();
    }

    private static void a(List<String> var0, List<String> var1) {
        for(int var2 = 0; var2 < var0.size(); ++var2) {
            try {
                var1.get(var2);
            } catch (IndexOutOfBoundsException var4) {
                var1.add("0");
            }
        }

    }

    public static String getUrlWithoutQuery(String url) {
        return url == null ? "" : url.split("\\?")[0];
    }

    public static String getQueryFromUrl(String url) {
        try {
            return url.split("\\?")[1];
        } catch (ArrayIndexOutOfBoundsException var1) {
            return "";
        }
    }

    @Nullable
    public static Map<String, String> getQueryFromUrlAsMap(String url) {
        return (url = getQueryFromUrl(url)) != null ? getQueryToMap(url) : null;
    }

    @NonNull
    public static Map<String, String> getQueryToMap(String query) {
        LinkedHashMap var1 = new LinkedHashMap();
        if (query == null) {
            return var1;
        } else {
            String[] query1;
            int var2 = (query1 = query.split("&")).length;

            for(int var3 = 0; var3 < var2; ++var3) {
                String var4;
                int var5;
                if ((var5 = (var4 = query1[var3]).indexOf(61)) > 0) {
                    var1.put(var4.substring(0, var5), var4.substring(var5 + 1));
                }
            }

            return var1;
        }
    }

    @Nullable
    public static String getQueryFromUrlAsJsonString(String url) {
        Map url1;
        return (url1 = getQueryFromUrlAsMap(url)) != null ? (new GsonBuilder()).create().toJson(url1) : "";
    }

    public static String listToJSONArrayString(List<String> list) {
        return list != null ? (new Gson()).toJson(list) : null;
    }

    public static String getMajorVersion(@NonNull String version) {
        return version.split("\\.")[0];
    }

    public static String removeSlashesAtEndOfUrl(String url) {
        while(url.endsWith("/")) {
            url = url.substring(0, url.length() - 1);
        }

        return url;
    }

    public static String guaranteeOneSlashAtEndOfUrl(String url) {
        return removeSlashesAtEndOfUrl(url) + "/";
    }
}
