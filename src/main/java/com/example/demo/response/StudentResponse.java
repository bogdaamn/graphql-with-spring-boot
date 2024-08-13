package com.example.demo.response;

import com.example.demo.entity.Student;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class StudentResponse {

    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private String street;
    private String city;
    private List<SubjectResponse> learningSubjects;
    /*
    shouldn't be added to the graphql schema
    This is for internal use
     */
    private Student student;
    /*
     if any business logic is presented - use resolver.
     In older versions, they need to implement GraphQLResolvev<? - class that need a resolver>
     */
    private String fullName;

    public StudentResponse(Student student) {
        this.student = student;
        this.id = student.getId();
        this.firstName = student.getFirstName();
        this.lastName = student.getLastName();
        this.email = student.getEmail();

        this.street = student.getAddress().getStreet();
        this.city = student.getAddress().getCity();
    }

}
