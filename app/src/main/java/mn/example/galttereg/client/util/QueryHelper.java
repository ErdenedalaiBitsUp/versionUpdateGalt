package mn.example.galttereg.client.util;

import androidx.annotation.Nullable;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import mn.example.galttereg.client.request.Request;


/**
 * Created by Backbase R&D B.V on 23/05/2018.
 * Helper class for query
 */
public class QueryHelper {

    private static final String TAG = QueryHelper.class.getSimpleName();

    private QueryHelper() {
        //Hide constructor
    }

    /**
     * Gets the parameter from a query, if the parameter does not exist, it returns null
     *
     * @param query     query to get the parameter from
     * @param paramName paramName to get the value from
     * @return parameter value or null if not present.
     */
    @Nullable
    public static String getParam(@Nullable String query, String paramName) {
        if (query != null && query.contains(paramName)) {
            for (String paramPair : query.split("&")) {
                String[] paramSplit = paramPair.split("=");
                if (paramSplit.length > 1 && paramSplit[0].equals(paramName)) {
                    try {
                        return URLDecoder.decode(paramSplit[1], "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                    }
                }
            }
        }
        return null;
    }

    /**
     * Get int value for parameter from query. Will return -1 if param is not present.
     *
     * @param query     query to get the value from
     * @param paramName parameter name to use as key
     * @return parameter value or -1 if not present.
     */
    public static int getIntParam(@Nullable String query, String paramName) {
        String param = QueryHelper.getParam(query, paramName);
        if (param == null) {
            return -1;
        }
        return Integer.parseInt(param);
    }

    /**
     * Get int value for parameter from query. Will return -1 if param is not present.
     *
     * @param query     query to get the value from
     * @param paramName parameter name to use as key
     * @return parameter value or -1 if not present.
     */
    public static double getDoubleParam(@Nullable String query, String paramName) {
        String param = getParam(query, paramName);
        if (param == null) {
            return -1;
        }
        return Double.parseDouble(param);
    }


    /**
     * @param request request to get the endpoint from
     * @return endpoint as string (last part in url without query params)
     */
    @Nullable
    public static String getEndpoint(Request request) {
        String[] split = request.getUri().toString().split("/");
        return split[split.length - 1].split("\\?")[0];
    }
}
