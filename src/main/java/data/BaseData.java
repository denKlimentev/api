package data;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;

import java.io.IOException;
import java.util.Properties;

public class BaseData {
    private static final Properties ACCOUNT_PROPS = new Properties();

    static {
        try {
            ACCOUNT_PROPS.load(BaseData.class.getResourceAsStream("/config.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static final String HOST = ACCOUNT_PROPS.getProperty("localHost");
    public static final int PORT = Integer.parseInt(ACCOUNT_PROPS.getProperty("port"));

    public static final String HOST_LOGIN = "/challenge/login";
    public static final String HOST_LOGOUT = "/challenge/logout";
    public static final String HOST_HELLO = "/challenge/hello";
    public static final String HOST_CLIENTS = "/challenge/clients";
    public static final String X_SESSION_ID = "X-Session-Id";

    public static final int CODE_200 = 200;
    public static final int CODE_500 = 500;
    public static final int CODE_401 = 401;

    public static final String ACCEPT = "accept";
    public static final String OK = "Ok";

    public static final String EMPTY = " */*";


    public static void setRequestSpec() {
        RestAssured.requestSpecification = new RequestSpecBuilder()
                .setBaseUri(HOST)
                .setPort(PORT)
                .log(LogDetail.ALL)
                .build();


      }

}
