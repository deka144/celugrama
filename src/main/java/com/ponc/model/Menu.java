package com.ponc.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Data //le crea getter, setter, equals, hashcode y tostring //de Lombok
@AllArgsConstructor //constructor con todos los parametros //de Lombok
@NoArgsConstructor // contructor sin parametros //de Lombok
@Entity //Indica que esta clase sera una tabla en la DB //de JPA
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Menu {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer idMenu;

    @Column(nullable=false, length=20)
    private String name;

    @Column(nullable=false, length=50)
    private String url;

    @Column(nullable=false, length=20)
    private String icon;

    @Column(nullable=false)
    private Boolean state;

    @ManyToMany
    @JoinTable(name="menu_role",
            joinColumns = @JoinColumn(name="id_Menu", referencedColumnName="idMenu"),
            inverseJoinColumns = @JoinColumn(name="id_role", referencedColumnName="idRole"))
    private List<Role> roles;
}
