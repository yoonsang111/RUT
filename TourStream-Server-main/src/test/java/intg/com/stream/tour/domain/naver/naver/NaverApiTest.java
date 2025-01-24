package com.stream.tour.domain.naver.naver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.stream.tour.domain.naver.naver.dto.NaverGetInquiryRequest;
import com.stream.tour.domain.naver.naver.dto.NaverOAuthRequest;
import com.stream.tour.domain.naver.naver.dto.NaverOAuthResponse;
import com.stream.tour.domain.naver.naver.dto.SaveProductRequest;
import com.stream.tour.domain.naver.naver.enums.NaverUrl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;

import static com.stream.tour.domain.naver.naver.dto.NaverOAuthRequest.Type;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@Slf4j
@DisplayName("네이버 API 테스트")
@SpringBootTest
public class NaverApiTest {

    @Value("${api.naver.client.id}")
    private String clientId;

    @Value("${api.naver.client.secret}")
    private String clientSecret;

    @Value("${api.naver.host}")
    private String host;

    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        restTemplate = new RestTemplate();
        objectMapper = new ObjectMapper()
                .enable(SerializationFeature.INDENT_OUTPUT)
                .registerModule(new JavaTimeModule());
    }

    private String generateAccessToken() {
        long timestamp = System.currentTimeMillis();
        String signature = SignatureGenerator.generateSignature(clientId, clientSecret, timestamp);
        MultiValueMap<String, Object> request = NaverOAuthRequest.toMultiValueMap(objectMapper, clientId, timestamp, signature, Type.SELF, null);

        // when
        ResponseEntity<NaverOAuthResponse> responseEntity = restTemplate.postForEntity(host + NaverUrl.OAUTH2_TOKEN.getUrl(), request, NaverOAuthResponse.class);
        NaverOAuthResponse response = responseEntity.getBody();

        return response.getAccessToken();
    }

    @DisplayName("인증 토큰 발급 요청")
    @Test
    public void oauth2Test() throws Exception {
        // given
        long timestamp = System.currentTimeMillis();
        String signature = SignatureGenerator.generateSignature(clientId, clientSecret, timestamp);
        MultiValueMap<String, Object> request = NaverOAuthRequest.toMultiValueMap(objectMapper, clientId, timestamp, signature, Type.SELF, null);

        // when
        ResponseEntity<NaverOAuthResponse> responseEntity = restTemplate.postForEntity(host + NaverUrl.OAUTH2_TOKEN.getUrl(), request, NaverOAuthResponse.class);
        NaverOAuthResponse response = responseEntity.getBody();

        // then
        log.info("response = {}", objectMapper.writeValueAsString(response));
        assertAll(
                () -> assertThat(response).isNotNull(),
                () -> assertThat(response.getAccessToken()).isNotNull(),
                () -> assertThat(response.getExpiresIn()).isGreaterThan(0),
                () -> assertThat(response.getTokenType()).isEqualTo("Bearer")
        );
    }

    @DisplayName("고객 문의 내역 조회")
    @Test
    public void testGetInquiries() throws Exception {
        // given
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(generateAccessToken());

        HttpEntity request = new HttpEntity(httpHeaders);

        UriComponents uriComponents = UriComponentsBuilder.fromUriString(host + NaverUrl.INQUIRIES.getUrl())
                .queryParams(NaverGetInquiryRequest.toMultiValueMap(objectMapper, 1, 10, LocalDate.of(2023, 01, 01), LocalDate.of(2023, 12, 31), null))
                .build();

        // when
        ResponseEntity<String> exchange = restTemplate.exchange(uriComponents.toUriString(), HttpMethod.GET, request, String.class);

        // then
        log.info("exchange = {}", objectMapper.writeValueAsString(exchange));
        assertAll(
                () -> assertThat(exchange).isNotNull(),
                () -> assertThat(exchange.getStatusCode()).isEqualTo(HttpStatus.OK)
        );
    }

     /**
     * http://shop1.phinf.naver.net/20240220_175/1708430695926qCf0G_PNG/1593922806245872_927794774.png
     */
    @DisplayName("상품 이지미 다건 등록")
    @Test
    void testProductImageUpload() throws Exception {
        // given
        Resource file = new ClassPathResource("/static/images/product/banana_milk.jpeg");

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(generateAccessToken());
        httpHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultipartBodyBuilder multipartBodyBuilder = new MultipartBodyBuilder();
        multipartBodyBuilder.part("imageFiles", file, MediaType.IMAGE_JPEG);

        MultiValueMap<String, HttpEntity<?>> multipartBody = multipartBodyBuilder.build();

        HttpEntity<MultiValueMap<String, HttpEntity<?>>> requestEntity = new HttpEntity<>(multipartBody, httpHeaders);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(
                host + NaverUrl.PRODUCT_IMAGES_UPLOAD.getUrl(),
                requestEntity,
                String.class
        );

        System.out.println("body = " + responseEntity.getBody());
    }

    @Test
    public void testGetLeafCategories() throws Exception {
        // given
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(generateAccessToken());
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        // when
        HttpEntity<String> request = new HttpEntity<>(httpHeaders);
        UriComponents uriComponents = UriComponentsBuilder.fromUriString(host + NaverUrl.CATEGORIES.getUrl())
                .queryParam("last", "true").build();

        // then
        ResponseEntity<String> exchange = restTemplate.exchange(uriComponents.toUriString(), HttpMethod.GET, request, String.class);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonElement je = JsonParser.parseString(exchange.getBody());
        String prettyJsonString = gson.toJson(je).replace("\\u003e", ">");
        System.out.println("forObject = " + prettyJsonString);
    }

    @Test
    public void testGetChannels() {
        // given
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(generateAccessToken());
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        // when
        HttpEntity<String> request = new HttpEntity<>(httpHeaders);
        UriComponents uriComponents = UriComponentsBuilder.fromUriString(host + NaverUrl.CHANNELS.getUrl()).build();

        // then
        ResponseEntity<String> exchange = restTemplate.exchange(uriComponents.toUriString(), HttpMethod.GET, request, String.class);
        System.out.println("forObject = " + exchange.getBody());
    }

    @DisplayName("원산지 코드 정보 전체 조회")
    @Test
    void testProductOriginAreas() {
        // given
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(generateAccessToken());
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        // when
        HttpEntity<String> request = new HttpEntity<>(httpHeaders);
        UriComponents uriComponents = UriComponentsBuilder.fromUriString(host + NaverUrl.ORIGIN_AREAS.getUrl()).build();

        // then
        ResponseEntity<String> exchange = restTemplate.exchange(uriComponents.toUriString(), HttpMethod.GET, request, String.class);
        System.out.println("forObject = " + exchange.getBody());
    }

    @DisplayName("상품 등록")
    @Test
    public void testSaveProduct() throws Exception {
        // given
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(generateAccessToken());
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        // when
        String body = objectMapper.writeValueAsString(SaveProductRequest.createSaveProductRequest());
        System.out.println("body = " + body);
        HttpEntity<String> request = new HttpEntity<>(body, httpHeaders);

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(host + NaverUrl.SAVE_PRODUCT.getUrl(), request, String.class);
        String response = responseEntity.getBody();

        System.out.println("response = " + response);

        // then
    }
}
