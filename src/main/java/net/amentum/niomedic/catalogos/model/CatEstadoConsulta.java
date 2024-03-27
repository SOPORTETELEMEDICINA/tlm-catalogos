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
@Table(name = "cat_estado_consulta")
public class CatEstadoConsulta implements Serializable {
   @Id
   @Column(name = "id_estado_consulta")
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Integer idEstadoConsulta;
   @Size(max = 30)
   private String descripcion;
   @Size(max = 10)
   private String color;
   private Boolean activo;
   private String datosBusqueda;
}
