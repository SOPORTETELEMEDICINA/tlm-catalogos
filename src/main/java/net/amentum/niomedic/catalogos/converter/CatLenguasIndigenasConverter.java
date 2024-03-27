package net.amentum.niomedic.catalogos.converter;

import net.amentum.niomedic.catalogos.model.CatLenguasIndigenas;
import net.amentum.niomedic.catalogos.views.CatLenguasIndigenasView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CatLenguasIndigenasConverter {
	private Logger logger = LoggerFactory.getLogger(CatLenguasIndigenasConverter.class);

	public CatLenguasIndigenasView toView(CatLenguasIndigenas catLenguasIndigenas, Boolean completeConversion){
		CatLenguasIndigenasView catLenguasIndigenasView = new CatLenguasIndigenasView();
		catLenguasIndigenasView.setIdCatLenguasIndigenas(catLenguasIndigenas.getIdCatLenguasIndigenas());
		catLenguasIndigenasView.setLengCatalogKey(catLenguasIndigenas.getLengCatalogKey());
		catLenguasIndigenasView.setLengDescripcion(catLenguasIndigenas.getLengDescripcion());
		catLenguasIndigenasView.setActivo(catLenguasIndigenas.getActivo());
		logger.debug("convertir catLenguasIndigenas to View: {}", catLenguasIndigenasView);
		return catLenguasIndigenasView;
	}

	/**
	 *Comvierte un modelo vista a una entidad
	 *@param view modelo vista que llega de la petición REST
	 *@param entity modelo entidad en la que se guardará en la DB
	 *@param update indica si es una actualización en el modelo entidad
	 *@return CatLenguasIndigenas modelo entidad que será guardado en la DB
	 **/
	public CatLenguasIndigenas toEntity(CatLenguasIndigenasView view, CatLenguasIndigenas entity, Boolean update) {
		if(update) {
			entity.setIdCatLenguasIndigenas(view.getIdCatLenguasIndigenas());
		}
		entity.setLengDescripcion(view.getLengDescripcion());
		entity.setDatosBusqueda(view.getLengDescripcion());
		entity.setActivo(view.getActivo());
		entity.setLengCatalogKey(view.getLengCatalogKey());
		return entity;
	}

}
