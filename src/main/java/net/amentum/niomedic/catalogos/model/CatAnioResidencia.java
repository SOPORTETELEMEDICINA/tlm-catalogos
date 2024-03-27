package net.amentum.niomedic.catalogos.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

@Data // @Getter, @Setter, @ToString, @EqualsAndHashCode and @RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cat_anio_residencia")
public class CatAnioResidencia implements Serializable {
    @Id
    @Column(name = "id_cat_anio_de_residencia")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_cat_anio_de_residencia ;

    @Size(max = 100)
    private String clave_anio_de_residencia;


}
