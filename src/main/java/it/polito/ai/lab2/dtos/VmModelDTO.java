package it.polito.ai.lab2.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class VmModelDTO extends RepresentationModel<VmModelDTO> {

    private String id;
    private int nVCpu;
    private int disk;
    private int ram;

}
