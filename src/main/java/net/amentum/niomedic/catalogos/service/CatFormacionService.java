package net.amentum.niomedic.catalogos.service;

import net.amentum.niomedic.catalogos.exception.CatFormacionException;
import net.amentum.niomedic.catalogos.views.CatFormacionView;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CatFormacionService {
	CatFormacionView creteCatFormacion(CatFormacionView catFormacionView) throws CatFormacionException;
	
	CatFormacionView updateCatFormacion(Integer idCatFormacion, CatFormacionView catFormacionView) throws CatFormacionException;

	CatFormacionView getDetailsByIdCatFormacion(Integer idCatFormacion) throws CatFormacionException;

	List<CatFormacionView> findAll() throws CatFormacionException;

	Page<CatFormacionView> getCatFormacionSearch(String datosBusqueda,Boolean activo, Integer page, Integer size, String orderColumn, String orderType) throws CatFormacionException;
}
