package net.amentum.niomedic.catalogos.converter;

import net.amentum.niomedic.catalogos.model.CatNumerosEmergencia;
import net.amentum.niomedic.catalogos.views.CatNumerosEmergenciaView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CatNumerosEmergenciaConverter {
   private Logger logger = LoggerFactory.getLogger(CatNumerosEmergenciaConverter.class);

   public CatNumerosEmergenciaView toView(CatNumerosEmergencia catNumerosEmergencia, Boolean completeConversion){
      CatNumerosEmergenciaView catNumerosEmergenciaView = new CatNumerosEmergenciaView();
      catNumerosEmergenciaView.setIdCatNumerosEmergencia(catNumerosEmergencia.getIdCatNumerosEmergencia());
      catNumerosEmergenciaView.setNumeroTelefonico(catNumerosEmergencia.getNumeroTelefonico());
      catNumerosEmergenciaView.setNumeroEmergenciaDescripcion(catNumerosEmergencia.getNumeroEmergenciaDescripcion());

      logger.debug("convertir catNumerosEmergencia to View: {}", catNumerosEmergenciaView);
      return catNumerosEmergenciaView;
   }

}
