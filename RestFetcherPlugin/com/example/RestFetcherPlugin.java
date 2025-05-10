package com.example;

import com.remedy.arsys.plugincontainer.*;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class RestFetcherPlugin implements Plugin {

    @Override
    public void processRequest(PluginContext pc) throws IOException {
        String path = pc.getRequest().getPathInfo();
        System.out.println(">> RestFetcherPlugin.processRequest path = " + path);

        if ("/params".equals(path)) {
            pc.getResponse().setContentType("application/json");
            pc.getResponse().setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
            pc.getResponse().setHeader("Pragma", "no-cache");
            pc.getResponse().setHeader("Expires", "0");
            pc.getResponse().setHeader("Content-Disposition", "inline");

            PrintWriter out = pc.getResponse().getWriter();
            out.print("{ \"name\": \"RestFetcher\", \"version\": \"1.0.22\", \"type\": \"Visual\", " +
                    "\"description\": \"Plugin that fetches REST data\", \"author\": \"AR Plugin\", " +
                    "\"parameters\": [\"urlField\", \"targetField\"] }");
            out.flush();
            return;
        }

        pc.getResponse().setContentType("text/html;charset=UTF-8");
        pc.getResponse().setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        pc.getResponse().setHeader("Pragma", "no-cache");
        pc.getResponse().setHeader("Expires", "0");
        pc.getResponse().setHeader("Content-Disposition", "inline");

        PrintWriter out = pc.getResponse().getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<html><head><meta charset='UTF-8'>");

        // Inkluderar Mid Tier-eventstödet
        out.println(pc.getPageService().getEventInfrastructureCode());

        // JS som diagnostiserar vad som finns tillgängligt
        out.println("<script type='text/javascript'>");
        out.println("function init() {");
        out.println("  console.log('[RestFetcher] INIT start');");

        out.println("  var origin = document.referrer.split('/').slice(0,3).join('/');");
        out.println("  console.log('[RestFetcher] Referrer origin:', origin);");

        out.println("  if (typeof EventDispatcher !== 'undefined') {");
        out.println("    console.log('[RestFetcher] EventDispatcher FOUND');");
        out.println("    console.log('[RestFetcher] Available methods on EventDispatcher:', Object.keys(EventDispatcher));");

        out.println("    if (typeof EventDispatcher.setParentOrigin === 'function') {");
        out.println("      EventDispatcher.setParentOrigin(origin);");
        out.println("    } else {");
        out.println("      console.warn('[RestFetcher] setParentOrigin() not available');");
        out.println("    }");

        out.println("    if (typeof EventDispatcher.subscribe === 'function') {");
        out.println("      EventDispatcher.subscribe('TriggerFetch', function(url) {");
        out.println("        console.log('[RestFetcher] TriggerFetch received:', url);");
        out.println("        EventDispatcher.sendEventToMidTier('FetchURL', url).then(js => {");
        out.println("          try { eval(js); } catch(err) {");
        out.println("            console.error('[RestFetcher] Eval error:', err);");
        out.println("            document.getElementById('output').innerText = 'Error: ' + err;");
        out.println("          }");
        out.println("        });");
        out.println("      });");
        out.println("    } else {");
        out.println("      console.warn('[RestFetcher] subscribe() not available');");
        out.println("    }");

        out.println("  } else {");
        out.println("    console.warn('[RestFetcher] EventDispatcher NOT available');");
        out.println("  }");
        out.println("}");

        out.println("window.addEventListener('load', init);");
        out.println("</script>");

        out.println("</head><body>");
        out.println("<h3>RestFetcher is ready.</h3>");
        out.println("<div id='output'></div>");
        out.println("</body></html>");
        out.flush();
    }

    @Override
    public String handleEvent(PluginContext pc, String eventType, String eventData) {
        System.out.println(">> handleEvent: " + eventType + " with data: " + eventData);

        if (!"FetchURL".equals(eventType)) return "";

        String json = callExternalAPI(eventData);
        String escaped = json
                .replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "")
                .replace("\r", "");

        // Returnerar JSON till fält 1225050901
        return "EventDispatcher.sendEventToParentField(1225050901, \"" + escaped + "\");";
    }

    private String callExternalAPI(String url) {
        try (InputStream in = new URL(url).openStream()) {
            return new String(in.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            return "{\"error\": \"" + e.getMessage().replace("\"", "'") + "\"}";
        }
    }

    @Override public void cleanup() {}
    @Override public void init(PluginConfig config) {}

    @Override
    public DefinitionFactory getDefinitionFactory() {
        return new DefinitionFactory() {
            @Override
            public Definition createFromString(PluginContext context, DefinitionKey key, String defJson) {
                return null;
            }
            @Override
            public Definition createFromStream(PluginContext context, DefinitionKey key, InputStream stream) {
                return null;
            }
        };
    }
}
