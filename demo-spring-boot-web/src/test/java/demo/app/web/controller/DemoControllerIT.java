package demo.app.web.controller;

import static demo.app.web.test.CustomSecurityMockMvcRequestPostProcessors.mockUserAdmin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.anonymous;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.testng.Assert.assertEquals;

import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.testng.annotations.Test;

import demo.app.web.test.BaseWebIntegrationTest;

public class DemoControllerIT extends BaseWebIntegrationTest {

    @Test
    public void home_anonymous() throws Exception {

        final String URI = "/demo";
        RequestBuilder builder = get(URI).with(csrf()).with(anonymous());
        performRequest(builder, status().isFound());
    }

    @Test
    public void home() throws Exception {

        final String URI = "/demo";
        RequestBuilder builder = get(URI).with(csrf()).with(mockUserAdmin());
        MvcResult result = performRequest(builder, status().isOk()).andReturn();

        assertEquals("demo/home", result.getModelAndView().getViewName());
    }

    @Test
    public void demo_security() throws Exception {

        final String URI = "/demo/security";
        RequestBuilder builder = post(URI).with(csrf()).with(mockUserAdmin());
        MvcResult result = performRequest(builder, status().isOk()).andReturn();

        assertEquals("demo/security/security", result.getModelAndView().getViewName());
    }
}
