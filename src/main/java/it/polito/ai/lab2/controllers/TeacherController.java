package it.polito.ai.lab2.controllers;


import it.polito.ai.lab2.dtos.CourseDTO;
import it.polito.ai.lab2.dtos.StudentDTO;
import it.polito.ai.lab2.dtos.TeacherDTO;
import it.polito.ai.lab2.services.AiException;
import it.polito.ai.lab2.services.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/API/teachers")
public class TeacherController {

    @Autowired
    TeamService teamService;


    @GetMapping({"", "/"})
    public List<TeacherDTO> all () {
        return teamService
                .getAllTeachers()
                .stream()
                .map(ModelHelper::enrich)
                .collect(Collectors.toList());
    }

    //@PreAuthorize("hasRole('USER')")
    @GetMapping("/{id}")
    public TeacherDTO getOne (@PathVariable String id) throws ResponseStatusException {
        Optional<TeacherDTO> teacher = teamService.getTeacher(id);
        if(teacher.isPresent())
            return ModelHelper.enrich(teacher.get());
        else
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Couldn't find teacher with id: " + id);
    }

    @GetMapping("/{id}/getCourses")
    public List<CourseDTO> getTeacherCourses (@PathVariable String id) throws ResponseStatusException {
        List<CourseDTO> teacherCourses;
        try {
            teacherCourses = teamService.getCoursesForTeacher(id);
        } catch (AiException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getErrorMessage());
        }
        return teacherCourses
                .stream()
                .map(ModelHelper :: enrich)
                .collect(Collectors.toList());
    }

}