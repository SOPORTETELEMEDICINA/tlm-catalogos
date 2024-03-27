package net.amentum.niomedic.catalogos.converter;

import net.amentum.niomedic.catalogos.model.CatTipoAsentamientoDom;
import net.amentum.niomedic.catalogos.views.CatTipoAsentamientoDomView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CatTipoAsentamientoDomConverter {
   private Logger logger = LoggerFactory.getLogger(CatTipoAsentamientoDomConverter.class);

   public CatTipoAsentamientoDomView toView(CatTipoAsentamientoDom catTipoAsentamientoDom, Boolean completeConversion){
      CatTipoAsentamientoDomView catTipoAsentamientoDomView = new CatTipoAsentamientoDomView();
      catTipoAsentamientoDomView.setIdCatTipoAsentamiento(catTipoAsentamientoDom.getIdCatTipoAsentamiento());
      catTipoAsentamientoDomView.setDescripcionAsentamiento(catTipoAsentamientoDom.getDescripcionAsentamiento());
      catTipoAsentamientoDomView.setAbreviatura(catTipoAsentamientoDom.getAbreviatura());
      catTipoAsentamientoDomView.setIdAsentCve(catTipoAsentamientoDom.getIdAsentCve());

      logger.debug("convertir catTipoAsentamientoDom to View: {}", catTipoAsentamientoDomView);
      return catTipoAsentamientoDomView;
   }

}
