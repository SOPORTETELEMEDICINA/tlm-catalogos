package net.amentum.niomedic.catalogos.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data // @Getter, @Setter, @ToString, @EqualsAndHashCode and @RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cat_subtitulos_cie10")
public class CatSubtitulosCie10 implements Serializable {
   @Id
   @Column(name = "id_cat_subtitulos")
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Integer idCatSubtitulos;
   @Size(max = 10)
   private String codigosSubtitulosCie10;
   @Size(max = 200)
   private String descripcionSubtitulosCie10;
   @Size(max = 200)
   private String aliasSubtitulosCie10;
   private String datosBusqueda;
//   relaciones
   @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
   @JoinColumn(name = "cat_capitulos_id", referencedColumnName = "id_cat_capitulos")
   private CatCapitulosCie10 catCapitulosCie10;

   @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
   @JoinColumn(name = "cat_titulos_id", referencedColumnName = "id_cat_titulos")
   private CatTitulosCie10 catTitulosCie10;

   @Override
   public String toString() {
      return "CatSubtitulosCie10{" +
         "idCatSubtitulos=" + idCatSubtitulos +
         ", codigosSubtitulosCie10='" + codigosSubtitulosCie10 + '\'' +
         ", descripcionSubtitulosCie10='" + descripcionSubtitulosCie10 + '\'' +
         ", aliasSubtitulosCie10='" + aliasSubtitulosCie10 + '\'' +
         ", datosBusqueda='" + datosBusqueda + '\'' +
         '}';
   }
}
