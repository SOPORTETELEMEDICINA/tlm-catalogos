package net.amentum.niomedic.catalogos.service;

import net.amentum.niomedic.catalogos.exception.CatDolometroException;
import net.amentum.niomedic.catalogos.views.CatDolometroView;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CatDolometroService {
   CatDolometroView getDetailsByIdCatDolometro(Integer idCatDolometro) throws CatDolometroException;

   List<CatDolometroView> findAll() throws CatDolometroException;

   Page<CatDolometroView> getCatDolometroSearch(String datosBusqueda, Integer page, Integer size, String orderColumn, String orderType) throws CatDolometroException;
}
