package dtos;

import entities.Owner;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString

public class OwnerDTO {
    private Long id;
    private String name;
    private String address;
    private String phone;


    public OwnerDTO(Owner owner) {
        this.id = owner.getId();
        this.name = owner.getName();
        this.address = owner.getAddress();
        this.phone = owner.getPhone();
    }

    public OwnerDTO(Long id,String name, String address, String phone) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone = phone;
    }



    public static List<OwnerDTO> getDTOs(List<Owner> owners){
        List<OwnerDTO> ownerDTOs = new ArrayList<>();
        owners.forEach(owner -> ownerDTOs.add(new OwnerDTO(owner)));
        return ownerDTOs;
    }

}
