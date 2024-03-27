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
@Table(name = "cat_resultado_edi_psp")
public class CatResultadoEdiPSP implements Serializable {

    @Id
    @Column(name = "id_cat_resultado_edi")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_cat_resultado_edi ;

    @Size(max = 100)
    private String descripcion_resultado_edi;

    private Integer clave_edi ;


}
