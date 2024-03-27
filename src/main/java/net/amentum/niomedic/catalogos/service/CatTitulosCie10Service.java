package net.amentum.niomedic.catalogos.service;

import net.amentum.niomedic.catalogos.exception.CatTitulosCie10Exception;
import net.amentum.niomedic.catalogos.views.CatTitulosCie10View;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CatTitulosCie10Service {
   CatTitulosCie10View getDetailsByIdCatTitulosCie10(Integer idCatTitulosCie10) throws CatTitulosCie10Exception;

   List<CatTitulosCie10View> findAll() throws CatTitulosCie10Exception;

   Page<CatTitulosCie10View> getCatTitulosCie10Search(String datosBusqueda, Integer page, Integer size, String orderColumn, String orderType) throws CatTitulosCie10Exception;
}
