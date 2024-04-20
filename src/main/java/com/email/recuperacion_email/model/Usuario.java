package com.email.recuperacion_email.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;

    private String nombre;

    @Column(unique = true)
    private String nombreUsuario;

    @Column(unique = true)
    @Email
    private String email;

    private String password;

    @JsonIgnore
    private String tokenPassword;

    @ManyToOne
    @JoinColumn(name = "id_role",foreignKey = @ForeignKey(name = "FK_Usuario_Role"), nullable = false)
    private Role role;


}
