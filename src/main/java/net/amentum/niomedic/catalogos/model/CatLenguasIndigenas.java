package net.amentum.niomedic.catalogos.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data // @Getter, @Setter, @ToString, @EqualsAndHashCode and @RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cat_lenguas_indigenas")
public class CatLenguasIndigenas implements Serializable {
	private static final long serialVersionUID = 5587795260608129279L;
	@Id
	@Column(name = "id_cat_lenguas_indigenas")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idCatLenguasIndigenas;
	private Integer lengCatalogKey;
	@Size(min=3, max = 50, message="Ingrese minimo 3 caracteres y máximo 50 ")
	@NotNull(message = "No puede ser nulo")
	private String lengDescripcion;
	private String datosBusqueda;
	@NotNull
	private Boolean activo;
}
