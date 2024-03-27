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
import java.io.Serializable;


@Data // @Getter, @Setter, @ToString, @EqualsAndHashCode and @RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cat_instrumental")
public class CatInstrumental implements Serializable {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "id_instrumental")
   private Integer idInstrumental;
   private Integer fkGrupo;
   private String clave;
   @Column(columnDefinition = "TEXT")
   private String nombreGenericoEspecifico;
   @Column(columnDefinition = "TEXT")
   private String especialidades;
   @Column(columnDefinition = "TEXT")
   private String funciones;

}
