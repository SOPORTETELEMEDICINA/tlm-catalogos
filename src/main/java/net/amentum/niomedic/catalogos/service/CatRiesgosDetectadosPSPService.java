package net.amentum.niomedic.catalogos.service;

import net.amentum.niomedic.catalogos.exception.CatRiesgosDetectadosPSPException;
import net.amentum.niomedic.catalogos.views.CatRiesgosDetectadosView;

import java.util.List;

public interface CatRiesgosDetectadosPSPService {

   CatRiesgosDetectadosView getDetailsByidRiesgo(Integer idRiesgosDetectados) throws CatRiesgosDetectadosPSPException;

    List<CatRiesgosDetectadosView> findAll() throws CatRiesgosDetectadosPSPException;



}

