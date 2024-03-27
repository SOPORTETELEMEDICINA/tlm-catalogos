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
@Table(name = "cat_entidades_dom")
public class CatEntidadesDom implements Serializable {
   @Id
   @Column(name = "id_cat_entidades")
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Integer idCatEntidades;
   private Integer catalogKey;
   @Size(max = 40)
   private String descripcionEntidades;
   @Size(max = 4)
   private String abreviatura;
   private String datosBusqueda;
}
