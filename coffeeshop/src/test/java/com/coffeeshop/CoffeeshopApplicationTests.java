package com.coffeeshop;
import static org.assertj.core.api.Assertions.assertThat;
import com.coffeeshop.Controller.IndexController;
import com.coffeeshop.EntityClasses.UserEntity;
import com.coffeeshop.Model.BaseRestResponse;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
class CoffeeshopApplicationTests {
    @Autowired
    IndexController indexController;
    
    @LocalServerPort
    int randomServerPort;
    
    @Test
    public void ChekTest(){
        final ResponseEntity<String> result = indexController.Test();
        Gson g = new Gson();
        BaseRestResponse response = g.fromJson(result.getBody(), BaseRestResponse.class);
        assertThat(result.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getMessage()).isEqualTo("1.0.0");
        assertThat(response.getError()).isEqualTo(false);
    }
    @Test
    public void Authenticate(){
        UserEntity userEntity=new UserEntity();
        userEntity.setUserName("owner");
        userEntity.setPassword("123");
        final ResponseEntity<String> result = indexController.Authenticate(userEntity);
        System.out.println(result.toString());
        Gson g = new Gson();
        BaseRestResponse response = g.fromJson(result.getBody(), BaseRestResponse.class);
        assertThat(result.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getError()).isEqualTo(false);
        UserEntity userEntity1 = g.fromJson(response.getData().toString(), UserEntity.class);
        System.out.println(userEntity1.getToken());

    }
    
}
