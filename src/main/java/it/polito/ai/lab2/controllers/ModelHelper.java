package it.polito.ai.lab2.controllers;

import it.polito.ai.lab2.dtos.*;
import org.springframework.hateoas.Link;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

public class ModelHelper {

    public static CourseDTO enrich(CourseDTO courseDTO) {
        Link self = linkTo(methodOn(CourseController.class).getOne(courseDTO.getName())).withSelfRel();
        Link enrolled = linkTo(methodOn(CourseController.class).enrolledStudents(courseDTO.getName())).withRel("enrolled");
        courseDTO.add(self);
        courseDTO.add(enrolled);
        return courseDTO;
    }

    public static StudentDTO enrich(StudentDTO studentDTO) {
        Link link = linkTo(methodOn(StudentController.class).getOne(studentDTO.getId())).withSelfRel();
        studentDTO.add(link);
        return studentDTO;
    }

    public static TeacherDTO enrich(TeacherDTO teacherDTO) {
        Link link = linkTo(methodOn(TeacherController.class).getOne(teacherDTO.getId())).withSelfRel();
        teacherDTO.add(link);
        return teacherDTO;
    }

    public static TeamDTO enrich(TeamDTO teamDTO) {
        Link link = linkTo(methodOn(TeamController.class).getOne(teamDTO.getId())).withSelfRel();
        teamDTO.add(link);
        return teamDTO;
    }

    public static VmDTO enrich(VmDTO vmDTO) {
        Link link = linkTo(methodOn(VmController.class).getOne(vmDTO.getId())).withSelfRel();
        vmDTO.add(link);
        return vmDTO;
    }

    public static VmModelDTO enrich(VmModelDTO vmModelDTO) {
        Link link = linkTo(methodOn(VmController.class).getVmModel(vmModelDTO.getId())).withSelfRel();
        vmModelDTO.add(link);
        return vmModelDTO;
    }
}
