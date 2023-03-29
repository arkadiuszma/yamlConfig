package configuration;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class PropertyStore {
    public PropertyStore() {
        this.initValues();
    }

    private void initValues() {
        getConfigValue();
    }

    protected Map <String, Object> readConfigFile(){
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        try {
            return mapper.readValue(new File("src/main/resources/config.yaml"), Map.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void getConfigValue() {
        Map <String, Object> properties = (Map<String, Object>) readConfigFile().get("browserConfig");
        if (properties != null) {
            properties.forEach((key, value) -> System.setProperty(key, value.toString()));
        }
    }
}
