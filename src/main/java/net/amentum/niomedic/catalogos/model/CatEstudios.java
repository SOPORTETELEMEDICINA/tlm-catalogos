package net.amentum.niomedic.catalogos.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name="cat_estudio")
public class CatEstudios implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6867311598208725031L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_cat_estudio")
	private Integer idCatEstudio;
	@Size(max=100, message = "Maximo 100 caracteres")
	@NotEmpty(message="No puede ser nulo/vació")
	private String descripcion;
	@Column(columnDefinition="TEXT")
	private String preparacion;
	@Size(max=100, message = "Maximo 100 caracteres")
	@NotEmpty(message="No puede ser nulo/vació")
	private String estudio;
	@NotNull(message = "No puede ser nulo")
	private Boolean activo;
	
}
