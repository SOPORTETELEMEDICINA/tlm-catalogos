package net.amentum.niomedic.catalogos.service;

import net.amentum.niomedic.catalogos.exception.CatEstadoConsultaException;
import net.amentum.niomedic.catalogos.views.CatEstadoConsultaView;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CatEstadoConsultaService {
   CatEstadoConsultaView getDetailsByIdCatEstadoConsulta(Integer idEstadoConsulta) throws CatEstadoConsultaException;

   List<CatEstadoConsultaView> findAll() throws CatEstadoConsultaException;

   Page<CatEstadoConsultaView> getCatEstadoConsultaSearch(String datosBusqueda, Integer page, Integer size, String orderColumn, String orderType) throws CatEstadoConsultaException;
}
