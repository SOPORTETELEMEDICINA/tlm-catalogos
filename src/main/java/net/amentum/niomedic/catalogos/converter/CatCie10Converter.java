package net.amentum.niomedic.catalogos.converter;

import net.amentum.niomedic.catalogos.model.CatCapitulosCie10;
import net.amentum.niomedic.catalogos.model.CatCie10;
import net.amentum.niomedic.catalogos.model.CatSubtitulosCie10;
import net.amentum.niomedic.catalogos.model.CatTitulosCie10;
import net.amentum.niomedic.catalogos.views.CatCie10View;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CatCie10Converter {
   private Logger logger = LoggerFactory.getLogger(CatCie10Converter.class);

   public CatCie10View toView(CatCie10 catCie10, Boolean completeConversion) {
      CatCie10View catCie10View = new CatCie10View();
      catCie10View.setIdCie10(catCie10.getIdCie10());
      catCie10View.setLetra(catCie10.getLetra());
      catCie10View.setCatalogKey(catCie10.getCatalogKey());
      catCie10View.setAsterisco(catCie10.getAsterisco());
      catCie10View.setNombre(catCie10.getNombre());
      catCie10View.setLsex(catCie10.getLsex());
      catCie10View.setLinf(catCie10.getLinf());
      catCie10View.setLsup(catCie10.getLsup());
      catCie10View.setTrivial(catCie10.getTrivial());
      catCie10View.setErradicado(catCie10.getErradicado());
      catCie10View.setNInter(catCie10.getNInter());
      catCie10View.setNin(catCie10.getNin());
      catCie10View.setNinmtobs(catCie10.getNinmtobs());
      catCie10View.setNoCbd(catCie10.getNoCbd());
      catCie10View.setNoAph(catCie10.getNoAph());
      catCie10View.setFetal(catCie10.getFetal());
      catCie10View.setDiaCapituloType(catCie10.getDiaCapituloType());
      catCie10View.setYearModifi(catCie10.getYearModifi());
      catCie10View.setYearAplicacion(catCie10.getYearAplicacion());
      catCie10View.setNotdiaria(catCie10.getNotdiaria());
      catCie10View.setNotsemanal(catCie10.getNotsemanal());
      catCie10View.setSistemaEspecial(catCie10.getSistemaEspecial());
      catCie10View.setBirmm(catCie10.getBirmm());
      catCie10View.setCveCausaType(catCie10.getCveCausaType());
      catCie10View.setEpiMorta(catCie10.getEpiMorta());
      catCie10View.setEpiMortaM5(catCie10.getEpiMortaM5());
      catCie10View.setEdasEIrasEnM5(catCie10.getEdasEIrasEnM5());
      catCie10View.setLista1(catCie10.getLista1());
      catCie10View.setLista5(catCie10.getLista5());
      catCie10View.setPrinmorta(catCie10.getPrinmorta());
      catCie10View.setPrinmorbi(catCie10.getPrinmorbi());
      catCie10View.setLmMorbi(catCie10.getLmMorbi());
      catCie10View.setLmMorta(catCie10.getLmMorta());
      catCie10View.setLgbd165(catCie10.getLgbd165());
      catCie10View.setLomsbeck(catCie10.getLomsbeck());
      catCie10View.setLgbd190(catCie10.getLgbd190());
      catCie10View.setEsCauses(catCie10.getEsCauses());
      catCie10View.setNumCauses(catCie10.getNumCauses());
      catCie10View.setEsSuiveMorta(catCie10.getEsSuiveMorta());
      catCie10View.setDaga(catCie10.getDaga());
      catCie10View.setEpiClave(catCie10.getEpiClave());
      catCie10View.setEpiClaveDesc(catCie10.getEpiClaveDesc());
      catCie10View.setEsSuiveMorb(catCie10.getEsSuiveMorb());
      catCie10View.setEsSuiveNotin(catCie10.getEsSuiveNotin());
      catCie10View.setEsSuiveEstEpi(catCie10.getEsSuiveEstEpi());
      catCie10View.setEsSuiveEstBrote(catCie10.getEsSuiveEstBrote());
      catCie10View.setSinac(catCie10.getSinac());
      catCie10View.setCodigox(catCie10.getCodigox());
      catCie10View.setCodSitLesion(catCie10.getCodSitLesion());

      if (completeConversion) {
         CatCapitulosCie10 catCapitulosCie10 = catCie10.getCatCapitulosCie10();
         catCie10View.set_descripcionCapitulosCie10((catCapitulosCie10 == null) ? "No existe" : catCapitulosCie10.getDescripcionCapitulosCie10());
         catCie10View.setCatCapitulosId((catCapitulosCie10 == null) ? -1 : catCapitulosCie10.getIdCatCapitulos());

         CatTitulosCie10 catTitulosCie10 = catCie10.getCatTitulosCie10();
         catCie10View.set_descripcionTitulosCie10((catTitulosCie10 == null) ? "No existe" : catTitulosCie10.getDescripcionTitulosCie10());
         catCie10View.setCatTitulosId((catTitulosCie10 == null) ? -1 : catTitulosCie10.getIdCatTitulos());

         CatSubtitulosCie10 catSubtitulosCie10 = catCie10.getCatSubtitulosCie10();
         catCie10View.set_descripcionSubtitulosCie10((catSubtitulosCie10 == null) ? "No existe" : catSubtitulosCie10.getDescripcionSubtitulosCie10());
         catCie10View.setCatSubtitulosId((catSubtitulosCie10 == null) ? -1 : catSubtitulosCie10.getIdCatSubtitulos());
      }

      logger.debug("convertir catCie10 to View: {}", catCie10View);
      return catCie10View;
   }

}
