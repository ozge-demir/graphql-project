package com.example.graphqlproject.controller;

import com.example.graphqlproject.model.Role;
import com.example.graphqlproject.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureGraphQlTester;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.graphql.test.tester.GraphQlTester;
import org.springframework.test.annotation.DirtiesContext;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@AutoConfigureGraphQlTester
class UserControllerTest {

    @Autowired
    GraphQlTester graphQlTester;

    @BeforeEach
    void setUp() {
        createUser(new User("ozge", "ozge@mail.com", Role.ADMIN));
        createUser(new User("missy", "missy@mail.com", Role.USER));
        createUser(new User("frida", "frida@mail.com", Role.USER));
    }

    @Test
    void when_getAllUsers_should_return_userList() {

        // language=graphql
        String query = """
                {
                	getAllUsers{
                    id
                    username
                    role
                    created
                    updated
                  }
                }
                """;

        graphQlTester.document(query)
                .execute()
                .path("getAllUsers")
                .entityList(User.class).hasSize(3);
    }

    @Test
    void when_createUser_should_createNewUserAndReturnUser() {
        String mutation =
                """
                mutation{
                  createUser(userRequest:{username: "ozge",mail:"mail@me.com",role:ADMIN}) {
                    id
                    username
                    role
                    created
                    updated
                  }
                }
                """;

        graphQlTester.document(mutation)
                .execute()
                .path("createUser")
                .entity(User.class);
    }

    @Test
    void when_updateUser_should_updateUserAndReturnUser() {
        String mutation = """
                updateUser(userRequest:{username: "ozge",mail:"mail_OZGE@me.com",role:USER}) {
                    id
                    username
                    role
                    created
                    updated
                  }
                }
                """;
    }

    @Test
    void when_deleteUser_should_deleteUserAndReturnTrue() {
        String mutation = """
                deleteUser(id:1)
                """;
    }

    void createUser(User user){
        String mutation = """
                mutation{
                  createUser(userRequest:{username: "%s",mail:"%s",role:%s}) {
                    id
                    username
                    role
                    created
                    updated
                  }
                }
                """.formatted(user.getUsername(), user.getMail(), user.getRole());

        graphQlTester.document(mutation).execute();
    }
}