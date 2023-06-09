package dtos;

import entities.Harbour;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class HarbourDTO {
    private Long id;
    private String name;
    private String address;
    private int capacity;
    private List<BoatsDTO> boatList;

    public HarbourDTO(Harbour harbour) {
        this.id = harbour.getId();
        this.name = harbour.getName();
        this.address = harbour.getAddress();
        this.capacity = harbour.getCapacity();
        this.boatList = BoatsDTO.getDTOs(harbour.getBoat());
    }

    public HarbourDTO(String harbourName, String address, int capacity) {
        this.name = harbourName;
        this.address = address;
        this.capacity = capacity;
    }

    public static List<HarbourDTO> getDTOs(List<Harbour> harbours){
        List<HarbourDTO> harbourDTOs = new ArrayList<>();
        harbours.forEach(harbour -> harbourDTOs.add(new HarbourDTO(harbour)));
        return harbourDTOs;
    }
}
