package Mobtestget;

import org.junit.jupiter.api.Assertions;
import org.testng.Assert;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;


import java.util.regex.Matcher;
import java.util.regex.Pattern;


//
//
//



public class Search {
	

	int userid;

    @Test
    public void getRequestforDelphine() {
        Response response = (Response) given()
                .contentType(ContentType.JSON).queryParam("username", "Delphine")
                .when()
                .get("https://jsonplaceholder.typicode.com/users")
                .then()
                .extract().response();

        Assertions.assertEquals(200, response.statusCode());       
        JsonPath jsonPathEvaluator = response.jsonPath();
        userid = jsonPathEvaluator.get("id[0]");
    	System.out.println("id received from Get User " + userid);
        
    }
    @Test
    public void getRequestforPosts() {
    	Response response = (Response) given()
                .contentType(ContentType.JSON).queryParam("userId", userid)
                .when()
                .get("https://jsonplaceholder.typicode.com/posts/")
                .then()
                .extract().response();
        Assertions.assertEquals(200, response.statusCode());
        JsonPath jsonPathEvaluatorforPosts = response.jsonPath();
        //String id = jsonPathEvaluatorforPosts.get("username[8]");
        userid = jsonPathEvaluatorforPosts.get("id[0]");
        System.out.println("user id received from Get Post " + userid);        
        int sizeofPost = jsonPathEvaluatorforPosts.getInt("username.size()");
        System.out.println("total post for Delphine = " + sizeofPost); 
           //Inside loop for checking Posts
        for(int i = 0; i < sizeofPost; i++) {
           String postId = jsonPathEvaluatorforPosts.getString("id["+i+"]");
           Response response1 = (Response) given()
                   .contentType(ContentType.JSON).queryParam("postId", postId)
                   .when()
                   .get("https://jsonplaceholder.typicode.com/comments/")
                   .then()
                   .extract().response();
           //Inside loop for checking comments.               
           JsonPath jsonPathEvaluatoremail = response1.jsonPath();
           int sizeofPostemail = jsonPathEvaluatoremail.getInt("id.size()");
           //count of comments
           System.out.println("comment = " + sizeofPostemail);
           for(int ii = 0; ii < sizeofPostemail; ii++) {     	   
           String email = jsonPathEvaluatoremail.getString("email["+ii+"]");
           System.out.println("email+ " + email);           
           String regex = "^(.+)@(.+)$";         
           Pattern pattern = Pattern.compile(regex);
           Matcher matcher = pattern.matcher(email);
           System.out.println("The Email address " + email + " is " + (matcher.matches() ? "valid" : "invalid"));
                  
           }
        }
        }
           
    
    

}
