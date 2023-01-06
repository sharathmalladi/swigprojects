public class Program {
        static {
        System.loadLibrary("rlclientlib");
    }

    public static void main(String args[]) {
        configuration config = new configuration();
        String endpoint = "https://javasdktests.cognitiveservices.azure.com/";
        config.set("http.api.key", "<apikeygoeshere>");
        config.set("interaction.http.api.host", endpoint + "personalizer/v1.1-preview.3/logs/interactions");
        config.set("observation.http.api.host", endpoint + "personalizer/v1.1-preview.3/logs/observations");
        config.set("interaction.sender.implementation", "INTERACTION_HTTP_API_SENDER");
        config.set("observation.sender.implementation", "OBSERVATION_HTTP_API_SENDER");
        // TBD: sub sample rate
        config.set("model.blob.uri", endpoint + "personalizer/v1.1-preview.3/model");
        config.set("model.source", "HTTP_MODEL_DATA");
        config.set("protocol.version", "2");

        live_model liveModel = new live_model(config);
        liveModel.init();
        ranking_response response;
        int retVal = liveModel.choose_rank("eventid", "context_json", response);
        System.out.println("choose rank returned: " + retVal);
    }
}

