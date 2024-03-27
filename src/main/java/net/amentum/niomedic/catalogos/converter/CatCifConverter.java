package net.amentum.niomedic.catalogos.converter;

import net.amentum.niomedic.catalogos.model.CatCif;
import net.amentum.niomedic.catalogos.views.CatCifView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class CatCifConverter {
   private Logger logger = LoggerFactory.getLogger(CatCifConverter.class);

   public CatCif toEntity(CatCifView catCifView, CatCif catCif, Boolean update){
      catCif.setDiscCodigo(catCifView.getDiscCodigo().toUpperCase());
      catCif.setDiscDescripcion(catCifView.getDiscDescripcion().toUpperCase());
      catCif.setActivo(catCifView.getActivo());
      catCif.setDatosBusqueda(catCifView.getDiscCodigo().toUpperCase() +" "+catCifView.getDiscDescripcion().toUpperCase());
      catCif.setFechaUltimaModificacion(new Date());

      logger.debug("convertir CatCifView to Entity: {}", catCif);
      return catCif;
   }

   public CatCifView toView(CatCif catCif, Boolean completeConversion){
      CatCifView catCifView = new CatCifView();
      catCifView.setIdCatCif(catCif.getIdCatCif());
      catCifView.setDiscCodigo(catCif.getDiscCodigo());
      catCifView.setDiscDescripcion(catCif.getDiscDescripcion());
      catCifView.setActivo(catCif.getActivo());
      catCifView.setFechaUltimaModificacion(catCif.getFechaUltimaModificacion());

      logger.debug("convertir CatCif to View: {}", catCifView);
      return catCifView;
   }

}
