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
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data // @Getter, @Setter, @ToString, @EqualsAndHashCode and @RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cat_dolometro")
public class CatDolometro implements Serializable {
   @Id
   @Column(name = "id_cat_dolometro")
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Integer idCatDolometro;
   private Integer nivel;
   @Size(max = 20)
   private String doloDescripcion;
   private String datosBusqueda;
}
