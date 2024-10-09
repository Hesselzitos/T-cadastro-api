package com.bank.repository;

import com.bank.model.User;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

@Repository
public class UserRepository {

    private final DynamoDbTable<User> userTable;

    public UserRepository(DynamoDbEnhancedClient dynamoDbEnhancedClient) {
        this.userTable = dynamoDbEnhancedClient.table("Users", TableSchema.fromBean(User.class));
    }

    public void saveUser(User user) {
        userTable.putItem(user);
    }

    public User getUserByDocumentNumber(String documentNumber) {
        return userTable.getItem(r -> r.key(k -> k.partitionValue(documentNumber)));
    }
}
