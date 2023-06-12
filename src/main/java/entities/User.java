package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.mindrot.jbcrypt.BCrypt;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "user_name", length = 25)
    private String user_name;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "user_pass")
    private String userPass;
    @Column(name = "address")
    private String address;
    @Column(name = "phone")
    private String phone;
    @Column(name = "email", length = 100)
    private String email;
    @Column(name = "birth_year")
    private String birthYear;
    @Column (name = "gender")
    private String gender;

    @JoinTable(name = "user_roles", joinColumns = {
            @JoinColumn(name = "user_name", referencedColumnName = "user_name")}, inverseJoinColumns = {
            @JoinColumn(name = "role_name", referencedColumnName = "role_name")})

    @ManyToMany
    private List<Role> roleList = new ArrayList<>();

   @JoinTable(name = "user_trips", joinColumns = {
            @JoinColumn(name = "user_id", referencedColumnName = "id")}, inverseJoinColumns = {
            @JoinColumn(name = "trip_id", referencedColumnName = "id")})
    @ManyToMany
    private List<Trip> tripList = new ArrayList<>();

    public User(String user_name, String user_pass, String address, String phone, String email, String birthYear, String gender) {
        this.user_name = user_name;
        this.userPass = BCrypt.hashpw(user_pass, BCrypt.gensalt());
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.birthYear = birthYear;
        this.gender = gender;
    }

    public List<String> getRolesAsStrings() {
        if (roleList.isEmpty()) {
            return null;
        }
        List<String> rolesAsStrings = new ArrayList<>();
        roleList.forEach((role) -> {
            rolesAsStrings.add(role.getRoleName());
        });
        return rolesAsStrings;
    }

    //TODO Change when password is hashed
    public boolean verifyPassword(String pw) {
        return BCrypt.checkpw(pw, userPass);
    }

    public User(String user_name, String userPass) {
        this.user_name = user_name;
        this.userPass = BCrypt.hashpw(userPass, BCrypt.gensalt());
    }


    public void setUserPass(String userPass) {
        this.userPass = BCrypt.hashpw(userPass, BCrypt.gensalt());
        ;
    }


    public void addRole(Role userRole) {
        roleList.add(userRole);
    }

}
