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
@Table(name = "cat_codigo_postal_dom")
public class CatCodigoPostalDom implements Serializable {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "id_cat_codigo_postal")
   private Long idCatCodigoPostal;
   @Size(max = 6)
   private String codigoPostal;
   @Size(max = 100)
   private String asentamiento;
   @Size(max = 100)
   private String ciudad;
   private Integer idAsentaCpcons;
   @Size(max = 15)
   private String zona;
   @Size(max = 6)
   private String cveCiudad;
   private String datosBusqueda;
//   relaciones
   @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
   @JoinColumn(name = "cat_tipo_asentamiento_id", referencedColumnName = "id_cat_tipo_asentamiento")
   private CatTipoAsentamientoDom catTipoAsentamientoDom; //catTipoAsentamientoId

   @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
   @JoinColumn(name = "cat_municipios_id", referencedColumnName = "id_cat_municipios")
   private CatMunicipiosDom catMunicipiosDom; //catMunicipiosId

   @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
   @JoinColumn(name = "cat_entidades_id", referencedColumnName = "id_cat_entidades")
   private CatEntidadesDom catEntidadesDom; //catEntidadesId

   @Override
   public String toString() {
      return "CatCodigoPostalDom{" +
         "idCatCodigoPostal='" + idCatCodigoPostal + '\'' +
         ", asentamiento='" + asentamiento + '\'' +
         ", ciudad='" + ciudad + '\'' +
         ", idAsentaCpcons=" + idAsentaCpcons +
         ", zona='" + zona + '\'' +
         ", cveCiudad='" + cveCiudad + '\'' +
         ", datosBusqueda='" + datosBusqueda + '\'' +
         '}';
   }
}
