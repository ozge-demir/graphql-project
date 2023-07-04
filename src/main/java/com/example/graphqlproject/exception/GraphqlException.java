package com.example.graphqlproject.exception;

import graphql.ErrorType;
import graphql.GraphQLError;
import graphql.schema.DataFetchingEnvironment;
import org.apache.catalina.User;
import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter;
import org.springframework.stereotype.Component;

@Component
public class GraphqlException extends DataFetcherExceptionResolverAdapter {

    @Override
    protected GraphQLError resolveToSingleError(Throwable ex, DataFetchingEnvironment env) {
        if (ex instanceof UserNotFoundException){
            return toGraphqlError(ex);
        } else if (ex instanceof Exception) {
            return toGraphqlError(ex);
        }
        return super.resolveToSingleError(ex, env);
    }

    private GraphQLError toGraphqlError(Throwable throwable) {
        return GraphQLError.newError()
                .message(throwable.getMessage())
                .errorType(ErrorType.DataFetchingException)
                .build();
    }
}
