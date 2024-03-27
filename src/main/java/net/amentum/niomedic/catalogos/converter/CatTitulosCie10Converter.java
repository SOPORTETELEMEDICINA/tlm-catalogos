package net.amentum.niomedic.catalogos.converter;

import net.amentum.niomedic.catalogos.model.CatCapitulosCie10;
import net.amentum.niomedic.catalogos.model.CatTitulosCie10;
import net.amentum.niomedic.catalogos.views.CatTitulosCie10View;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CatTitulosCie10Converter {
   private Logger logger = LoggerFactory.getLogger(CatTitulosCie10Converter.class);

   public CatTitulosCie10View toView(CatTitulosCie10 catTitulosCie10, Boolean completeConversion) {
      CatTitulosCie10View catTitulosCie10View = new CatTitulosCie10View();
      catTitulosCie10View.setIdCatTitulos(catTitulosCie10.getIdCatTitulos());
      catTitulosCie10View.setCodigosTitulosCie10(catTitulosCie10.getCodigosTitulosCie10());
      catTitulosCie10View.setDescripcionTitulosCie10(catTitulosCie10.getDescripcionTitulosCie10());
      catTitulosCie10View.setAliasTitulosCie10(catTitulosCie10.getAliasTitulosCie10());

      if (completeConversion) {
         CatCapitulosCie10 catCapitulosCie10 = catTitulosCie10.getCatCapitulosCie10();
         catTitulosCie10View.set_descripcionCapitulosCie10((catCapitulosCie10 == null) ? "No existe" : catCapitulosCie10.getDescripcionCapitulosCie10());
         catTitulosCie10View.setCatCapitulosId((catCapitulosCie10 == null) ? -1 : catCapitulosCie10.getIdCatCapitulos());
      }

      logger.debug("convertir catTitulosCie10 to View: {}", catTitulosCie10View);
      return catTitulosCie10View;
   }

}
