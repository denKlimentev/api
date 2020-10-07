package data.results;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.ArrayList;


@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class ClientsResult {

    private String resultCode;
    private ArrayList<String> clients;

}
