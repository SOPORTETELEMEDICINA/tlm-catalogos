package net.amentum.niomedic.catalogos.service;

import net.amentum.niomedic.catalogos.exception.CatCifException;
import net.amentum.niomedic.catalogos.views.CatCifView;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CatCifService {

   CatCifView getDetailsByIdCatCif(Integer idCatCif) throws CatCifException;

   List<CatCifView> findAll() throws CatCifException;

   Page<CatCifView> getCatCifSearch(String datosBusqueda, Boolean activo, Integer page, Integer size, String orderColumn, String orderType) throws CatCifException;

   CatCifView createCatCif(CatCifView catCifView) throws CatCifException;

   CatCifView updateCatCif(CatCifView catCifView) throws CatCifException;
}
