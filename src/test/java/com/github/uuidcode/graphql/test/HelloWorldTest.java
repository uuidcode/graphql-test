package com.github.uuidcode.graphql.test;

import static org.junit.Assert.*;

import java.util.stream.Stream;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.StaticDataFetcher;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;

public class HelloWorldTest {
    protected static Logger logger = LoggerFactory.getLogger(HelloWorldTest.class);

    @Test
    public void test() {
        String schema = CoreUtil.getContentFromResource("hello.schema");

        if (logger.isDebugEnabled()) {
            logger.debug(">>> test schema: \n{}", schema);
        }

        SchemaParser schemaParser = new SchemaParser();
        TypeDefinitionRegistry typeDefinitionRegistry = schemaParser.parse(schema);

        RuntimeWiring runtimeWiring = RuntimeWiring.newRuntimeWiring()
            .type("Query", builder -> builder.dataFetcher("hello", new StaticDataFetcher("world")))
            .build();

        SchemaGenerator schemaGenerator = new SchemaGenerator();
        GraphQLSchema graphQLSchema = schemaGenerator.makeExecutableSchema(typeDefinitionRegistry, runtimeWiring);

        GraphQL graphQL = GraphQL.newGraphQL(graphQLSchema).build();
        ExecutionResult executionResult = graphQL.execute("{hello}");

        String result = executionResult.getData().toString();

        if (logger.isDebugEnabled()) {
            logger.debug(">>> test result: {}", CoreUtil.toJson(result));
        }
    }
}