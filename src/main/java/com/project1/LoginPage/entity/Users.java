package com.project1.LoginPage.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "Users")
@Data
@NoArgsConstructor
public class Users {
    @Id
    private ObjectId id;
    @Indexed(unique = true)
    private String userName;
    private String password;

    @NonNull
    @Indexed(unique = true)
    private String email;
    private List<String> roles;
}
