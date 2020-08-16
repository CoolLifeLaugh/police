package com.lhsj.police.dingding;

import com.lhsj.police.dingding.controller.DingDingController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebAppConfiguration // 开启web应用配置
@SpringBootTest
public class DingDingApp {

    private MockMvc mvc;

    @Before
    public void setUp() {
        mvc = MockMvcBuilders.standaloneSetup(new DingDingController()).build();
    }

    @Test
    public void sendText() throws Exception {
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .get("/dingding/send/text")
                .accept(MediaType.APPLICATION_JSON_UTF8);

        mvc.perform(builder)
                .andExpect(status().isOk()) // 用于判断返回的期望值
                .andExpect(content().string(equalTo("true")));

    }
}
