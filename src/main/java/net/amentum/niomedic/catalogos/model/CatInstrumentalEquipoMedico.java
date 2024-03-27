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
@Table(name = "cat_instrumental_equipo_medico")
public class CatInstrumentalEquipoMedico implements Serializable {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "id")
   private Integer id;
   private Integer idGpoTipo;
   @Size(max = 185)
   private String emNombreGenerico;
   @Size(max = 15)
   private String emClave;
   @Size(max = 110)
   private String emEspecialidades;
   @Size(max = 170)
   private String emServicios;
   @Column(columnDefinition = "TEXT")
   private String emDescripcion;
   @Column(columnDefinition = "TEXT")
   private String emRefacciones;
   @Column(columnDefinition = "TEXT")
   private String emAccesoriosOpcionales;
   @Column(columnDefinition = "TEXT")
   private String emConsumibles;
   @Column(columnDefinition = "TEXT")
   private String emInstalacion;
   @Size(max = 220)
   private String emOperacion;
   @Size(max = 290)
   private String emMantenimiento;
   @Size(max = 370)
   private String emNomsCertificados;

}
