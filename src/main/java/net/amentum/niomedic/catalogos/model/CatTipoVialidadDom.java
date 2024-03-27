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
@Table(name = "cat_tipo_vialidad_dom")
public class CatTipoVialidadDom implements Serializable {
   @Id
   @Column(name = "id_cat_tipo_vialidad")
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Integer idCatTipoVialidad;
   private Integer vialCve;
   @Size(max = 20)
   private String descripcionVialidad;
   private String datosBusqueda;
}
