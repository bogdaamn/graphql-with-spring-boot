package com.example.demo.query;

import com.example.demo.entity.Subject;
import com.example.demo.request.CreateStudentRequest;
import com.example.demo.request.SampleRequestInput;
import com.example.demo.response.StudentResponse;
import com.example.demo.response.SubjectResponse;
import com.example.demo.service.StudentService;
import com.example.demo.type.SubjectNameFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class QueryController {

    private final StudentService studentService;

    @QueryMapping
    public String firstQuery() {
        return "First Query";
    }

    @QueryMapping
    public String secondQuery() {
        return "Second Query";
    }

    @QueryMapping
    public String fullName(@Argument SampleRequestInput sampleRequest) {
        return sampleRequest.getFirstName() + " " + sampleRequest.getLastName();
    }

    @QueryMapping
    public StudentResponse student(@Argument long studentId) {
        return new StudentResponse(studentService.getStudentById(studentId));
    }

    @MutationMapping
    public StudentResponse createStudent(@Argument("createStudentRequest") CreateStudentRequest createStudentRequest){
        return new StudentResponse(studentService.createStudent(createStudentRequest));
    }

    /*
    NOT working when was in the separate class like in the previous versions! Must be in the
    same class as all over resolvers

    Triggered only when we request the learningSubjects. In other case this method is ignored
     */
    @SchemaMapping(typeName = "StudentResponse", field = "learningSubjects")
    public List<SubjectResponse> getLearningSubjects(StudentResponse studentResponse,
                                                     @Argument("subjectNameFilters") List<SubjectNameFilter> subjectNameFilters) {
        List<SubjectResponse> learningSubjects = new ArrayList<>();

        if (studentResponse.getStudent().getLearningSubjects() != null) {

            for (Subject subject : studentResponse.getStudent().getLearningSubjects()) {
                if (subjectNameFilters.contains(SubjectNameFilter.All)) {
                    learningSubjects.add(new SubjectResponse(subject));

                } else if ((subjectNameFilters.contains(SubjectNameFilter.Java) &&
                            subject.getSubjectName().equalsIgnoreCase(SubjectNameFilter.Java.name()))
                           ||
                           (subjectNameFilters.contains(SubjectNameFilter.MySQL) &&
                            subject.getSubjectName().equalsIgnoreCase(SubjectNameFilter.MySQL.name()))
                           ||
                           (subjectNameFilters.contains(SubjectNameFilter.MongoDB) &&
                            subject.getSubjectName().equalsIgnoreCase(SubjectNameFilter.MongoDB.name()))) {

                    learningSubjects.add(new SubjectResponse(subject));

                }
            }
        }

        return learningSubjects;
    }

    @SchemaMapping(typeName = "StudentResponse", field = "fullName")
    public String getFullName(StudentResponse studentResponse) {
        return studentResponse.getFirstName() + " " + studentResponse.getLastName();

    }
}