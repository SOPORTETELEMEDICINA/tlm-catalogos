package net.amentum.niomedic.catalogos.service;

import net.amentum.niomedic.catalogos.exception.CatIrasPlanTratamientoPSPException;
import net.amentum.niomedic.catalogos.views.CatIrasPlanTratamientoView;

import java.util.List;

public interface CatIrasPlanTratamientoPSPService {

   CatIrasPlanTratamientoView getDetailsByidIras(Integer idIrasPlanTratamiento) throws CatIrasPlanTratamientoPSPException;

    List<CatIrasPlanTratamientoView> findAll() throws CatIrasPlanTratamientoPSPException;
    

}

