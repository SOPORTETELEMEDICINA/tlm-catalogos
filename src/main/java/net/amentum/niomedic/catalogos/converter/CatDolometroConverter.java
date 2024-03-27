package net.amentum.niomedic.catalogos.converter;

import net.amentum.niomedic.catalogos.model.CatDolometro;
import net.amentum.niomedic.catalogos.views.CatDolometroView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CatDolometroConverter {
   private Logger logger = LoggerFactory.getLogger(CatDolometroConverter.class);

   public CatDolometroView toView(CatDolometro catDolometro, Boolean completeConversion){
      CatDolometroView catDolometroView = new CatDolometroView();
      catDolometroView.setIdCatDolometro(catDolometro.getIdCatDolometro());
      catDolometroView.setNivel(catDolometro.getNivel());
      catDolometroView.setDoloDescripcion(catDolometro.getDoloDescripcion());

      logger.debug("convertir catDolometro to View: {}", catDolometroView);
      return catDolometroView;
   }

}
