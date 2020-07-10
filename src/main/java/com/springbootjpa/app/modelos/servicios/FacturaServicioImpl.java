package com.springbootjpa.app.modelos.servicios;

import com.springbootjpa.app.modelos.daos.IFacturaDAO;
import com.springbootjpa.app.modelos.entidades.Factura;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class FacturaServicioImpl implements FacturaServicio {

    private final IFacturaDAO facturaDao;

    @Transactional
    @Override
    public void guardarFactura(Factura factura) {

        this.facturaDao.save(factura);

    }

    @Transactional(readOnly = true)
    @Override
    public Factura buscarFacturaPorId(Long idFactura) {

        return this.facturaDao.findById(idFactura).orElse(null);
    }

}
