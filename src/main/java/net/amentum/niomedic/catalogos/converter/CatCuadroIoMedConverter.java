package net.amentum.niomedic.catalogos.converter;

import net.amentum.niomedic.catalogos.model.CatCuadroIoMed;
import net.amentum.niomedic.catalogos.views.CatCuadroIoMedView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CatCuadroIoMedConverter {
   private Logger logger = LoggerFactory.getLogger(CatCuadroIoMedConverter.class);

   public CatCuadroIoMedView toView(CatCuadroIoMed catCuadroIoMed, Boolean completeConversion){
      CatCuadroIoMedView catCuadroIoMedView = new CatCuadroIoMedView();
      catCuadroIoMedView.setIdCatCuadroIo(catCuadroIoMed.getIdCatCuadroIo());
      catCuadroIoMedView.setDescripcionCuadroIo(catCuadroIoMed.getDescripcionCuadroIo());

      logger.debug("convertir catCuadroIoMed to View: {}", catCuadroIoMedView);
      return catCuadroIoMedView;
   }

}
