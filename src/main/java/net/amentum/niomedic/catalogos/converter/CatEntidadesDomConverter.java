package net.amentum.niomedic.catalogos.converter;

import net.amentum.niomedic.catalogos.model.CatEntidadesDom;
import net.amentum.niomedic.catalogos.views.CatEntidadesDomView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CatEntidadesDomConverter {
   private Logger logger = LoggerFactory.getLogger(CatEntidadesDomConverter.class);

   public CatEntidadesDomView toView(CatEntidadesDom catEntidadesDom, Boolean completeConversion){
      CatEntidadesDomView catEntidadesDomView = new CatEntidadesDomView();
      catEntidadesDomView.setIdCatEntidades(catEntidadesDom.getIdCatEntidades());
      catEntidadesDomView.setCatalogKey(catEntidadesDom.getCatalogKey());
      catEntidadesDomView.setDescripcionEntidades(catEntidadesDom.getDescripcionEntidades());
      catEntidadesDomView.setAbreviatura(catEntidadesDom.getAbreviatura());

      logger.debug("convertir catEntidadesDom to View: {}", catEntidadesDomView);
      return catEntidadesDomView;
   }

}
