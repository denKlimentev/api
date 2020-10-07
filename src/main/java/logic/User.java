package logic;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import data.results.ClientsResult;
import data.results.MessageResult;
import data.UserData;
import data.results.ResponseResult;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j;

import java.util.UUID;

import static io.restassured.RestAssured.given;
import static data.BaseData.*;

@Log4j
public class User {

    @Getter
    private String jsonUser;

    @Getter
    private UserData user;

    public void precondition() {

        generateUSerData();

    }

    public void generateUSerData() {

        String userName = UUID.randomUUID().toString();
        log.info("generate userName : " + userName);
        String surname = UUID.randomUUID().toString();
        log.info("generate surname : " + surname);
        user = new UserData(userName, surname);

    }

    @SneakyThrows
    public void create(int quantity) {

        for (int i = 0; i < quantity; i++) {

            generateUSerData();

            jsonUser = new ObjectMapper().writeValueAsString(user);

            given()
                    .body(jsonUser)
                    .contentType(ContentType.JSON)
                    .accept(ContentType.JSON)
                    .when()
                    .post(HOST_CLIENTS)
                    .then()
                    .statusCode(CODE_200)
                    .assertThat();
        }
    }


    @SneakyThrows
    public ResponseResult create(UserData user) {

        ResponseResult resultRes = new ResponseResult();
        jsonUser = new ObjectMapper().writeValueAsString(user);

        Response result;

        result =  given()
                .body(jsonUser)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post(HOST_CLIENTS)
                .then().extract().response();

    resultRes.setBody(String.valueOf(result.getBody()));
    resultRes.setResultCode(result.getStatusCode());

    return resultRes;
    }

    @SneakyThrows
    public ResponseResult login() {

        ResponseResult resultRes = new ResponseResult();

        log.info("login");
        Response result;
        result = given()
                .body(new ObjectMapper().writeValueAsString(user.getUsername()))
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post(HOST_LOGIN)
                .then().extract().response();

        log.info(" x-session-id   : " + result.header(X_SESSION_ID));


        resultRes.setBody(String.valueOf(result.getBody()));
        resultRes.setResultCode(result.getStatusCode());
        resultRes.setHeader(result.header(X_SESSION_ID));
        return resultRes;


    }

    @SneakyThrows
    public int loginOut() {

        log.info("logout");

      return   given()
                .body(new ObjectMapper().writeValueAsString(user.getUsername()))
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post(HOST_LOGOUT)
                .then().extract().statusCode();

    }

    public MessageResult sendMessage(String sessionId) {

        log.info("set message with session id : " + sessionId);

       return given()
                .header(ACCEPT, EMPTY)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header(X_SESSION_ID, sessionId)
                .when()
                .get(HOST_HELLO)
                .then()
                .extract().body().as(MessageResult.class);

    }

    public ClientsResult getUsersList() {
        log.info("Get user list ");

         return   given()
                        .header(ACCEPT, EMPTY)
                        .when()
                        .get(HOST_CLIENTS)
                        .then()
                        .extract().body().as(ClientsResult.class);
    }

}
