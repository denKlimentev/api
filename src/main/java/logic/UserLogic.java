package logic;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import logic.data.results.ClientsResult;
import logic.data.results.MessageResult;
import logic.data.User;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j;

import java.util.UUID;

import static io.restassured.RestAssured.given;
import static logic.BaseData.*;
import static org.hamcrest.core.StringContains.containsString;

@Log4j
public class UserLogic {

    private  ResponseSpecification assert200;

    @Getter
    private String jsonUser;

    @Getter
    private User user;

    public void  precondition(){
        RequestSpecification  requestSpec = new RequestSpecBuilder()
                .setBaseUri(HOST)
                .setPort(PORT)
                .log(LogDetail.ALL)
                .build();

        generateUSerData();


        assert200 = new ResponseSpecBuilder()
                .expectStatusCode(CODE_200)
                .expectBody(containsString(OK))
                .build();

        RestAssured.requestSpecification = requestSpec;
    }

    public void generateUSerData() {

        String userName = UUID.randomUUID().toString();
        log.info("generate userName : " + userName);
        String surname = UUID.randomUUID().toString();
        log.info("generate surname : " + surname);
        user = new User(userName, surname);

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
                    .spec(assert200)
                    .assertThat();
        }
    }

    @SneakyThrows
    public void create(int statusCode, User user) {


        jsonUser = new ObjectMapper().writeValueAsString(user);

        log.info("set post expect status code  : " + statusCode);

        given()
                .body(jsonUser)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post(HOST_CLIENTS)
                .then()
                .statusCode(statusCode)
                .assertThat();

    }

    @SneakyThrows
    public String login() {

        log.info("login");

        String id = given()
                .body(new ObjectMapper().writeValueAsString(user.getUsername()))
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post(HOST_LOGIN)
                .then()
                .spec(assert200)
                .extract()
                .header(X_SESSION_ID);

        log.info(" x-session-id   : " + id);

        return id;


    }

    @SneakyThrows
    public void loginOut() {

        log.info("logout");

        given()
                .body(new ObjectMapper().writeValueAsString(user.getUsername()))
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post(HOST_LOGOUT)
                .then()
                .spec(assert200);

    }

    public MessageResult senMessage(int statusCode, String sessionId) {

        log.info("set message with session id : " + sessionId);

        return
                given()
                        .header(ACCEPT, EMPTY)
                        .contentType(ContentType.JSON)
                        .accept(ContentType.JSON)
                        .header(X_SESSION_ID, sessionId)
                        .when()
                        .get(HOST_HELLO)
                        .then()
                        .statusCode(statusCode)
                        .assertThat()
                        .extract().body().as(MessageResult.class);

    }

    public ClientsResult getUsersList() {
        log.info("Get user list ");
        return
                given()
                        .header(ACCEPT, EMPTY)
                        .when()
                        .get(HOST_CLIENTS)
                        .then()
                        .spec(assert200)
                        .extract().body().as(ClientsResult.class);
    }

}
