package it.polito.ai.lab2.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Data
public class Student {

    @Id
    private String id;

    private String name;

    private String firstName;

    private String email;

    private String photoPath;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name="student_course", joinColumns = @JoinColumn(name="student"),
            inverseJoinColumns = @JoinColumn(name="course_name"))
    private List<Course> courses;
    {
        courses = new ArrayList<>();
    }

    @ManyToOne
    @JoinColumn(name="team")
    private Team team;

    public int addCourse (Course course) {
        courses.add(course);
        course.getStudents().add(this);
        return courses.indexOf(course);
    }

    @ManyToMany(mappedBy = "owners")
    private List<Vm> vms;
    {
        vms = new ArrayList<>();
    }

    public int addVm(Vm vm) {
        vms.add(vm);
        vm.getOwners().add(this);
        return vms.indexOf(vm);
    }
}
