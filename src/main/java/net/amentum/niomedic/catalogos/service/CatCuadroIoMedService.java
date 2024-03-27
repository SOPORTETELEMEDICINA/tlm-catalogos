package net.amentum.niomedic.catalogos.service;

import net.amentum.niomedic.catalogos.exception.CatCuadroIoMedException;
import net.amentum.niomedic.catalogos.views.CatCuadroIoMedView;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CatCuadroIoMedService {
   CatCuadroIoMedView getDetailsByIdCatCuadroIoMed(Integer idCatCuadroIoMed) throws CatCuadroIoMedException;

   List<CatCuadroIoMedView> findAll() throws CatCuadroIoMedException;

   Page<CatCuadroIoMedView> getCatCuadroIoMedSearch(String datosBusqueda, Integer page, Integer size, String orderColumn, String orderType) throws CatCuadroIoMedException;
}
