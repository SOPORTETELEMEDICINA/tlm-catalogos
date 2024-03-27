package net.amentum.niomedic.catalogos.service;

import net.amentum.niomedic.catalogos.exception.CatCapitulosCie10Exception;
import net.amentum.niomedic.catalogos.views.CatCapitulosCie10View;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CatCapitulosCie10Service {
   CatCapitulosCie10View getDetailsByIdCatCapitulosCie10(Integer idCatCapitulosCie10) throws CatCapitulosCie10Exception;

   List<CatCapitulosCie10View> findAll() throws CatCapitulosCie10Exception;

   Page<CatCapitulosCie10View> getCatCapitulosCie10Search(String datosBusqueda, Integer page, Integer size, String orderColumn, String orderType) throws CatCapitulosCie10Exception;
}
