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
import java.util.Date;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Data // @Getter, @Setter, @ToString, @EqualsAndHashCode and @RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cat_cif")
public class CatCif implements Serializable {
   private static final long serialVersionUID = -1425965855789113902L;
   @Id
   @Column(name = "id_cat_cif")
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Integer idCatCif;
   @Size(max = 10, message="No puede contener más de 10 caracteres")
   @NotEmpty(message="No puede ser nulo/vacio")
   private String discCodigo;
   @Size(max = 200, message="No puede contener más de 200 caracteres")
   @NotEmpty(message="No puede ser nulo/vacio")
   private String discDescripcion;
   @NotNull(message="No puede ser nulo")
   private Boolean activo;
   @Temporal(TemporalType.TIMESTAMP)
   private Date fechaUltimaModificacion;
   private String datosBusqueda;
}
