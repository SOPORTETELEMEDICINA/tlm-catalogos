package net.amentum.niomedic.catalogos.service;

import net.amentum.niomedic.catalogos.exception.CatTipoplazaException;
import net.amentum.niomedic.catalogos.views.CatTipoplazaView;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CatTipoplazaService {

    CatTipoplazaView getDetailsByplaid(Integer plaid) throws CatTipoplazaException;

    List<CatTipoplazaView> findAll() throws CatTipoplazaException;

    Page<CatTipoplazaView> getCatTipoplazaSearch(String datosBusqueda, Integer page, Integer size, String orderColumn, String orderType) throws CatTipoplazaException;

}



