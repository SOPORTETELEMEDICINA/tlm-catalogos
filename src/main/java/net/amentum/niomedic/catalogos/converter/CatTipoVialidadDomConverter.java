package net.amentum.niomedic.catalogos.converter;

import net.amentum.niomedic.catalogos.model.CatTipoVialidadDom;
import net.amentum.niomedic.catalogos.views.CatTipoVialidadDomView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CatTipoVialidadDomConverter {
   private Logger logger = LoggerFactory.getLogger(CatTipoVialidadDomConverter.class);

   public CatTipoVialidadDomView toView(CatTipoVialidadDom catTipoVialidadDom, Boolean completeConversion){
      CatTipoVialidadDomView catTipoVialidadDomView = new CatTipoVialidadDomView();
      catTipoVialidadDomView.setIdCatTipoVialidad(catTipoVialidadDom.getIdCatTipoVialidad());
      catTipoVialidadDomView.setVialCve(catTipoVialidadDom.getVialCve());
      catTipoVialidadDomView.setDescripcionVialidad(catTipoVialidadDom.getDescripcionVialidad());

      logger.debug("convertir catTipoVialidadDom to View: {}", catTipoVialidadDomView);
      return catTipoVialidadDomView;
   }

}
