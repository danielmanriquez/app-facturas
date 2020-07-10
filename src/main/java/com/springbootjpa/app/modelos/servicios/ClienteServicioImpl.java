package com.springbootjpa.app.modelos.servicios;

import com.springbootjpa.app.modelos.daos.IClienteDAO;
import com.springbootjpa.app.modelos.entidades.Cliente;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service("clienteServicioImpl")
@Transactional
public class ClienteServicioImpl implements ClienteServicio {

    private final IClienteDAO clienteDao;

    @Transactional(readOnly = true)
    @Override
    public List<Cliente> listarClientes() {

        return this.clienteDao.findAll();

    }

    @Transactional
    @Override
    public void guardarCliente(Cliente cliente) {

        this.clienteDao.save(cliente);
    }

    @Transactional(readOnly = true)
    @Override
    public Cliente buscarClientePorId(Long idCliente) {

        return this.clienteDao.findById(idCliente).orElse(null);

    }

    @Transactional
    @Override
    public void eliminarCliente(Long idCliente) {

        this.clienteDao.delete(this.clienteDao.findById(idCliente).orElse(null));

    }

    @Transactional(readOnly = true)
    @Override
    public Page<Cliente> listarClientes(Pageable pageable) {

        return clienteDao.findAll(pageable);
    }

}
