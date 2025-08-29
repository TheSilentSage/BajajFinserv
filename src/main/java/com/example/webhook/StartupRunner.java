package com.example.webhook;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import java.util.*;

@Component
public class StartupRunner implements ApplicationRunner {
    
    @Autowired
    private RestTemplate restTemplate;
    
    @Override
    public void run(ApplicationArguments args) {
        try {
            String url = "https://bfhldevapigw.healthrx.co.in/hiring/generateWebhook/JAVA";
            Map<String, String> requestBody = new HashMap<>();
            requestBody.put("name", "John Doe");
            requestBody.put("regNo", "REG12347");
            requestBody.put("email", "john@example.com");
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Map<String, String>> entity = new HttpEntity<>(requestBody, headers);
            ResponseEntity<Map> response = restTemplate.postForEntity(url, entity, Map.class);
            System.out.println("Webhook generation response: " + response.getStatusCode() + " - " + response.getBody());
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                String webhook = (String) response.getBody().get("webhook");
                String accessToken = (String) response.getBody().get("accessToken");
                System.out.println("Webhook URL: " + webhook);
                System.out.println("Access Token: " + accessToken);
                
                // Prepare SQL solution
                String finalQuery = "SELECT e1.EMP_ID, e1.FIRST_NAME, e1.LAST_NAME, d.DEPARTMENT_NAME, COUNT(e2.EMP_ID) AS YOUNGER_EMPLOYEES_COUNT FROM EMPLOYEE e1 JOIN DEPARTMENT d ON d.DEPARTMENT_ID = e1.DEPARTMENT LEFT JOIN EMPLOYEE e2 ON e2.DEPARTMENT = e1.DEPARTMENT AND e2.DOB > e1.DOB GROUP BY e1.EMP_ID, e1.FIRST_NAME, e1.LAST_NAME, d.DEPARTMENT_NAME ORDER BY e1.EMP_ID DESC;";
                Map<String, String> answerBody = new HashMap<>();
                answerBody.put("finalQuery", finalQuery);
                
                // Use the standard test webhook URL as specified in requirements
                String testWebhookUrl = "https://bfhldevapigw.healthrx.co.in/hiring/testWebhook/JAVA";
                
                // Use only the working JWT format (without "Bearer " prefix)
                String authFormat = accessToken;
                
                try {
                    HttpHeaders answerHeaders = new HttpHeaders();
                    answerHeaders.setContentType(MediaType.APPLICATION_JSON);
                    answerHeaders.set("Authorization", authFormat);
                    
                    // Add additional headers that might be required
                    answerHeaders.set("Accept", "application/json");
                    answerHeaders.set("User-Agent", "SpringBoot-Webhook-Solver/1.0");
                    
                    System.out.println("Submitting answer to webhook: " + testWebhookUrl);
                    System.out.println("Request body: " + answerBody);
                    System.out.println("Authorization header: " + authFormat);
                    
                    HttpEntity<Map<String, String>> answerEntity = new HttpEntity<>(answerBody, answerHeaders);
                    ResponseEntity<String> answerResponse = restTemplate.postForEntity(testWebhookUrl, answerEntity, String.class);
                    
                    System.out.println("=== Submission successful! ===");
                    System.out.println("Response status: " + answerResponse.getStatusCode());
                    System.out.println("Response body: " + answerResponse.getBody());
                    
                    // Terminate the application after successful submission
                    System.out.println("Application terminating after successful submission...");
                    System.exit(0);
                    
                } catch (Exception ex) {
                    System.err.println("=== Submission failed ===");
                    System.err.println("Error: " + ex.getMessage());
                    ex.printStackTrace();
                    System.exit(1);
                }
                
            } else {
                System.err.println("Failed to generate webhook: " + response.getStatusCode() + " - " + response.getBody());
                System.exit(1);
            }
        } catch (Exception e) {
            System.err.println("Exception in startup flow: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }
}
