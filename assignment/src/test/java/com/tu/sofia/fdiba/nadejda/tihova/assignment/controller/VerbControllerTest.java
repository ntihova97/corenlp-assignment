package com.tu.sofia.fdiba.nadejda.tihova.assignment.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(VerbController.class)
class VerbControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @Test
    void extractVerbsFromText() throws Exception {
        mockMvc.
                perform(post("/sentence/tokens/verb").content("This is a simple sentence with a two verbs that I just wrote"))
                .andExpect(status().isOk()).andExpect(content().string("[\"is\",\"wrote\"]"));

    }


    @Test
    void extractNounsFromText() throws Exception {
        mockMvc.
                perform(post("/sentence/tokens/noun").content("I like nouns"))
                .andExpect(status().isOk()).andExpect(content().string("[\"nouns\"]"));

    }

    @Test
    void extractNotImpleneted() throws Exception {
        mockMvc.perform(post("/sentence/tokens/notImplemented")).andExpect(status().isBadRequest());
    }

}