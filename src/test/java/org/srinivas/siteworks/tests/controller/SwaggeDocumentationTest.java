package org.srinivas.siteworks.tests.controller;


import org.codehaus.jackson.map.ObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.context.WebApplicationContext;
import org.srinivas.siteworks.config.AppConfig;
import org.srinivas.siteworks.webconfig.ChangeMvcContextConfiguration;

import java.nio.charset.Charset;
import java.util.Map;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class, ChangeMvcContextConfiguration.class})
@WebAppConfiguration
public class SwaggeDocumentationTest {


    private static final Logger logger = LoggerFactory.getLogger(SwaggeDocumentationTest.class);
    protected static final MediaType JSON_MEDIA_TYPE = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("UTF-8"));
    protected static final MediaType XML_MEDIA_TYPE = new MediaType(MediaType.APPLICATION_XML.getType(), MediaType.APPLICATION_XML.getSubtype(), Charset.forName("UTF-8"));
    protected MockMvc mockMvc;


    @Autowired
    protected WebApplicationContext webApplicationContext;


    @Before
    public void setUp() throws Exception {
        mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    @After
    public void tearDown() throws Exception {
        this.mockMvc = null;

    }


    @Test
    public void testSwaggerApiDocs() throws Exception {

        try {

            MvcResult result = mockMvc.perform(get("/v2/api-docs"))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(JSON_MEDIA_TYPE))
                    .andReturn();

            ObjectMapper jsonMapper = new ObjectMapper();
            String resultString = result.getResponse().getContentAsString();
            logger.info("content=" + resultString);
            org.codehaus.jackson.JsonNode jsonNode = jsonMapper.readTree(resultString);
            Map<String, Object> data = jsonMapper.convertValue(jsonNode, Map.class);
            assertTrue(data.get("swagger").equals("2.0"));
        } catch (Exception e) {
            logger.info(e.getMessage());
            fail("Failed Due to: " + e.getMessage());
        }

    }

    @Test
    public void testSwaggerHtml() throws Exception {

        mockMvc.perform(get("/swagger-ui.html"))
                .andExpect(status().isOk());

    }


}
