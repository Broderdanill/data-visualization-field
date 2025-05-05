package com.example.dvm;

import com.bmc.arsys.api.ARException;
import com.bmc.arsys.api.StatusInfo;
import com.bmc.arsys.pluginsvr.plugins.ARPlugin;
import com.bmc.arsys.pluginsvr.plugins.ARPluginContext;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.Map;

public class UrlFetcherPlugin extends ARPlugin {

    public void initialize(ARPluginContext context) throws ARException {
        // Init - no logging available
    }

    public void execute(ARPluginContext context, Map<String, Object> input, Map<String, Object> output) throws ARException {
        try {
            // Get input parameters from Custom Properties
            String urlFieldIdStr = (String) input.get("url");
            String responseFieldIdStr = (String) input.get("responseField");

            // Get actual field value from input map
            String targetUrl = (String) input.get(urlFieldIdStr);

            // Fetch URL content
            String response = fetchUrl(targetUrl);

            // Return result to specified field
            output.put(responseFieldIdStr, response);

        } catch (Exception e) {
            throw new ARException(Collections.singletonList(
                new StatusInfo(2, 10000, "Error in UrlFetcherPlugin: " + e.getMessage())
            ));
        }
    }

    private String fetchUrl(String urlStr) throws Exception {
        URL url = new URL(urlStr);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        StringBuilder content = new StringBuilder();
        String line;

        while ((line = in.readLine()) != null) {
            content.append(line);
        }

        in.close();
        con.disconnect();
        return content.toString();
    }
}
