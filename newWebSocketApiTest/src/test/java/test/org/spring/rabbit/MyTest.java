package test.org.spring.rabbit;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
 
import org.junit.Test;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriTemplateHandler;
import org.springframework.web.util.UriTemplateHandler;
 
public class MyTest {
 
    @Test
    public void testClient() throws URISyntaxException, IOException {
        System.out.println("testing testClient");
         
        ConnectionFactory connectionFactory = new CachingConnectionFactory("localhost");
 
        AmqpAdmin admin = new RabbitAdmin(connectionFactory);
        admin.declareQueue(new Queue("kkQueue"));
 
        AmqpTemplate template = new RabbitTemplate(connectionFactory);
        template.convertAndSend("kkQueue", "another msg");
         
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory() {
            @Override
            protected void prepareConnection(HttpURLConnection connection, String httpMethod) throws IOException {
                super.prepareConnection(connection, httpMethod);
 
                //Basic Authentication for Police API
                String authorisation = "guest" + ":" + "guest";
 
 
 
 
                //            byte[] encodedAuthorisation = ;
                connection.setRequestProperty("Authorization", "Basic Z3Vlc3Q6Z3Vlc3Q=");
            }
        };
        factory.setBufferRequestBody(false);
        factory.setConnectTimeout(15 * 1000);
        factory.setReadTimeout(15 * 1000);
         
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Content-Type",MediaType.APPLICATION_JSON_VALUE);
         
        RestTemplate restTemplate = new RestTemplate(factory);
 
        List<MediaType> acceptableMediaTypes = new ArrayList<MediaType>();
        acceptableMediaTypes.add(MediaType.APPLICATION_JSON);
         
        Map<String, String> body = new HashMap<String, String>();   
        body.put("count", "5");
        body.put("requeue", "true");
        body.put("encoding", "auto");
        body.put("truncate", "50000");
 
        HttpEntity <?>requestEntity = new HttpEntity<Object>( body,requestHeaders);
        ResponseEntity response = null;
 
        try {
//          DefaultUriTemplateHandler uth = new DefaultUriTemplateHandler();
//          uth.setParsePath(false);
//          uth.shouldParsePath();
//          restTemplate.setUriTemplateHandler(uth);
            response = restTemplate.exchange(new URI(
                    "http://localhost:15672/api/queues/%2F/kkQueue/get"),
                    HttpMethod.POST,
                    requestEntity,
                    String.class);
            System.out.println(response.getBody());
 
        } catch(Throwable t){
 
            System.out.println(t.getMessage());
 
            t.printStackTrace();
        } 
         
         
         
         
//        
//        //ClientHttpResponse response = request.execute();
//        assertEquals("Mismatched response code", HttpStatus.OK.value(), response.getRawStatusCode());
//
//        BufferedReader rdr = new BufferedReader(new InputStreamReader(response.getBody()));
//        try {
//            for (String line = rdr.readLine(); line != null; line = rdr.readLine()) {
//                System.out.println(line);
//            }
//        } finally {
//            rdr.close();
//        }
    }
}