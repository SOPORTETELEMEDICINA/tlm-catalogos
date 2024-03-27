package net.amentum.niomedic.catalogos.service;

import net.amentum.niomedic.catalogos.exception.CatNacionalidadesException;
import net.amentum.niomedic.catalogos.views.CatNacionalidadesView;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CatNacionalidadesService {

	CatNacionalidadesView createNacionalidades(CatNacionalidadesView catNacionalidadesView) throws CatNacionalidadesException;

	CatNacionalidadesView updateNacionalidades(Integer idCatNacionalidades, CatNacionalidadesView catNacionalidadesView) throws CatNacionalidadesException;

	CatNacionalidadesView getDetailsByIdCatNacionalidades(Integer idCatNacionalidades) throws CatNacionalidadesException;

	List<CatNacionalidadesView> findAll() throws CatNacionalidadesException;

	Page<CatNacionalidadesView> getCatNacionalidadesSearch(String datosBusqueda,Boolean activo, Integer page, Integer size, String orderColumn, String orderType) throws CatNacionalidadesException;
}
