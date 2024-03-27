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
@Table(name="cat_clues_tipologia")
public class CatCluesTipologia implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6009685364993215832L;
	@Id
	@Column(name="id_tipologia")
	private Integer idTipologia;
	@Size(min=3, max=150)
	private String  nombreTipologia;
	@Size(min=1, max=20)
	private String claveTipologia;

}
