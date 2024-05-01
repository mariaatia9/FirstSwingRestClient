/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ibb.myswingrestclient;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibb.model.Person;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.ClientInfoStatus;
import java.util.List;

/**
 *
 * @author trainer
 */
public class RestServiceClass {

    public static final String URL = "http://localhost:8080/api/";

    public List<Person> callServiceWithList() {
        String urlString = URL + "liste"; // URL des WebService
        List<Person> lm = null;
        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

            String output;
            System.out.println("Output from Server .... \n");
            ObjectMapper objectMapper = new ObjectMapper();
            lm = objectMapper.readValue(br.readLine(), new TypeReference<List<Person>>() {
            });

            conn.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return lm;
    }

    public boolean sendService(Person m) {
        String urlString = URL + "create";
        Client c = ClientBuilder.newClient();
        Response response = null;
        try {
            response = c.target(urlString).request(MediaType.APPLICATION_JSON).post(Entity.entity(m, MediaType.APPLICATION_JSON));
        } catch (Exception ex) {
            return false;
        } finally {
            response.close();
            c.close();
        }
        return true;
    }

}
