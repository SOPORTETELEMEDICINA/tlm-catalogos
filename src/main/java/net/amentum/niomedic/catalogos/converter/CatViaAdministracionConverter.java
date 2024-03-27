package net.amentum.niomedic.catalogos.converter;

import net.amentum.niomedic.catalogos.model.CatViaAdministracion;
import net.amentum.niomedic.catalogos.views.CatViaAdministracionView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CatViaAdministracionConverter {
	private Logger logger = LoggerFactory.getLogger(CatViaAdministracionConverter.class);

	public CatViaAdministracionView toView(CatViaAdministracion catViaAdministracion, Boolean completeConversion){
		CatViaAdministracionView catViaAdministracionView = new CatViaAdministracionView();
		catViaAdministracionView.setIdCatViaAdministracion(catViaAdministracion.getIdCatViaAdministracion());
		catViaAdministracionView.setVaDescripcion(catViaAdministracion.getVaDescripcion());
		catViaAdministracionView.setActivo(catViaAdministracion.getActivo());
		logger.debug("convertir catViaAdministracion to View: {}", catViaAdministracionView);
		return catViaAdministracionView;
	}

	public CatViaAdministracion toEntity(CatViaAdministracionView view, CatViaAdministracion entity, Boolean update) {
		if(update) {
			entity.setIdCatViaAdministracion(view.getIdCatViaAdministracion());
		}
		entity.setActivo(view.getActivo());
		entity.setVaDescripcion(view.getVaDescripcion().toUpperCase());
		entity.setDatosBusqueda(view.getVaDescripcion().toUpperCase());		
		return entity;
	}

}
