package net.amentum.niomedic.catalogos.converter;

import net.amentum.niomedic.catalogos.model.CatTipoInsumoMed;
import net.amentum.niomedic.catalogos.views.CatTipoInsumoMedView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CatTipoInsumoMedConverter {
   private Logger logger = LoggerFactory.getLogger(CatTipoInsumoMedConverter.class);

   public CatTipoInsumoMedView toView(CatTipoInsumoMed catTipoInsumoMed, Boolean completeConversion){
      CatTipoInsumoMedView catTipoInsumoMedView = new CatTipoInsumoMedView();
      catTipoInsumoMedView.setIdCatTipoInsumo(catTipoInsumoMed.getIdCatTipoInsumo());
      catTipoInsumoMedView.setDescripcionTipoInsumo(catTipoInsumoMed.getDescripcionTipoInsumo());

      logger.debug("convertir catTipoInsumoMed to View: {}", catTipoInsumoMedView);
      return catTipoInsumoMedView;
   }

}
