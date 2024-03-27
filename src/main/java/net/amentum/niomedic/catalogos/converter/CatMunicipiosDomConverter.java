package net.amentum.niomedic.catalogos.converter;

import net.amentum.niomedic.catalogos.model.CatMunicipiosDom;
import net.amentum.niomedic.catalogos.views.CatMunicipiosDomView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CatMunicipiosDomConverter {
   private Logger logger = LoggerFactory.getLogger(CatMunicipiosDomConverter.class);

   public CatMunicipiosDomView toView(CatMunicipiosDom catMunicipiosDom, Boolean completeConversion){
      CatMunicipiosDomView catMunicipiosDomView = new CatMunicipiosDomView();
      catMunicipiosDomView.setIdCatMunicipios(catMunicipiosDom.getIdCatMunicipios());
      catMunicipiosDomView.setCveMunicipio(catMunicipiosDom.getCveMunicipio());
      catMunicipiosDomView.setDescripcionMunicipios(catMunicipiosDom.getDescripcionMunicipios());
      catMunicipiosDomView.setEfeKey(catMunicipiosDom.getEfeKey());

      logger.debug("convertir catMunicipiosDom to View: {}", catMunicipiosDomView);
      return catMunicipiosDomView;
   }

}
