package com.kanstantin.camping.controllers;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kanstantin.camping.MyIntegrationTest;
import com.kanstantin.camping.domain.TripDao;
import com.kanstantin.camping.model.TripDTO;

public class TripApiControllerTest extends MyIntegrationTest {

    private MockMvc mockMvc;

    @Autowired
    private TripDao tripDao;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setup() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void getTripTest() throws Exception {
        TripDTO trip = new TripDTO();
        trip.setName("Test Trip");
        trip.setStartDate("2022-11-15");
        trip.setEndDate("2022-12-01");
        trip.setParticipants(5);
        trip = tripDao.save(trip);

        this.mockMvc.perform(get("/api/trips/" + trip.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name", is(trip.getName())))
                .andExpect(jsonPath("$.data.id", is(trip.getId())));
    }

    @Test
    public void addTripTest() throws Exception {
        TripDTO trip = new TripDTO();
        trip.setName("Test Trip");
        trip.setStartDate("2022-11-15");
        trip.setEndDate("2022-12-01");
        trip.setParticipants(5);

        this.mockMvc.perform(post("/api/trips")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(trip)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id", greaterThan(0)))
                .andExpect(jsonPath("$.data.name", is(trip.getName())));
    }


    @Test
    public void updateTripTest() throws Exception {
        TripDTO trip = new TripDTO();
        trip.setName("Initial Trip");
        trip.setStartDate("2022-11-01");
        trip.setEndDate("2022-11-10");
        trip.setParticipants(3);
        trip = tripDao.save(trip);

        trip.setName("Updated Trip");
        trip.setStartDate("2022-12-01");
        trip.setEndDate("2022-12-10");
        trip.setParticipants(4);

        this.mockMvc.perform(put("/api/trips/" + trip.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(trip)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id", is(trip.getId())))
                .andExpect(jsonPath("$.data.name", is(trip.getName())))
                .andExpect(jsonPath("$.data.startDate", is(trip.getStartDate())))
                .andExpect(jsonPath("$.data.endDate", is(trip.getEndDate())))
                .andExpect(jsonPath("$.data.participants", is(trip.getParticipants())));
    }
}
