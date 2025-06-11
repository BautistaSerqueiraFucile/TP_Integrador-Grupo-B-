package org.example.msvcfacturacion.services;

import org.example.msvcfacturacion.entities.Factura;
import org.example.msvcfacturacion.repositories.FacturacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FacturacionService {

    @Autowired
    private FacturacionRepository repoFacturacion;

    @Transactional
    public List<Factura> findAll() throws Exception {
        return repoFacturacion.findAll();
    }

    @Transactional
    public Factura save(Factura factura) throws Exception {
        return repoFacturacion.save(factura);
    }

    @Transactional
    public Factura findById(Long id) {
        return repoFacturacion.findById(id).get();
    }
}
