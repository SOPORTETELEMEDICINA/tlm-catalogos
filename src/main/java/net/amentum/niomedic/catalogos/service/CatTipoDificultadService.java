package net.amentum.niomedic.catalogos.service;

import net.amentum.niomedic.catalogos.exception.CatTipoDificultadException;
import net.amentum.niomedic.catalogos.views.CatTipoDificultadView;

import java.util.List;

public interface CatTipoDificultadService {

   CatTipoDificultadView getDetailsByidTipoDificultad(Integer idTipoDificultad) throws CatTipoDificultadException;

    List<CatTipoDificultadView> findAll() throws CatTipoDificultadException;


}

