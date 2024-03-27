package net.amentum.niomedic.catalogos.service;

import net.amentum.niomedic.catalogos.exception.CatSeAutodenominaAfromexicanoException;
import net.amentum.niomedic.catalogos.views.CatSeAutodenominaAfromexicanoView;

import java.util.List;

public interface CatSeAutodenominaAfromexicanoService {

   CatSeAutodenominaAfromexicanoView getDetailsByidSeAutodenominaAfromexicano(Integer idSeAutodenominaAfromexicano) throws CatSeAutodenominaAfromexicanoException;

    List<CatSeAutodenominaAfromexicanoView> findAll() throws CatSeAutodenominaAfromexicanoException;


}

