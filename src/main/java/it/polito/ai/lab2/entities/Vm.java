package it.polito.ai.lab2.entities;

import it.polito.ai.lab2.dataStructures.VmStatus;
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
public class Vm {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;

    private VmStatus currentStatus;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name="vm_owner", joinColumns = @JoinColumn(name="vm_id"),
            inverseJoinColumns = @JoinColumn(name="owner_id"))
    private List<Student> owners;
    {
        owners = new ArrayList<>();
    }

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "vmModel_id")
    private VmModel vmModel;

    public int addOwner(Student student) {
        owners.add(student);
        student.getVms().add(this);
        return owners.indexOf(student);
    }

}
