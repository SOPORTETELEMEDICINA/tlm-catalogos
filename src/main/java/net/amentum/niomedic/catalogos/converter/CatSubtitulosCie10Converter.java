package net.amentum.niomedic.catalogos.converter;

import net.amentum.niomedic.catalogos.model.CatCapitulosCie10;
import net.amentum.niomedic.catalogos.model.CatSubtitulosCie10;
import net.amentum.niomedic.catalogos.model.CatTitulosCie10;
import net.amentum.niomedic.catalogos.views.CatSubtitulosCie10View;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CatSubtitulosCie10Converter {
   private Logger logger = LoggerFactory.getLogger(CatSubtitulosCie10Converter.class);

   public CatSubtitulosCie10View toView(CatSubtitulosCie10 catSubtitulosCie10, Boolean completeConversion) {
      CatSubtitulosCie10View catSubtitulosCie10View = new CatSubtitulosCie10View();
      catSubtitulosCie10View.setIdCatSubtitulos(catSubtitulosCie10.getIdCatSubtitulos());
      catSubtitulosCie10View.setCodigosSubtitulosCie10(catSubtitulosCie10.getCodigosSubtitulosCie10());
      catSubtitulosCie10View.setDescripcionSubtitulosCie10(catSubtitulosCie10.getDescripcionSubtitulosCie10());
      catSubtitulosCie10View.setAliasSubtitulosCie10(catSubtitulosCie10.getAliasSubtitulosCie10());

      if (completeConversion) {
         CatCapitulosCie10 catCapitulosCie10 = catSubtitulosCie10.getCatCapitulosCie10();
         catSubtitulosCie10View.set_descripcionCapitulosCie10((catCapitulosCie10 == null) ? "No existe" : catCapitulosCie10.getDescripcionCapitulosCie10());
         catSubtitulosCie10View.setCatCapitulosId((catCapitulosCie10 == null) ? -1 : catCapitulosCie10.getIdCatCapitulos());

         CatTitulosCie10 catTitulosCie10 = catSubtitulosCie10.getCatTitulosCie10();
         catSubtitulosCie10View.set_descripcionTitulosCie10((catTitulosCie10 == null) ? "No existe" : catTitulosCie10.getDescripcionTitulosCie10());
         catSubtitulosCie10View.setCatTitulosId((catTitulosCie10 == null) ? -1 : catTitulosCie10.getIdCatTitulos());
      }

      logger.debug("convertir catSubtitulosCie10 to View: {}", catSubtitulosCie10View);
      return catSubtitulosCie10View;
   }

}
