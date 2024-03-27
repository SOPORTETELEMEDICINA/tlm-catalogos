package net.amentum.niomedic.catalogos.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "cat_especialidades")
public class CatEspecialidades implements Serializable {

   private static final long serialVersionUID = -7702888133806990963L;

   @Id
   @Column(name = "id_cat_especialidades")
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Integer idCatEspecialidades;
   @Size(max = 10, message = "No puede contener más de 10 caracteres")
   @NotEmpty(message = "No puede ser nulo/vacio")
   private String especialidadCodigo;
   @Size(max = 100, message = "No puede contener más de 100 caracteres")
   @NotEmpty(message = "No puede ser nulo/vacio")
   private String especialidadDescripcion;
   @NotNull(message = "No puede ser nulo")
   private Boolean activo;
   @Temporal(TemporalType.TIMESTAMP)
   private Date fechaUltimaModificacion;
   private String datosBusqueda;

}
