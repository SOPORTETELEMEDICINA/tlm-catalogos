package net.amentum.niomedic.catalogos.converter;

import org.springframework.stereotype.Component;

import net.amentum.niomedic.catalogos.model.CatEstudios;
import net.amentum.niomedic.catalogos.views.CatEstudiosView;

@Component
public class CatEstudiosConverter {
	
	public CatEstudios toEntity(CatEstudiosView view, CatEstudios entity, Boolean update) {
		if(update) {
			entity.setIdCatEstudio(view.getIdCatEstudio());	
		}
		entity.setDescripcion(view.getDescripcion());
		entity.setPreparacion(view.getPreparacion());
		entity.setEstudio(view.getEstudio());
		entity.setActivo(view.getActivo());
		return entity;
	}
	
	public CatEstudiosView toView(CatEstudios entity, Boolean complete) {
		CatEstudiosView view = new CatEstudiosView();
		view.setIdCatEstudio(entity.getIdCatEstudio());
		view.setDescripcion(entity.getDescripcion());
		view.setPreparacion(entity.getPreparacion());
		view.setEstudio(entity.getEstudio());
		view.setActivo(entity.getActivo());
		return view;
	}
}
