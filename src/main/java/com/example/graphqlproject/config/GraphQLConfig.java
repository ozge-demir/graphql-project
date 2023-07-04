package com.example.graphqlproject.config;

import graphql.scalars.ExtendedScalars;
import graphql.schema.idl.RuntimeWiring;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;

@Configuration
public class GraphQLConfig {

    @Bean
    RuntimeWiringConfigurer runtimeWiringConfigurer() {
        return builder -> builder.scalar(ExtendedScalars.DateTime);
    }
}
