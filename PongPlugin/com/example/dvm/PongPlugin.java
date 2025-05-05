package com.example.dvm;

import com.bmc.arsys.api.ARException;
import com.bmc.arsys.api.StatusInfo;
import com.bmc.arsys.pluginsvr.plugins.ARPlugin;
import com.bmc.arsys.pluginsvr.plugins.ARPluginContext;

import java.util.Collections;
import java.util.Map;

public class PongPlugin extends ARPlugin {

    public void initialize(ARPluginContext context) throws ARException {
        // Nothing to initialize
    }

    public void execute(ARPluginContext context, Map<String, Object> input, Map<String, Object> output) throws ARException {
        try {
            // Hårdkodat svar
            output.put("536870914", "pong");
        } catch (Exception e) {
            throw new ARException(Collections.singletonList(
                new StatusInfo(2, 10000, "PongPlugin error: " + e.getMessage())
            ));
        }
    }
}
