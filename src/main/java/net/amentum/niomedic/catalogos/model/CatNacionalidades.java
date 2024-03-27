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
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;

@Data // @Getter, @Setter, @ToString, @EqualsAndHashCode and @RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cat_nacionalidades",  uniqueConstraints=@UniqueConstraint(columnNames="nacPaisCodigo"))
public class CatNacionalidades implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7616088625641223299L;
	@Id
	@Column(name = "id_cat_nacionalidades")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idCatNacionalidades;
	@NotNull(message = "No puede ser nulo")
	private Integer nacPaisCodigo;
	@NotEmpty(message = "No puede ser nulo/vacio")
	@Size(max = 40)
	private String nacPaisDescripcion;
	@NotEmpty(message = "No puede ser nulo/vacio")
	@Size(max = 5)
	private String nacPaisClave;
	private String datosBusqueda;
	@NotNull(message = "No puede ser nulo")
	private Boolean activo;
}
