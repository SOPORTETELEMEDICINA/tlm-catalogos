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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;

@Data // @Getter, @Setter, @ToString, @EqualsAndHashCode and @RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cat_formacion")
public class CatFormacion implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -747933578346443172L;
	@Id
	@Column(name = "id_cat_formacion")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idCatFormacion;
	@Size(max = 110, message="No puede contener más de 110 caracteres")
	@NotEmpty(message="No puede ser nulo/vació")
	private String formDescripcion;
	@Size(max = 70, message="No puede contener más de 70 caracteres")
	@NotEmpty(message="No puede ser nulo/vació")
	private String formAgrupacion;
	@NotNull(message="No pude ser nulo")
	private Integer formGrado;
	private String datosBusqueda;
	@NotNull(message="No puede ser nulo")
	private Boolean activo;
}
