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
@Table(name = "cat_capitulos_cie10")
public class CatCapitulosCie10 implements Serializable {
   @Id
   @Column(name = "id_cat_capitulos")
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Integer idCatCapitulos;
   @Size(max = 10)
   private String codigosCapitulosCie10;
   @Size(max = 150)
   private String descripcionCapitulosCie10;
   private String datosBusqueda;
}
