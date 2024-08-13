package com.example.demo.service;

import com.example.demo.request.CreateStudentRequest;
import com.example.demo.response.StudentResponse;
import graphql.kickstart.spring.webclient.boot.GraphQLRequest;
import graphql.kickstart.spring.webclient.boot.GraphQLResponse;
import graphql.kickstart.spring.webclient.boot.GraphQLWebClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final GraphQLWebClient graphQLWebClient;

    public StudentResponse createStudent(CreateStudentRequest createStudentRequest){
        Map<String, Object> variables = new HashMap<>();
        variables.put("createStudentRequest", createStudentRequest);

        String mutationRequest = "mutation createStudent ($createStudentRequest : CreateStudentRequest){\n"
                                 + "  createStudent(createStudentRequest: $createStudentRequest){\n"
                                 + "    id\n"
                                 + "    firstName\n"
                                 + "    lastName\n"
                                 + "    fullName\n"
                                 + "    learningSubjects(subjectNameFilters : [All]) {\n"
                                 + "      id\n"
                                 + "      subjectName\n"
                                 + "      marksObtained\n"
                                 + "    }\n"
                                 + "  }\n"
                                 + "}";

        GraphQLRequest graphQLRequest = GraphQLRequest.builder()
                .query(mutationRequest)
                .variables(variables)
                .build();

        GraphQLResponse response = graphQLWebClient.post(graphQLRequest).block();

        return response.get("student", StudentResponse.class);
    }

    public StudentResponse getStudent(Integer id) {

        //will be provided during request build process
        Map<String, Object> variables = new HashMap<>();
        variables.put("id", id);

        String query = "query getStudent ($id : ID, $subName : SubjectNameFilter) {\n"
                       + "  student(studentId: $id) {\n"
                       + "    id\n"
                       + "    firstName\n"
                       + "    lastName\n"
                       + "    fullName\n"
                       + "    learningSubjects (subjectNameFilters: [ $subName ]){\n"
                       + "      id\n"
                       + "      subjectName\n"
                       + "      marksObtained\n"
                       + "    } \n"
                       + "  }\n"
                       + "}\n";

        GraphQLRequest graphQLRequest = GraphQLRequest.builder()
                .query(query)
                //using the map of variables that we have prepared before
                .variables(variables)
                .build();

        GraphQLResponse response = graphQLWebClient.post(graphQLRequest).block();

        return response.get("student", StudentResponse.class);
    }
}