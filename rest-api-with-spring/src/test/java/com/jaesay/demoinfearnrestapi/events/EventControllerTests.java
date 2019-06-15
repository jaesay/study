package com.jaesay.demoinfearnrestapi.events;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest
public class EventControllerTests {

    @Autowired
    MockMvc mockMvc;

    // MappingJacksonJson이 의존성으로 들어가 있으면 ObjectMapper가 자동으로 빈으로 등록됨
    // => 스프링부트를 사용하면 ObjectMapper가 이미 빈으로 등록되어 있음
    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void createEvent() throws Exception {

        Event event = Event.builder()
                .name("Spring")
                .description("REST API Development with Spring")
                .beginEnrollmentDateTime(LocalDateTime.of(2018, 11, 23, 14, 21))
                .closeEnrollmentDateTime(LocalDateTime.of(2018, 11, 24, 14, 21))
                .beginEventDateTime(LocalDateTime.of(2018, 11, 25, 14, 21))
                .endEventDateTime(LocalDateTime.of(2018, 11, 26, 14, 21))
                .basePrice(100)
                .maxPrice(200)
                .limitOfEnrollment(100)
                .location("강남역 D2 스타텁 팩토리")
                .build();

        mockMvc.perform(post("/api/events/")
                    .contentType(MediaType.APPLICATION_JSON_UTF8) // 이 요청의 본문에 json을 담아서 보내고 있다.
                    .accept(MediaTypes.HAL_JSON)    // HAL_JSON 스펙에 준하는 응답을 받고 싶다.
                    .content(objectMapper.writeValueAsString(event))) // contentType에 맞게 json 문자열로 바꿈
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").exists());
    }
}
