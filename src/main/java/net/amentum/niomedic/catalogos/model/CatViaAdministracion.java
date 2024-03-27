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
@Table(name = "cat_via_administracion")
public class CatViaAdministracion implements Serializable {
	/**
	 * 
	 **/
	private static final long serialVersionUID = -1893385524987964184L;
	@Id
	@Column(name = "id_cat_via_administracion")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idCatViaAdministracion;
	@Size(max = 50, message = "Maximo 50 caracteres" )
	@NotEmpty(message="No puede ser nulo/vació")
	private String vaDescripcion;
	private String datosBusqueda;
	@NotNull(message="No puede ser nulo")
	private Boolean activo;
}
