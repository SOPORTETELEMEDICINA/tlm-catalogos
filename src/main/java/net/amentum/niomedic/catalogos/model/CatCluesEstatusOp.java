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
@Table(name="cat_clues_estatus_op")
public class CatCluesEstatusOp implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3114677979633396682L;
	@Id
	@Column(name="id_estatus")
	private Integer idEstatus;
	@Size(min=3, max=60)
	private String estatusOperacion;
	@Override
	public String toString() {
		return "CatCluesEstatusOp {"+
				"idEstatus=" + idEstatus + 
				", estatusOperacion=" + estatusOperacion +
				"}";
	}
	
}
