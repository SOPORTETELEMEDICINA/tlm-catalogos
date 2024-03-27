package net.amentum.niomedic.catalogos.service;

import net.amentum.niomedic.catalogos.exception.CatEdasPlanTratamientoPSPException;
import net.amentum.niomedic.catalogos.views.CatEdasPlanTratamientoView;

import java.util.List;

public interface CatEdasPlanTratamientoPSPService {

   CatEdasPlanTratamientoView getDetailsByidPlanTratamiento(Integer idEdasPlanTratamiento) throws CatEdasPlanTratamientoPSPException;

    List<CatEdasPlanTratamientoView> findAll() throws CatEdasPlanTratamientoPSPException;


}

