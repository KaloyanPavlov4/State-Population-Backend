package kaloyan.state_population_backend.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import kaloyan.state_population_backend.model.County;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class CountyApiService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    private static final String API_URL = "https://services.arcgis.com/P3ePLMYs2RVChkJx/ArcGIS/rest/services/USA_Census_Counties/FeatureServer/0";
    private static final String QUERY_COUNTY_NAME_POPULATION_STATE = "/query?where=1=1&outFields=STATE_NAME,+NAME,+POPULATION&returnGeometry=false&featureEncoding=esriDefault&f=pjson";
    private static final String QUERY_COUNTY_NAME_POPULATION_STATE_COUNT = "/query?where=1=1&outFields=STATE_NAME,+NAME,+POPULATION&returnGeometry=false&returnCountOnly=true&featureEncoding=esriDefault&f=pjson";
    private static final String PARAMETER_OFFSET = "&resultOffset=%s";
    private static final String PARAMETER_RECORDCOUNT = "&resultRecordCount=%s";

    private Integer totalCount = null;

    public CountyApiService(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    public Integer getTotalCount() throws JsonProcessingException {
        if(totalCount == null) {
            ResponseEntity<String> response = restTemplate.getForEntity(API_URL + QUERY_COUNTY_NAME_POPULATION_STATE_COUNT, String.class);
            totalCount = objectMapper.readTree(response.getBody()).get("count").asInt();
        }
        return totalCount;
    }

    public Page<County> fetchCountyAndPopulation(Pageable pageable) throws JsonProcessingException {
        long offset = pageable.getOffset();
        int limit = pageable.getPageSize();

        StringBuilder url = new StringBuilder(API_URL);
        url.append(QUERY_COUNTY_NAME_POPULATION_STATE);
        url.append(String.format(PARAMETER_OFFSET, offset));
        url.append(String.format(PARAMETER_RECORDCOUNT, limit));

        List<County> counties = new ArrayList<>();
        ResponseEntity<String> response = restTemplate.getForEntity(url.toString(), String.class);
            JsonNode root = objectMapper.readTree(response.getBody());
            JsonNode features = root.get("features");

            if (features.isArray()) {
                for (JsonNode countyNode : features) {
                    JsonNode attributes = countyNode.get("attributes");

                    County county = new County();
                    county.setName(attributes.get("NAME").asText());
                    county.setState(attributes.get("STATE_NAME").asText());
                    if(attributes.get("POPULATION").isNull()) county.setPopulation(null);
                    else county.setPopulation(attributes.get("POPULATION").asLong());

                    counties.add(county);
                }
            }

        return new PageImpl<>(counties, pageable, getTotalCount());
    }
}
