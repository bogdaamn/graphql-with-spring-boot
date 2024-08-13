localhost:8080/graphiql - embedded graphql ui
localhost:8080/graphql - hit this one with a POST if use the Altair plugin

if you want to change the mapping for the endpoint, you need to write this in the application.properties

#by default /graphiql
graphiql.mapping=/api-1

#by default /graphql
graphql.servlet.mapping=/api-2

----------------------------------------------------------------------------------------------------
// if there are input objects we need to reach level where the java data-types are presented

1.
input SampleRequest {
    firstName : String
    lastName : String
}
2.
input SampleRequest {
    firstName : String!
    lastName : String
}

Second example does not allow dirstName to be null value


-----------------
@Controller
class BookController {

    @QueryMapping
    public Book bookById(@Argument String id) {
        return Book.getById(id);
    }

    @SchemaMapping
    public Author author(Book book) {
        return Author.getById(book.authorId());
    }

}
The @QueryMapping annotation binds this method to a query, a field under the Query type. The query field is then
determined from the method name, bookById. It could also be declared on the annotation. Spring for GraphQL uses
RuntimeWiring.Builder to register the handler method as a graphql.schema.DataFetcher for the query field bookById.
In GraphQL Java, DataFetchingEnvironment provides access to a map of field-specific argument values. Use the @Argument
annotation to have an argument bound to a target object and injected into the handler method. By default, the method
parameter name is used to look up the argument. The argument name can be specified in the annotation.
The @SchemaMapping annotation maps a handler method to a field in the GraphQL schema and declares it to be the
DataFetcher for that field. The field name defaults to the method name, and the type name defaults to the simple class
name of the source/parent object injected into the method. In this example, the field defaults to author and the type
defaults to Book. The type and field can be specified in the annotation.

For more, see the documentation for the Spring for GraphQL annotated controller feature.
https://www.graphql-java.com/tutorials/getting-started-with-spring-boot/

------------------------------------------------------------------------------------
Over the versions graphQL became more annotation-based, so if you see that classes and resolvers are implementing
interfaces, that means that they are using old versions of graphql.

GraphQLMutationResolver -> @MutationMapping
GraphQLQueryResolver -> @QueryMapping
GraphQLResolver -> @SchemaMapping(typeName - *name of the type to which this resolver needs to be linked*,
                                  field - *filedName on which this resolver needs to be triggered*)