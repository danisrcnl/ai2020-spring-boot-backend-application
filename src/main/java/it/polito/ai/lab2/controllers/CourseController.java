package it.polito.ai.lab2.controllers;

import it.polito.ai.lab2.dtos.CourseDTO;
import it.polito.ai.lab2.dtos.StudentDTO;
import it.polito.ai.lab2.entities.Course;
import it.polito.ai.lab2.entities.CourseNotFoundException;
import it.polito.ai.lab2.services.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/API/courses")
public class CourseController {

    @Autowired
    TeamService teamService;

    @GetMapping({"", "/"})
    public List<CourseDTO> all() {
        return teamService
                .getAllCourses()
                .stream()
                .map(ModelHelper::enrich)
                .collect(Collectors.toList());
    }

    @GetMapping("/{name}")
    public CourseDTO getOne(@PathVariable String name) throws ResponseStatusException {
        Optional<CourseDTO> course = teamService.getCourse(name);
        if(course.isPresent())
            return ModelHelper.enrich(course.get());
        else
            throw new ResponseStatusException(HttpStatus.CONFLICT, name);
    }

    @GetMapping("/{name}/enrolled")
    public List<StudentDTO> enrolledStudents(@PathVariable String name) throws ResponseStatusException {
        List<StudentDTO> enrolled;
        try {
            enrolled = teamService.getEnrolledStudents(name);
        } catch(CourseNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, name);
        }
        return enrolled;
    }

    @PostMapping({"", "/"})
    public CourseDTO addCourse(@RequestBody CourseDTO courseDTO) throws ResponseStatusException {
        if(teamService.addCourse(courseDTO))
            return ModelHelper.enrich(courseDTO);
        else
            throw new ResponseStatusException(HttpStatus.CONFLICT, courseDTO.getName());
    }

}
