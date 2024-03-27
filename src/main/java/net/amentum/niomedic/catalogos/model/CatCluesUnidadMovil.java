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
import java.io.Serializable;

@Data // @Getter, @Setter, @ToString, @EqualsAndHashCode and @RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cat_clues_unidad_movil")
public class CatCluesUnidadMovil implements Serializable {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "id_cat_clues_um")
   private Integer idCatCluesUm;
   private Integer idUm;
   private String clues;
   private String nombreTipologia;
   private String unidadMovilMarca;
   private Integer unidadMovilModelo;
   private Integer unidadMovilCapacidad;
   private String unidadMovilPrograma;
   private Integer unidadMovilClavePrograma;
   private String unidadMovilTipo;
   private String unidadMovilClaveTipo;
   private String unidadMovilTipologia;
   private Integer unidadMovilClaveTipologia;

}
