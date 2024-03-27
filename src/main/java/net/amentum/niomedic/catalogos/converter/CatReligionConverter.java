package net.amentum.niomedic.catalogos.converter;

import net.amentum.niomedic.catalogos.model.CatReligion;
import net.amentum.niomedic.catalogos.views.CatReligionView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CatReligionConverter {
   private Logger logger = LoggerFactory.getLogger(CatReligionConverter.class);

   public CatReligionView toView(CatReligion catReligion, Boolean completeConversion){
      CatReligionView catReligionView = new CatReligionView();
      catReligionView.setIdCatReligion(catReligion.getIdCatReligion());
      catReligionView.setRelDescripcion(catReligion.getRelDescripcion());
      catReligionView.setActivo(catReligion.getActivo());
      logger.debug("convertir catReligion to View: {}", catReligionView);
      return catReligionView;
   }
   
   /**
    *Convierte un modelo vista dr catReligion a un modelo entidad
    *@param catReligionView modelo vista a convertir
    *@param catReligion modelo entidad que obtendra los valores del modelo vista
    *@param update indica si es o no una actualizacion
    *@return CatReligion modelo entidad que se guardará en la DB
    **/
   public CatReligion toEntity(CatReligionView catReligionView, CatReligion catReligion, Boolean update){
	   logger.info("convertiendo de modelo vista a entidad ", catReligionView);
	   if(update) {
		   catReligion.setIdCatReligion(catReligionView.getIdCatReligion());
	   }
	   catReligion.setRelDescripcion(catReligionView.getRelDescripcion());	  
	   catReligion.setDatosBusqueda(catReligionView.getRelDescripcion());
	   catReligion.setActivo(catReligionView.getActivo());
	   logger.info("Mondelo entidad: {}", catReligion);
	   return catReligion;
   }

}
