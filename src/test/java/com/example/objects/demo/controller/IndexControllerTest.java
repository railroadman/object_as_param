package com.example.objects.demo.controller;

import com.example.objects.demo.dto.UserDto;
import com.example.objects.demo.filter.Filter;
import com.example.objects.demo.repository.DataRepo;
import com.example.objects.demo.service.Fservice;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.Assert;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@Import({Fservice.class, DataRepo.class})
@WebMvcTest(controllers = IndexController.class)
class IndexControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private Fservice fservice;
    @MockBean
    private DataRepo repo;


    @Test
    void paramWithObject() throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();
        Filter filter = new Filter();
        filter.setAge(12);
        filter.setLogin("login1");
        filter.setFirstName("Vasya");
        filter.setEmail("dwdwd@gmail.com");
        MultiValueMap<String, String> map = convert(filter);
        List<UserDto> data = List.of(new UserDto(12, "dwdwd@gmail.com", "login1", "Vasya", "Popov"),
                                     new UserDto(22, "test2@gmail.com", "login2", "Misha", "Ivanov"));

        when(repo.getData(refEq(filter))).thenReturn(data);

        MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/").params(map)).andExpect(status().isOk()).andReturn();
        String result_str = result.getResponse().getContentAsString();
        System.out.println(result_str);
        Assertions.assertTrue(result_str.equals("ok"));

        verify(repo,times(1)).getData(refEq(filter));

    }

    private MultiValueMap<String, String> convert(Object obj) {
        ObjectMapper objectMapper = new ObjectMapper();
        MultiValueMap parameters = new LinkedMultiValueMap<String, String>();
        Map<String, String> maps = objectMapper.convertValue(obj, new TypeReference<Map<String, String>>() {
        });
        parameters.setAll(maps);

        return parameters;
    }
}