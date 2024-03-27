package net.amentum.niomedic.catalogos.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data // @Getter, @Setter, @ToString, @EqualsAndHashCode and @RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cat_cie10")
public class CatCie10 implements Serializable {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "id_cie10")
   private Integer idCie10;
   @Size(max = 2)
   private String letra;
   @Size(max = 5)
   private String catalogKey;
   @Size(max = 2)
   private String asterisco;
   @Size(max = 250)
   private String nombre;
   @Size(max = 2)
   private String lsex;
   @Size(max = 5)
   private String linf;
   @Size(max = 5)
   private String lsup;
   @Size(max = 3)
   private String trivial;
   @Size(max = 3)
   private String erradicado;
   @Size(max = 3)
   private String nInter;
   @Size(max = 3)
   private String nin;
   @Size(max = 3)
   private String ninmtobs;
   @Size(max = 3)
   private String noCbd;
   @Size(max = 3)
   private String noAph;
   @Size(max = 3)
   private String fetal;
   @Size(max = 3)
   private String diaCapituloType;
   @Size(max = 130)
   private String yearModifi;
   @Size(max = 5)
   private String yearAplicacion;
   @Size(max = 3)
   private String notdiaria;
   @Size(max = 3)
   private String notsemanal;
   @Size(max = 3)
   private String sistemaEspecial;
   @Size(max = 3)
   private String birmm;
   @Size(max = 2)
   private String cveCausaType;
   @Size(max = 3)
   private String epiMorta;
   @Size(max = 3)
   @Column(name="epi_morta_m5")
   private String epiMortaM5;
   @Size(max = 3)
   @Column(name="edas_e_iras_en_m5")
   private String edasEIrasEnM5;
   @Size(max = 4)
   private String lista1;
   @Size(max = 4)
   private String lista5;
   @Size(max = 5)
   private String prinmorta;
   @Size(max = 5)
   private String prinmorbi;
   @Size(max = 5)
   private String lmMorbi;
   @Size(max = 5)
   private String lmMorta;
   @Size(max = 4)
   private String lgbd165;
   @Size(max = 4)
   private String lomsbeck;
   @Size(max = 4)
   private String lgbd190;
   @Size(max = 3)
   private String esCauses;
   @Size(max = 4)
   private String numCauses;
   @Size(max = 3)
   private String esSuiveMorta;
   @Size(max = 2)
   private String daga;
   @Size(max = 4)
   private String epiClave;
   @Size(max = 100)
   private String epiClaveDesc;
   @Size(max = 3)
   private String esSuiveMorb;
   @Size(max = 3)
   private String esSuiveNotin;
   @Size(max = 3)
   private String esSuiveEstEpi;
   @Size(max = 3)
   private String esSuiveEstBrote;
   @Size(max = 3)
   private String sinac;
   @Size(max = 3)
   private String codigox;
   @Size(max = 3)
   private String codSitLesion;
   private String datosBusqueda;
//   relaciones
   @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
   @JoinColumn(name = "cat_capitulos_id", referencedColumnName = "id_cat_capitulos")
   private CatCapitulosCie10 catCapitulosCie10; //catCapitulosId

   @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
   @JoinColumn(name = "cat_titulos_id", referencedColumnName = "id_cat_titulos")
   private CatTitulosCie10 catTitulosCie10; //catTitulosId

   @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
   @JoinColumn(name = "cat_subtitulos_id", referencedColumnName = "id_cat_subtitulos")
   private CatSubtitulosCie10 catSubtitulosCie10; //catSubtitulosId

   @Override
   public String toString() {
      return "CatCie10{" +
         "idCie10=" + idCie10 +
         ", letra='" + letra + '\'' +
         ", catalogKey='" + catalogKey + '\'' +
         ", asterisco='" + asterisco + '\'' +
         ", nombre='" + nombre + '\'' +
         ", lsex='" + lsex + '\'' +
         ", linf='" + linf + '\'' +
         ", lsup='" + lsup + '\'' +
         ", trivial='" + trivial + '\'' +
         ", erradicado='" + erradicado + '\'' +
         ", nInter='" + nInter + '\'' +
         ", nin='" + nin + '\'' +
         ", ninmtobs='" + ninmtobs + '\'' +
         ", noCbd='" + noCbd + '\'' +
         ", noAph='" + noAph + '\'' +
         ", fetal='" + fetal + '\'' +
         ", diaCapituloType='" + diaCapituloType + '\'' +
         ", yearModifi='" + yearModifi + '\'' +
         ", yearAplicacion='" + yearAplicacion + '\'' +
         ", notdiaria='" + notdiaria + '\'' +
         ", notsemanal='" + notsemanal + '\'' +
         ", sistemaEspecial='" + sistemaEspecial + '\'' +
         ", birmm='" + birmm + '\'' +
         ", cveCausaType='" + cveCausaType + '\'' +
         ", epiMorta='" + epiMorta + '\'' +
         ", epiMortaM5='" + epiMortaM5 + '\'' +
         ", edasEIrasEnM5='" + edasEIrasEnM5 + '\'' +
         ", lista1='" + lista1 + '\'' +
         ", lista5='" + lista5 + '\'' +
         ", prinmorta='" + prinmorta + '\'' +
         ", prinmorbi='" + prinmorbi + '\'' +
         ", lmMorbi='" + lmMorbi + '\'' +
         ", lmMorta='" + lmMorta + '\'' +
         ", lgbd165='" + lgbd165 + '\'' +
         ", lomsbeck='" + lomsbeck + '\'' +
         ", lgbd190='" + lgbd190 + '\'' +
         ", esCauses='" + esCauses + '\'' +
         ", numCauses='" + numCauses + '\'' +
         ", esSuiveMorta='" + esSuiveMorta + '\'' +
         ", daga='" + daga + '\'' +
         ", epiClave='" + epiClave + '\'' +
         ", epiClaveDesc='" + epiClaveDesc + '\'' +
         ", esSuiveMorb='" + esSuiveMorb + '\'' +
         ", esSuiveNotin='" + esSuiveNotin + '\'' +
         ", esSuiveEstEpi='" + esSuiveEstEpi + '\'' +
         ", esSuiveEstBrote='" + esSuiveEstBrote + '\'' +
         ", sinac='" + sinac + '\'' +
         ", codigox='" + codigox + '\'' +
         ", codSitLesion='" + codSitLesion + '\'' +
         ", datosBusqueda='" + datosBusqueda + '\'' +
         '}';
   }
}
