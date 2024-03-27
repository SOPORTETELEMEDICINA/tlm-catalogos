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
@Table(name = "cat_municipios_dom")
public class CatMunicipiosDom implements Serializable {
   @Id
   @Column(name = "id_cat_municipios")
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Integer idCatMunicipios;
   private Integer cveMunicipio;
   private String descripcionMunicipios;
   private Integer efeKey;
   private String datosBusqueda;
}
