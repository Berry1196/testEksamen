package dtos;

import entities.Boats;
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
public class BoatsDTO {
    private Long id;
    private String brand;
    private String make;
    private String image;
    private String harbour;
    private List<OwnerDTO> owner = new ArrayList<>();
    private Long harbour_id;


    public BoatsDTO(Boats boats) {
        this.id = boats.getId();
        this.brand = boats.getBrand();
        this.make = boats.getMake();
        this.image = boats.getImage();
        if (boats.getHarbour() != null) {
            this.harbour = boats.getHarbour().getName();
        }
        this.owner = OwnerDTO.getDTOs(boats.getOwner());
    }

    public BoatsDTO(Long id,String brand, String make, String image) {
        this.id = id;
        this.brand = brand;
        this.make = make;
        this.image = image;
    }

    public static List<BoatsDTO> getDTOs(List<Boats> boats){
        List<BoatsDTO> boatsDTOs = new ArrayList<>();
        boats.forEach(boat -> boatsDTOs.add(new BoatsDTO(boat)));
        return boatsDTOs;
    }


}
