package pl.agasior.interviewprep.testutils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.test.web.servlet.MvcResult;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Optional;

public class ResponseParser {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static Optional<String> getStringValue(MvcResult mvcResult, String param) throws UnsupportedEncodingException, JsonProcessingException {
        final var json = mvcResult.getResponse().getContentAsString();
        final var responseMap = mapper.readValue(json, HashMap.class);
        return Optional.ofNullable((String) responseMap.get(param));
    }
}
