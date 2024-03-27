package net.amentum.niomedic.catalogos.service;

import net.amentum.niomedic.catalogos.exception.CatSexoException;
import net.amentum.niomedic.catalogos.views.CatSexoView;

import java.util.List;

public interface CatSexoService {

   CatSexoView getDetailsByidSexo(Integer idSexo) throws CatSexoException;

    List<CatSexoView> findAll() throws CatSexoException;


}

