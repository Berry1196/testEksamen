package entities;


import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor

@Entity
@Table(name = "owner")
public class Owner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name="name")
    private String name;
    @Column(name="address")
    private String address;
    @Column(name="phone")
    private String phone;

    @JoinTable(name = "boat_owner", joinColumns = {
            @JoinColumn(name = "owner_id", referencedColumnName = "id")}, inverseJoinColumns = {
            @JoinColumn(name = "boat_id", referencedColumnName = "boat_id")})
    @ManyToMany
    private List<Boats> boatList = new ArrayList<>();

    public Owner(String name, String address, String phone) {
        this.name = name;
        this.address = address;
        this.phone = phone;
    }

    //Add boat to owner
    public void addBoat(Boats boat){
        boatList.add(boat);
    }
}
