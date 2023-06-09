package entities;

import dtos.HarbourDTO;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "boats")
public class Boats {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "boat_id")
    private Long id;
    @Column(name="brand")
    private String brand;
    @Column(name="make")
    private String make;
    @Column(name="image")
    private String image;

    @ManyToMany(mappedBy = "boatList")
    private List<Owner> owner = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "harbour_id")
    private Harbour harbour;


    public Boats(String brand, String make, String image) {
        this.brand = brand;
        this.make = make;
        this.image = image;
    }

    public void addOwner(Owner owner){
        this.owner.add(owner);
        owner.getBoatList().add(this);
    }

}
