package com.mysbdemos.security_v1_demo.model;

import lombok.*;
import org.hibernate.annotations.NaturalId;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "role", schema = "auth")
public class Role extends BaseEntity implements GrantedAuthority, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "auth.role_id_seq")
    @SequenceGenerator(name = "auth.role_id_seq", allocationSize = 1)
    @Column(unique = true, nullable = false, updatable = false)
    private Long id;

    @NaturalId
    @NotBlank(message = "Role name cannot be blank") // TODO messages props
    @Column(unique = true, nullable = false)
    private String name;

    @Size(max = 255, message = "Description should not be greater than 255")
    private String description;


    @Override
    public String getAuthority() {
        return this.name;
    }
}
