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
@Table(name="cat_clues_subtipologia")
public class CatCluesSubtipologia implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3473206105172518396L;
	@Id
	@Column(name="id_subtipo")
	private Integer idSubtipo;
	@Size(min=3, max=100)
	private String nombreSubtipologia;
	@Size(min=1, max=10)
	private String claveSubtipologia;
	@Override
	public String toString() {
		return "CatCluesSubtipologia {"+
				"idSubtipo=" + idSubtipo + 
				", nombreSubtipologia=" + nombreSubtipologia +
				", claveSubtipologia=" + claveSubtipologia + 
				"}";
	}
	
}
