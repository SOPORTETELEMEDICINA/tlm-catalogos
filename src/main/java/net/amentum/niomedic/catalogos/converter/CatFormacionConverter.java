package net.amentum.niomedic.catalogos.converter;

import net.amentum.niomedic.catalogos.model.CatFormacion;
import net.amentum.niomedic.catalogos.views.CatFormacionView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CatFormacionConverter {
	private Logger logger = LoggerFactory.getLogger(CatFormacionConverter.class);

	public CatFormacionView toView(CatFormacion catFormacion, Boolean completeConversion){
		CatFormacionView catFormacionView = new CatFormacionView();
		catFormacionView.setIdCatFormacion(catFormacion.getIdCatFormacion());
		catFormacionView.setFormDescripcion(catFormacion.getFormDescripcion());
		catFormacionView.setFormAgrupacion(catFormacion.getFormAgrupacion());
		catFormacionView.setFormGrado(catFormacion.getFormGrado());
		catFormacionView.setActivo(catFormacion.getActivo());
		logger.debug("convertir catFormacion to View: {}", catFormacionView);
		return catFormacionView;
	}

	public CatFormacion toEntity(CatFormacion entity, CatFormacionView view, Boolean update) {
		if(update){
			entity.setIdCatFormacion(view.getIdCatFormacion());
		}
		entity.setFormDescripcion(view.getFormDescripcion().toUpperCase());
		entity.setFormAgrupacion(view.getFormAgrupacion().toUpperCase());
		entity.setFormGrado(view.getFormGrado());
		entity.setDatosBusqueda(view.getFormDescripcion().toUpperCase()+" "+view.getFormAgrupacion().toUpperCase());
		entity.setActivo(view.getActivo());
		logger.debug("Convirtiendo un modelo vista a una entidad, {}", entity );
		return entity;
	}
}
