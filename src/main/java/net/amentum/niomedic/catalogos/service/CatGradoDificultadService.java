package net.amentum.niomedic.catalogos.service;

import net.amentum.niomedic.catalogos.exception.CatGradoDificultadException;
import net.amentum.niomedic.catalogos.views.CatGradoDificultadView;

import java.util.List;

public interface CatGradoDificultadService {

   CatGradoDificultadView getDetailsByidGradoDificultad(Integer idGradoDificultad) throws CatGradoDificultadException;

    List<CatGradoDificultadView> findAll() throws CatGradoDificultadException;


}

