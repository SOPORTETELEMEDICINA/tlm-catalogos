package net.amentum.niomedic.catalogos.service;

import net.amentum.niomedic.catalogos.exception.CatViaAdministracionException;
import net.amentum.niomedic.catalogos.views.CatViaAdministracionView;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CatViaAdministracionService {
	
	CatViaAdministracionView createViaAdministracion(CatViaAdministracionView catViaAdministracionView) throws CatViaAdministracionException;
	
	CatViaAdministracionView updateViaAdministracion(Integer idCatViadAdministracion,CatViaAdministracionView catViaAdministracionView) throws CatViaAdministracionException;
	
	CatViaAdministracionView getDetailsByIdCatViaAdministracion(Integer idCatViaAdministracion) throws CatViaAdministracionException;

	List<CatViaAdministracionView> findAll() throws CatViaAdministracionException;

	Page<CatViaAdministracionView> getCatViaAdministracionSearch(String datosBusqueda,Boolean activo, Integer page, Integer size, String orderColumn, String orderType) throws CatViaAdministracionException;
}
