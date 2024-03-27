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
@Table(name = "cat_titulos_cie10")
public class CatTitulosCie10 implements Serializable {
   @Id
   @Column(name = "id_cat_titulos")
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Integer idCatTitulos;
   @Size(max = 15)
   private String codigosTitulosCie10;
   @Size(max = 120)
   private String descripcionTitulosCie10;
   @Size(max = 150)
   private String aliasTitulosCie10;
   private String datosBusqueda;
//   relaciones
   @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
   @JoinColumn(name = "cat_capitulos_id", referencedColumnName = "id_cat_capitulos")
   private CatCapitulosCie10 catCapitulosCie10;

   @Override
   public String toString() {
      return "CatTitulosCie10{" +
         "idCatTitulos=" + idCatTitulos +
         ", codigosTitulosCie10='" + codigosTitulosCie10 + '\'' +
         ", descripcionTitulosCie10='" + descripcionTitulosCie10 + '\'' +
         ", aliasTitulosCie10='" + aliasTitulosCie10 + '\'' +
         ", datosBusqueda='" + datosBusqueda + '\'' +
         '}';
   }
}
