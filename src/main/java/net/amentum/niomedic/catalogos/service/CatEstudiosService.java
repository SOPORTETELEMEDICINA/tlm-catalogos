package net.amentum.niomedic.catalogos.service;

import org.springframework.data.domain.Page;

import net.amentum.niomedic.catalogos.exception.CatEstudiosException;
import net.amentum.niomedic.catalogos.views.CatEstudiosView;

public interface CatEstudiosService {
	
	CatEstudiosView createCatEstudio(CatEstudiosView catEstudiosView) throws CatEstudiosException;
	
	CatEstudiosView updateCatEstudio(Integer idCatEstudio, CatEstudiosView catEstudiosView) throws CatEstudiosException;

	CatEstudiosView getCatEstudios(Integer idCatEstudio) throws CatEstudiosException;
	
	Page<CatEstudiosView> searchCatEstudios(String datosBusqueda, Boolean activo, Integer page, Integer size, String orderColumn, String orderType)throws CatEstudiosException;

}
