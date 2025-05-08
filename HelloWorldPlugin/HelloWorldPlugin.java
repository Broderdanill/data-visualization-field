package HelloWorldPlugin;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletResponse;
import com.bmc.arsys.pluginsvr.Plugin;
import com.bmc.arsys.pluginsvr.PluginConfig;
import com.bmc.arsys.pluginsvr.PluginContext;
import com.bmc.arsys.pluginsvr.NoPermissionException;
import com.bmc.arsys.pluginsvr.DefinitionFactory;

public class HelloWorldPlugin implements Plugin {

    public void init(PluginConfig config) {
        // Initialisering om nödvändigt
    }

    public void processRequest(PluginContext pc) throws IOException, NoPermissionException {
        HttpServletResponse response = pc.getResponse();
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter writer = response.getWriter();
        writer.println("<html><head><title>Hello World Plugin</title></head>");
        writer.println("<body><h1>Hello World</h1></body></html>");
    }

    public String handleEvent(PluginContext pc, String eventType, String eventData) {
        return null;
    }

    public DefinitionFactory getDefinitionFactory() {
        return null;
    }

    public void cleanup() {
        // Rensning om nödvändigt
    }
}
