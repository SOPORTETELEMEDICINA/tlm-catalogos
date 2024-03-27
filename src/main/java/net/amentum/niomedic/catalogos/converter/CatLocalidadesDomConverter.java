package net.amentum.niomedic.catalogos.converter;

import net.amentum.niomedic.catalogos.model.CatLocalidadesDom;
import net.amentum.niomedic.catalogos.views.CatLocalidadesDomView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CatLocalidadesDomConverter {
   private Logger logger = LoggerFactory.getLogger(CatLocalidadesDomConverter.class);

   public CatLocalidadesDomView toView(CatLocalidadesDom catLocalidadesDom, Boolean completeConversion){
      CatLocalidadesDomView catLocalidadesDomView = new CatLocalidadesDomView();
      catLocalidadesDomView.setIdCatLocalidades(catLocalidadesDom.getIdCatLocalidades());
      catLocalidadesDomView.setCatalogKey(catLocalidadesDom.getCatalogKey());
      catLocalidadesDomView.setDescripcionLocalidades(catLocalidadesDom.getDescripcionLocalidades());
      catLocalidadesDomView.setEfeKey(catLocalidadesDom.getEfeKey());
      catLocalidadesDomView.setMunKey(catLocalidadesDom.getMunKey());

      logger.debug("convertir catLocalidadesDom to View: {}", catLocalidadesDomView);
      return catLocalidadesDomView;
   }

}
