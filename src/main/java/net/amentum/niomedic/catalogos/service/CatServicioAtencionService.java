package net.amentum.niomedic.catalogos.service;

import net.amentum.niomedic.catalogos.exception.CatServicioAtencionException;
import net.amentum.niomedic.catalogos.views.CatServicioAtencionView;

import java.util.List;

public interface CatServicioAtencionService {

   CatServicioAtencionView getDetailsByidServicioAtencion(Integer idServicioAtencion) throws CatServicioAtencionException;

    List<CatServicioAtencionView> findAll() throws CatServicioAtencionException;


}

