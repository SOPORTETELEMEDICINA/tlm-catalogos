package net.amentum.niomedic.catalogos.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

@Data // @Getter, @Setter, @ToString, @EqualsAndHashCode and @RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cat_medicamentos")
public class CatMedicamentos implements Serializable {

   private static final long serialVersionUID = -4860069232475926057L;

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "id_cat_medicamentos")
   private Integer idCatMedicamentos;
   @Size(max = 25, message = "No puede contener más de 25 caracteres")
   @NotEmpty(message = "No puede ser nulo/vacio")
   private String cveCodigo;
   @Size(max = 25, message = "No puede contener más de 25 caracteres")
   @NotEmpty(message = "No puede ser nulo/vacio")
   private String subCveCodigo;
   @Size(max = 255, message = "No puede contener más de 255 caracteres")
   private String nombreGenerico;
   @Size(max = 100, message = "No puede contener más de 100 caracteres")
   private String formaFarmaceutica;
   @Column(columnDefinition = "TEXT")
   private String concentracion;
   @Column(columnDefinition = "TEXT")
   private String presentacion;
   @Column(columnDefinition = "TEXT")
   private String principalIndicaciones;
   @Column(columnDefinition = "TEXT")
   private String demasIndicaciones;
   @Size(max = 25, message = "No puede contener más de 25 caracteres")
   private String tipoActualizacion;
   @Size(max = 180, message = "No puede contener más de 180 caracteres")
   private String numActualizacion;
   @Column(columnDefinition = "TEXT")
   private String descripcionCompleta;
   @Column(columnDefinition = "TEXT")
   private String datosBusqueda;
   @NotNull(message = "No puede ser nulo")
   private Boolean activo;
   @Temporal(TemporalType.TIMESTAMP)
   private Date fechaUltimaModificacion;

//   relaciones
   @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
   @JoinColumn(name = "cat_tipo_insumo_id", referencedColumnName = "id_cat_tipo_insumo")
   private CatTipoInsumoMed catTipoInsumoMed; //catTipoInsumoId

   @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
   @JoinColumn(name = "cat_cuadroIo_id", referencedColumnName = "id_cat_cuadroIo")
   private CatCuadroIoMed catCuadroIoMed; //catCuadroIoId

   @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
   @JoinColumn(name = "cat_gpo_terapeutico_id", referencedColumnName = "id_cat_gpo_terapeutico")
   private CatGpoTerapeuticoMed catGpoTerapeuticoMed; //catGpoTerapeuticoId

}
