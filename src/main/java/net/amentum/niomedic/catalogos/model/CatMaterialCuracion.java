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
@Table(name = "cat_material_curacion")
public class CatMaterialCuracion implements Serializable {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "id_mc")
   private Integer idMc;
   private Integer cap;
   private Integer tit;
   private Integer grupo;
   @Size(max =15)
   private String mcClave;
   @Column(columnDefinition = "TEXT")
   private String mcNombreEspecifico;
//   @Size(max =80)
   @Column(columnDefinition = "TEXT")
   private String mcEspecialidadServicio;
   @Size(max =340)
   private String funcion;

}
