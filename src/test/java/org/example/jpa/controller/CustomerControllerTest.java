package org.example.jpa.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.jpa.model.Customer;
import org.example.jpa.repository.CustomerRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import javax.xml.transform.Result;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.web.servlet.function.RequestPredicates.contentType;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc(addFilters = false) // addFilter = false to disable security filter for unit test.
class CustomerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private static final String API_ENDPOINT = "/api/customers";

    private Customer customer1, customer2;

    private List<Customer> customerList = new ArrayList<>();

    // If List is to ArrayList
    // Then Map is to HashMap
    // Then Set is to HashSet

    // Run before each JUNIT test operation
    @BeforeEach
    void setUp() {

        // Delete all records in the database before starting
        customerRepository.deleteAll();

        // arrange (precondition)
        customer1 = Customer.builder()
                .name("Customer X")
                .email("customer_x@gmail.com")
                .phone("91234567")
                .build();

        // arrange (precondition)
        customer2 = Customer.builder()
                .name("Customer Y")
                .email("customer_y@gmail.com")
                .phone("98765432")
                .build();

        customerList.add(customer1);
        customerList.add(customer2);
    }

    @AfterEach
    void tearDown() {
    }




    @Test
    @DisplayName("** JUNIT test: get all customers from Customer Ctrl.")
    void allCustomers() throws Exception{


        // triple As (arrange, act, assert)
        // arrange - setup precondition
        customerRepository.saveAll(customerList);

        // act - action or behaviour to test
        ResultActions resultActions = mockMvc.perform(get(API_ENDPOINT));

        // assert - verify the output (as expected)
        resultActions.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()", is(customerList.size())));
    }

    @Test
    @DisplayName("** JUNIT test: get customer by Id")
    void customer() throws Exception{

        // arrange - setup precondition
        customerRepository.save(customer1);
        // refer to setup

        // act - action or behaviour to test (save the customer)
        ResultActions resultActions = mockMvc.perform(get(API_ENDPOINT.concat("/{id}"), customer1.getId()));;

        // assert - verify the output that the customer exists
        resultActions.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.name").value(customer1.getName()))
                .andExpect(jsonPath("$.email").value(customer1.getEmail()))
                .andExpect((jsonPath("$.phone").value(customer1.getPhone())));
    }

    @Test
    void saveCustomer() throws Exception {

        // arrange - setup precondition
        String requestBody = objectMapper.writeValueAsString(customer1);

        // act - action or behaviour to test
        ResultActions resultActions = mockMvc.perform(post(API_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody));

        // assert - verify the output (as expected)
        resultActions.andExpect(status().isCreated())
                .andDo(print())
                .andExpect(jsonPath("$.name").value(customer1.getName()))
                .andExpect(jsonPath("$.email").value(customer1.getEmail()))
                .andExpect(jsonPath("$.phone").value(customer1.getPhone()))
                .andExpect(result-> assertNotNull(result.getResponse().getContentAsString()))
                .andExpect(result -> assertTrue(result.getResponse().getContentAsString().contains(customer1.getEmail())));
    }

    @Test
    @DisplayName("** JUnit test: update customer from Customer Ctrl. **")
    void updateCustomer() throws Exception {

        // arrange - setup precondition
        customerRepository.save(customer1);

        Customer updateCustomer1 = customerRepository.findById(customer1.getId()).get();

        updateCustomer1.setName("Updated Customer X");
        updateCustomer1.setPhone("92234567");
        updateCustomer1.setEmail("updated_customer_x@gmail.com");

        String requestBody = objectMapper.writeValueAsString(updateCustomer1);

        // act -  action or behaviour to test
        ResultActions resultActions = mockMvc.perform(put(API_ENDPOINT.concat("/{id}"), updateCustomer1.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody));

        // assert - verify the output
        resultActions.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.name").value(updateCustomer1.getName()))
                .andExpect(jsonPath("$.phone").value(updateCustomer1.getPhone()))
                .andExpect(jsonPath("$.email").value(updateCustomer1.getEmail()));
    }

    @Test
    void deleteCustomer() throws Exception {

        // arrange
        customerRepository.save(customer1);

        Customer deleteCustomer1 = customerRepository.findById(customer1.getId()).get();

        String expectedResponse = String.format("%s deleted successfully", deleteCustomer1.getName());

        // act
        ResultActions resultActions = mockMvc.perform(delete(API_ENDPOINT.concat("/{id}"), deleteCustomer1.getId())
                .contentType(MediaType.APPLICATION_JSON));

        // assert
        resultActions.andExpect(status().isOk())
                .andDo(print())
                // Check that the body response matches the expected message
                .andExpect(result -> assertEquals(expectedResponse, result.getResponse().getContentAsString()));

    }

        @Test
        void countCustomer() throws Exception {
            customerRepository.saveAll(customerList);
            long count = customerRepository.count();

            ResultActions resultActions = mockMvc.perform(get(API_ENDPOINT.concat("/count"))
                    .contentType(MediaType.APPLICATION_JSON));

            resultActions.andExpect(status().isOk())
                    .andDo(print())
                    .andExpect(jsonPath("$.total").value(count));
        }

}