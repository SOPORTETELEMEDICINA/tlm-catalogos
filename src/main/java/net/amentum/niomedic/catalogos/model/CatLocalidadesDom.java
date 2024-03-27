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
@Table(name = "cat_localidades_dom")
public class CatLocalidadesDom implements Serializable {
   @Id
   @Column(name = "id_cat_localidades")
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Integer idCatLocalidades;
   private Integer catalogKey;
   @Size(max = 100)
   private String descripcionLocalidades;
//   @Size(max = 3)
   private Integer efeKey;
//   @Size(max = 4)
   private Integer munKey;
   private String datosBusqueda;
}
