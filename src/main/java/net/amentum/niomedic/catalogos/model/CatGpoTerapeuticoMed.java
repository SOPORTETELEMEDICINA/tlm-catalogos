package net.amentum.niomedic.catalogos.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data // @Getter, @Setter, @ToString, @EqualsAndHashCode and @RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cat_gpo_terapeutico_med")
public class CatGpoTerapeuticoMed implements Serializable {
   @Id
   @Column(name = "id_cat_gpo_terapeutico")
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Integer idCatGpoTerapeutico;
   private Integer medGpoTerapeutico;
   @Size(max = 60)
   private String descripcionGpoTerapeutico;
   @Size(max = 20)
   private String nivelAtencionGpoTerapeutico;
   private String datosBusqueda;
}
