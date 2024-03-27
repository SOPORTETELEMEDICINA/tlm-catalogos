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
@Table(name = "cat_tipo_dificultad")
public class CatTipoDificultad implements Serializable {
    @Id
    @Column(name = "id_cat_tipo_dificultad")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_cat_tipo_dificultad ;

    @Size(max = 100)
    private String descripcion_tipo_dificultad;

}
