package pl.agasior.interviewprep.testutils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pl.agasior.interviewprep.dto.CreateQuestionRequest;
import pl.agasior.interviewprep.dto.UpdateQuestionRequest;

import java.nio.charset.StandardCharsets;

@Component
public class RequestFactory {
    private static final String QUESTION_ENDPOINT = "/question";
    private static final String TAG_ENDPOINT = "/tag";

    private final ObjectMapper mapper;

    RequestFactory() {
        this.mapper = new ObjectMapper();
    }

    public MockHttpServletRequestBuilder createQuestion(CreateQuestionRequest request) throws JsonProcessingException {
        return postRequest(QUESTION_ENDPOINT, mapper.writeValueAsString(request));
    }

    public MockHttpServletRequestBuilder updateQuestion(UpdateQuestionRequest request) throws JsonProcessingException {
        return patchRequest(QUESTION_ENDPOINT, mapper.writeValueAsString(request));
    }

    public MockHttpServletRequestBuilder getTags() {
        return getRequest(TAG_ENDPOINT);
    }

    private MockHttpServletRequestBuilder postRequest(String url, String body) {
        return MockMvcRequestBuilders.post(url)
                .content(body)
                .header("Content-Type", "application/json")
                .characterEncoding(StandardCharsets.UTF_8.name());
    }

    private MockHttpServletRequestBuilder patchRequest(String url, String body) {
        return MockMvcRequestBuilders.patch(url)
                .content(body)
                .header("Content-Type", "application/json")
                .characterEncoding(StandardCharsets.UTF_8.name());
    }

    private MockHttpServletRequestBuilder getRequest(String url) {
        return MockMvcRequestBuilders.get(url)
                .header("Content-Type", "application/json")
                .characterEncoding(StandardCharsets.UTF_8.name());
    }
}
