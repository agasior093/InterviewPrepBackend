package pl.agasior.interviewprep.testutils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pl.agasior.interviewprep.dto.*;

import java.nio.charset.StandardCharsets;

@Component
public class RequestFactory {
    private static final String QUESTION_ENDPOINT = "/question";
    private static final String UPDATE_QUESTION_STATUS_ENDPOINT = QUESTION_ENDPOINT + "/status";
    private static final String TAG_ENDPOINT = "/tag";
    private static final String SIGN_UP_ENDPOINT = "/auth/signUp";
    private static final String GET_QUESTIONS_BY_TAGS = "/getQuestionsByTags";

    private static final String CONTENT_TYPE = "Content-Type";
    private static final String APPLICATION_JSON = "application/json";

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

    public MockHttpServletRequestBuilder updateQuestionStatus(UpdateQuestionStatusRequest request) throws JsonProcessingException {
        return patchRequest(UPDATE_QUESTION_STATUS_ENDPOINT, mapper.writeValueAsString(request));
    }

    public MockHttpServletRequestBuilder signUp(SignUpRequest request) throws JsonProcessingException {
        return postRequest(SIGN_UP_ENDPOINT, mapper.writeValueAsString(request));
    }

    public MockHttpServletRequestBuilder GetQuestionsByTags(GetQuestionsByTagsRequest request) throws JsonProcessingException {
        return postRequest(QUESTION_ENDPOINT + GET_QUESTIONS_BY_TAGS, mapper.writeValueAsString(request));
    }

    public MockHttpServletRequestBuilder getTags() {
        return getRequest(TAG_ENDPOINT);
    }

    private MockHttpServletRequestBuilder postRequest(String url, String body) {
        return MockMvcRequestBuilders.post(url)
                .content(body)
                .header(CONTENT_TYPE, APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8.name());
    }

    private MockHttpServletRequestBuilder patchRequest(String url, String body) {
        return MockMvcRequestBuilders.patch(url)
                .content(body)
                .header(CONTENT_TYPE, APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8.name());
    }

    private MockHttpServletRequestBuilder getRequest(String url) {
        return MockMvcRequestBuilders.get(url)
                .header(CONTENT_TYPE, APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8.name());
    }
}
