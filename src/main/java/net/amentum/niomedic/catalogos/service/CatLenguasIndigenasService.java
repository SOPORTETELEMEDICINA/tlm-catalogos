package net.amentum.niomedic.catalogos.service;

import net.amentum.niomedic.catalogos.exception.CatLenguasIndigenasException;
import net.amentum.niomedic.catalogos.views.CatLenguasIndigenasView;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CatLenguasIndigenasService {
	/**
	 * Método para agregar una nueva leguna indegena
	 * @param catLenguasIndigenasView lengua indigena que se va a guardar
	 * @return CatLenguasIndigenasView lengua indigena que se guardo mas valores agregados por la DB
	 * @throws CatLenguasIndigenasException exception que regresara cuando ocurra algun error
	 * */
	CatLenguasIndigenasView createCatLenguasIndigenas(CatLenguasIndigenasView catLenguasIndigenasView) throws CatLenguasIndigenasException;
	
	/**
	 * Método para actualizar una leguna indegena
	 * @Param idCatLenguasIndigenas id de la legua indígena que se modificara
	 * @param catLenguasIndigenasView lengua indigena que se va a actualizar
	 * @return CatLenguasIndigenasView lengua indigena con los nuevos valores almacenados
	 * @throws CatLenguasIndigenasException exception que regresara cuando ocurra algun error
	 * */
	CatLenguasIndigenasView updateCatLenguasIndigenas(Integer idCatLenguasIndigenas, CatLenguasIndigenasView catLenguasIndigenasView) throws CatLenguasIndigenasException;

	
	CatLenguasIndigenasView getDetailsByIdCatLenguasIndigenas(Integer idCatLenguasIndigenas) throws CatLenguasIndigenasException;

	List<CatLenguasIndigenasView> findAll() throws CatLenguasIndigenasException;

	Page<CatLenguasIndigenasView> getCatLenguasIndigenasSearch(String datosBusqueda,Boolean activo, Integer page, Integer size, String orderColumn, String orderType) throws CatLenguasIndigenasException;
}
