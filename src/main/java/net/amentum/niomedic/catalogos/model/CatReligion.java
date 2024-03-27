package net.amentum.niomedic.catalogos.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PostPersist;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data // @Getter, @Setter, @ToString, @EqualsAndHashCode and @RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cat_religion")
public class CatReligion implements Serializable {
	private static final long serialVersionUID = 5867510926670840889L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_cat_religion")
	private Integer idCatReligion;
	@Size(min=3, max = 40 , message="por favor ingrese minimo 3 caracteres y máximo 40")
	@NotNull(message="no puede ser nulo")
	private String relDescripcion;
	private String datosBusqueda;
	@NotNull(message="No puede ser nulo")
	private Boolean activo;
}
