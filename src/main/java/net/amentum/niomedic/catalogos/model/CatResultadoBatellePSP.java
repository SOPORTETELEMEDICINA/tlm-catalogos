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
@Table(name = "cat_resultado_batelle_psp")
public class CatResultadoBatellePSP implements Serializable {
    @Id
    @Column(name = "id_cat_resultado_batelle")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_cat_resultado_batelle ;

    @Size(max = 100)
    private String descripcion_resultado_batelle;

}
