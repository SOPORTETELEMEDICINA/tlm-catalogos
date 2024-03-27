package net.amentum.niomedic.catalogos.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="cat_clues_cve_scian")
public class CatCleusCveScian implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1449652101357642728L;
	@Id
	@Column(name="id_scian")
	private Integer idScian;
	private Integer claveScian;
	private String descripcion_scian;
	@Override
	public String toString() {
		return "CatCleusCveScian {"+
				"idScian=" + idScian + 
				", claveScian=" + claveScian + 
				", descripcion_scian=" + descripcion_scian + 
				"}";
	}
	
	
}
