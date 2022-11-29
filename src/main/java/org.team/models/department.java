pack org.team.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@Entity(name = "departmentEntity")
@Table(name = "department")
public class department implements Serializable {

    private Long id;

    @NotNull
    @Pattern( regexp = "[A-Z][a-z]{3,30}" )
    private String name;

    public department(){}

    public department(String name){
        this.name = name;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    @Column(name = "name")
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    @Override
    public String toString() {
        return "department{" +
                "id=" + id +
                ", department='" + name + '\'' +
                '}';
    }
}