package net.amentum.niomedic.catalogos.converter;

import net.amentum.niomedic.catalogos.model.CatCapitulosCie10;
import net.amentum.niomedic.catalogos.views.CatCapitulosCie10View;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CatCapitulosCie10Converter {
   private Logger logger = LoggerFactory.getLogger(CatCapitulosCie10Converter.class);

   public CatCapitulosCie10View toView(CatCapitulosCie10 catCapitulosCie10, Boolean completeConversion){
      CatCapitulosCie10View catCapitulosCie10View = new CatCapitulosCie10View();
      catCapitulosCie10View.setIdCatCapitulos(catCapitulosCie10.getIdCatCapitulos());
      catCapitulosCie10View.setCodigosCapitulosCie10(catCapitulosCie10.getCodigosCapitulosCie10());
      catCapitulosCie10View.setDescripcionCapitulosCie10(catCapitulosCie10.getDescripcionCapitulosCie10());

      logger.debug("convertir catCapitulosCie10 to View: {}", catCapitulosCie10View);
      return catCapitulosCie10View;
   }

}
