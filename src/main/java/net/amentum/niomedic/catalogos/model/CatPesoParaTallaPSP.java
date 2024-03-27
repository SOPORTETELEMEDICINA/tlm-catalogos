package net.amentum.niomedic.catalogos.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data // @Getter, @Setter, @ToString, @EqualsAndHashCode and @RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cat_peso_para_talla_psp")
public class CatPesoParaTallaPSP implements Serializable {

    @Id
    @Column(name = "id_cat_peso_para_talla")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_cat_peso_para_talla ;

    @Size(max = 100)
    private String descripcion_peso_para_talla;

}
