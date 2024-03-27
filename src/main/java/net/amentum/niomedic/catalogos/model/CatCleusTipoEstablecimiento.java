package net.amentum.niomedic.catalogos.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="cat_clues_tipo_establecimiento")
public class CatCleusTipoEstablecimiento implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -882596327198930011L;
	@Id
	@Column(name="id_tipo_estab")
	private Integer idTipoEstab;
	@Size(min=3, max=40)
	private String tipoEstabNombre;
	@Size(min=1, max=10)
	private String tipo_estab_cve;
	
	@Override
	public String toString() {
		return "CatCleusTipoEstablecimiento {"+
				"idTipoEstab=" + idTipoEstab + 
				", tipoEstabNombre=" + tipoEstabNombre +
				", tipo_estab_cve=" + tipo_estab_cve +
				"}";
	}
}
