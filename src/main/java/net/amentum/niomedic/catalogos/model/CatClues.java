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
@Table(name = "cat_clues")
public class CatClues implements Serializable {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "id_cat_clues")
   private Integer idCatClues;
   private String clues;
   private String fkEntidad;
   private String fkCveMunicipio;
   private String fkCveLocalidad;
   @Column(columnDefinition = "TEXT")
   private String nombreJurisdiccion;
   private Integer claveJurisdiccion;
   @Column(columnDefinition = "TEXT")
   private String nombreInstitucion;
   private String claveInstitucion;
   private String claveCortaInstitucion;
   private String nombreTipoEstablecimiento;
   private String claveTipoEstablecimiento;
   private String nombreTipologia;
   private String claveTipologia;
   private String nombreSubtipologia;
   private Integer claveSubtipologia;
   private Integer claveScian;
   @Column(columnDefinition = "TEXT")
   private String descripcionClaveScian;
   private Integer consultoriosMedGral;
   private Integer consultoriosOtrasAreas;
   private Integer totalConsultorios;
   private Integer camasAreaHos;
   private Integer camasOtrasAreas;
   private Integer totalCamas;
   private String nombreUnidad;
   private Integer claveVialidad;
   private String tipoVialidad;
   @Column(columnDefinition = "TEXT")
   private String vialidad;
   private String numeroExterior;
   private String numeroInterior;
   private Integer claveTipoAsentamiento;
   private String tipoAsentamiento;
   @Column(columnDefinition = "TEXT")
   private String asentamiento;
   private String entreTipoVialidad1;
   private String entreVialidad1;
   private String entreTipoVialidad2;
   private String entreVialidad2;
   @Column(columnDefinition = "TEXT")
   private String observacionesDireccion;
   private String codigoPostal;
   private String estatusOperacion;
   private Integer claveEstatusOperacion;
   private String tieneLicenciaSanitaria;
   private String numeroLicenciaSanitaria;
   private Boolean tieneAvisoFuncionamiento;
   private String fechaEmisionAvFun;
   private String rfcEstablecimiento;
   private String fechaConstruccion;
   private String fechaInicioOperacion;
   private String unidadMovilMarca;
   private Integer unidadMovilModelo;
   private Integer unidadMovilCapacidad;
   private String unidadMovilPrograma;
   private Integer unidadMovilClavePrograma;
   private String unidadMovilTipo;
   private String unidadMovilClaveTipo;
   private String unidadMovilTipologia;
   private Integer unidadMovilClaveTipologia;
   private Double longitud;
   private Double latitud;
   private String nombreInsAdm;
   private Integer claveInsAdm;
   private String nivelAtencion;
   private Integer claveNivelAtencion;
   private String estatusAcreditacion;
   private Integer claveEstatusAcreditacion;
   @Column(columnDefinition = "TEXT")
   private String acreditaciones;
   @Column(columnDefinition = "TEXT")
   private String subacreditacion;
   private String estratoUnidad;
   private Integer claveEstratoUnidad;
   private String tipoObra;
   private Integer claveTipoObra;
   @Column(columnDefinition = "TEXT")
   private String horarioAtencion;
   @Column(columnDefinition = "TEXT")
   private String areasServicios;
   private String ultimoMovimiento;
   private String fechaUltimoMovimiento;
   private String certificacionCsg;
   private String tipoCertificacion;
   private String vigenciaCertificacion;

}
