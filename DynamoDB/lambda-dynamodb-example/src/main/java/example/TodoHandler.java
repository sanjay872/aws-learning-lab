package example;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import software.amazon.awssdk.services.dynamodb.*;
import software.amazon.awssdk.services.dynamodb.model.*;

import java.util.HashMap;
import java.util.Map;

public class TodoHandler implements RequestHandler<Map<String,Object>, Map<String,Object>>{
    private final DynamoDbClient dynamoDb = DynamoDbClient.create();
    private final String TABLE_NAME = "Todos";

    public Map<String,Object> handleRequest(Map<String,Object> event, Context context){
        String id = (String) event.get("id");
        String task = (String) event.get("task");

        Map<String,Object> response = new HashMap<>();

        if (id == null || id.isEmpty()) {
            response.put("statusCode", 400);
            response.put("body", "Missing \"id\"");
            return response;
        }

        if (task != null && !task.isEmpty()) {
            // Put item
            PutItemRequest putReq = PutItemRequest.builder()
                .tableName(TABLE_NAME)
                .item(Map.of(
                    "id", AttributeValue.builder().s(id).build(),
                    "task", AttributeValue.builder().s(task).build()
                ))
                .build();

            dynamoDb.putItem(putReq);

            response.put("statusCode", 200);
            response.put("body", "Item " + id + " added or updated.");
        } else {
            // Get item
            GetItemRequest getReq = GetItemRequest.builder()
                .tableName(TABLE_NAME)
                .key(Map.of(
                    "id", AttributeValue.builder().s(id).build()
                ))
                .build();

            GetItemResponse getResp = dynamoDb.getItem(getReq);

            if (getResp.hasItem()) {
                response.put("statusCode", 200);
                response.put("body", getResp.item().toString());
            } else {
                response.put("statusCode", 404);
                response.put("body", "Item not found.");
            }
        }
        return response;
    }
}