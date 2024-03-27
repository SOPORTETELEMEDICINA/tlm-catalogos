package net.amentum.niomedic.catalogos.converter;

import net.amentum.niomedic.catalogos.model.CatNacionalidades;
import net.amentum.niomedic.catalogos.views.CatNacionalidadesView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CatNacionalidadesConverter {
	private Logger logger = LoggerFactory.getLogger(CatNacionalidadesConverter.class);

	public CatNacionalidadesView toView(CatNacionalidades catNacionalidades, Boolean completeConversion){
		CatNacionalidadesView catNacionalidadesView = new CatNacionalidadesView();
		catNacionalidadesView.setIdCatNacionalidades(catNacionalidades.getIdCatNacionalidades());
		catNacionalidadesView.setNacPaisCodigo(catNacionalidades.getNacPaisCodigo());
		catNacionalidadesView.setNacPaisDescripcion(catNacionalidades.getNacPaisDescripcion());
		catNacionalidadesView.setNacPaisClave(catNacionalidades.getNacPaisClave());
		catNacionalidadesView.setActivo(catNacionalidades.getActivo());
		logger.debug("convertir catNacionalidades to View: {}", catNacionalidadesView);
		return catNacionalidadesView;
	}

	/**
	 * Método para convertir un modelo vista a uno entidad
	 * @param CatNacionalidadesView modelo vista que será convertido a un modelo entidad
	 * @param CatNacionalidades modelo entidad que almacena el valor del modelo vista
	 * @return CatNacionalidades modelo entidad que se agregara a la DB
	 **/
	public CatNacionalidades toEntity(CatNacionalidadesView view,CatNacionalidades entity, Boolean update) {
		if(update) {
			entity.setIdCatNacionalidades(view.getIdCatNacionalidades());
		}
		entity.setNacPaisCodigo(view.getNacPaisCodigo());
		entity.setNacPaisDescripcion(view.getNacPaisDescripcion().toUpperCase());
		entity.setNacPaisClave(view.getNacPaisClave().toUpperCase());
		entity.setActivo(view.getActivo());
		entity.setDatosBusqueda(view.getNacPaisDescripcion().toUpperCase());
		logger.debug("Convirtiendo una modelo vita a uno entidad - {}",view);
		return entity;
	}

}
