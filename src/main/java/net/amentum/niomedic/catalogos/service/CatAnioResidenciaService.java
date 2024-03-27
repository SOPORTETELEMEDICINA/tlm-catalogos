package net.amentum.niomedic.catalogos.service;

import net.amentum.niomedic.catalogos.exception.CatAnioResidenciaException;
import net.amentum.niomedic.catalogos.views.CatAnioResidenciaView;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CatAnioResidenciaService {

   CatAnioResidenciaView getDetailsByidAnio(Integer idAnioResidencia) throws CatAnioResidenciaException;

    List<CatAnioResidenciaView> findAll() throws CatAnioResidenciaException;

    Page<CatAnioResidenciaView> getCatAnioResidenciaSearch(String clave_anio_de_residencia, Integer page, Integer size, String orderColumn, String orderType) throws CatAnioResidenciaException;


}

