package pl.agasior.interviewprep.testutils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pl.agasior.interviewprep.dto.CreateQuestionRequest;

import java.nio.charset.StandardCharsets;

@Component
public class RequestFactory {
    private static final String QUESTION_ENDPOINT = "/question";

    private final ObjectMapper mapper;

    RequestFactory() {
        this.mapper = new ObjectMapper();
    }

    public MockHttpServletRequestBuilder createQuestion(CreateQuestionRequest request) throws JsonProcessingException {
        return buildRequest(QUESTION_ENDPOINT, mapper.writeValueAsString(request));
    }

    private MockHttpServletRequestBuilder buildRequest(String url, String body) {
        return MockMvcRequestBuilders.post(url)
                .content(body)
                .header("Content-Type", "application/json")
                .characterEncoding(StandardCharsets.UTF_8.name());
    }
}
