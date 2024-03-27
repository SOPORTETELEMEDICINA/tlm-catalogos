package net.amentum.niomedic.catalogos.converter;

import net.amentum.niomedic.catalogos.model.CatGpoTerapeuticoMed;
import net.amentum.niomedic.catalogos.views.CatGpoTerapeuticoMedView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CatGpoTerapeuticoMedConverter {
   private Logger logger = LoggerFactory.getLogger(CatGpoTerapeuticoMedConverter.class);

   public CatGpoTerapeuticoMedView toView(CatGpoTerapeuticoMed catGpoTerapeuticoMed, Boolean completeConversion){
      CatGpoTerapeuticoMedView catGpoTerapeuticoMedView = new CatGpoTerapeuticoMedView();
      catGpoTerapeuticoMedView.setIdCatGpoTerapeutico(catGpoTerapeuticoMed.getIdCatGpoTerapeutico());
      catGpoTerapeuticoMedView.setMedGpoTerapeutico(catGpoTerapeuticoMed.getMedGpoTerapeutico());
      catGpoTerapeuticoMedView.setDescripcionGpoTerapeutico(catGpoTerapeuticoMed.getDescripcionGpoTerapeutico());
      catGpoTerapeuticoMedView.setNivelAtencionGpoTerapeutico(catGpoTerapeuticoMed.getNivelAtencionGpoTerapeutico());

      logger.debug("convertir catGpoTerapeuticoMed to View: {}", catGpoTerapeuticoMedView);
      return catGpoTerapeuticoMedView;
   }

}
