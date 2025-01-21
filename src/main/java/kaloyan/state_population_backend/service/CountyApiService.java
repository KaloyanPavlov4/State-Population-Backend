package kaloyan.state_population_backend.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import kaloyan.state_population_backend.model.County;
import kaloyan.state_population_backend.utils.State;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class CountyApiService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    private final String API_URL = "https://services.arcgis.com/P3ePLMYs2RVChkJx/ArcGIS/rest/services/USA_Census_Counties/FeatureServer/0";
    private final String QUERY_COUNTY_NAME_POPULATION_STATE = "/query?where=1=1&outFields=STATE_NAME,+NAME,+POPULATION&returnGeometry=false&featureEncoding=esriDefault&f=pjson";

    public CountyApiService(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    public List<County> fetchCountyAndPopulation() throws JsonProcessingException {
        ResponseEntity<String> response = restTemplate.getForEntity(API_URL + QUERY_COUNTY_NAME_POPULATION_STATE, String.class);
        JsonNode root = objectMapper.readTree(response.getBody());
        List<County> counties = new ArrayList<>();
        JsonNode features = root.get("features");

        if (features.isArray()) {
            for (JsonNode countyNode : features) {
                JsonNode attributes = countyNode.get("attributes");

                County county = new County();
                county.setName(attributes.get("NAME").asText());
                county.setState(State.fromStateName(attributes.get("STATE_NAME").asText()));
                county.setPopulation(attributes.get("POPULATION").asLong());

                counties.add(county);
            }
        }

        return counties;
    }
}
