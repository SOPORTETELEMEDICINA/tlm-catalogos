package net.amentum.niomedic.catalogos.converter;

import net.amentum.niomedic.catalogos.model.CatMotivosEnvio;
import net.amentum.niomedic.catalogos.views.CatMotivosEnvioView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class CatMotivosEnvioConverter {
   private Logger logger = LoggerFactory.getLogger(CatMotivosEnvioConverter.class);

   public CatMotivosEnvio toEntity(CatMotivosEnvioView catMotivosEnvioView, CatMotivosEnvio catMotivosEnvio, Boolean update) {
      catMotivosEnvio.setMotivosEnvioDescripcion(catMotivosEnvioView.getMotivosEnvioDescripcion().toUpperCase());
      catMotivosEnvio.setActivo(catMotivosEnvioView.getActivo());
      catMotivosEnvio.setDatosBusqueda(catMotivosEnvioView.getMotivosEnvioDescripcion().toUpperCase());
      catMotivosEnvio.setFechaUltimaModificacion(new Date());

      logger.debug("convertir CatMotivosEnvioView to Entity: {}", catMotivosEnvio);
      return catMotivosEnvio;
   }

   public CatMotivosEnvioView toView(CatMotivosEnvio catMotivosEnvio, Boolean completeConversion) {
      CatMotivosEnvioView catMotivosEnvioView = new CatMotivosEnvioView();
      catMotivosEnvioView.setIdCatMotivosEnvio(catMotivosEnvio.getIdCatMotivosEnvio());
      catMotivosEnvioView.setMotivosEnvioDescripcion(catMotivosEnvio.getMotivosEnvioDescripcion());
      catMotivosEnvioView.setActivo(catMotivosEnvio.getActivo());
      catMotivosEnvioView.setFechaUltimaModificacion(catMotivosEnvio.getFechaUltimaModificacion());

      logger.debug("convertir CatMotivosEnvio to View: {}", catMotivosEnvioView);
      return catMotivosEnvioView;
   }
}