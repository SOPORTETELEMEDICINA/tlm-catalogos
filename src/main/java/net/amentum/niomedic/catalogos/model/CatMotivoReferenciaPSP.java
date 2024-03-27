package net.amentum.niomedic.catalogos.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Data // @Getter, @Setter, @ToString, @EqualsAndHashCode and @RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cat_motivo_de_referencia_psp")
public class CatMotivoReferenciaPSP {
    @Id
    @Column(name = "id_cat_motivo_de_referencia")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_cat_motivo_de_referencia ;

    @Size(max = 100)
    private String descripcion_motivo_de_referencia;
}
