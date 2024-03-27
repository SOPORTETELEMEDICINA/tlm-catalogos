package net.amentum.niomedic.catalogos.service;

import net.amentum.niomedic.catalogos.exception.CatReligionException;
import net.amentum.niomedic.catalogos.views.CatReligionView;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CatReligionService {

	/**
	 *Método para agrega una nueva religión en el catálogo de religiones
	 *@param catReligionView representa la información que se almacenará en la DB
	 *@return CatReligionView regresa la información que se ingresó complementada con los ids de la DB
	 **/
	CatReligionView createCatReligion(CatReligionView catReligionView)throws CatReligionException;
	/**
	 *Método para actualizar un registro de catReligiones en DB
	 *@param catReligionView representa la información que se almacenará en la DB
	 *@param idCatReligion al cual se quiere actualizar
	 *@return CatReligionView regresa la información que se ingresó complementada con los ids de la DB
	 **/
	CatReligionView updateCatReligion(CatReligionView catReligionView, Integer idCatReligion)throws CatReligionException;
	
	
	CatReligionView getDetailsByIdCatReligion(Integer idCatReligion) throws CatReligionException;

	List<CatReligionView> findAll() throws CatReligionException;

	Page<CatReligionView> getCatReligionSearch(String datosBusqueda, Boolean activo, Integer page, Integer size, String orderColumn, String orderType) throws CatReligionException;
}
