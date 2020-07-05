package com.springbootjpa.app.modelos.daos;

import com.springbootjpa.app.modelos.entidades.Cliente;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository("clienteDAOImpl")
public class ClienteDAOImpl implements ClienteDAO {

    @PersistenceContext
    private EntityManager em;

    @SuppressWarnings("unchecked")
    @Override
    public List<Cliente> findAll() {

        return em.createQuery("from Cliente").getResultList();

    }

    @Override
    public void save(Cliente cliente) {

        if (cliente.getIdCliente() != null && cliente.getIdCliente() > 0) {

            em.merge(cliente);
        } else {

            em.persist(cliente);
        }

    }

    @Override
    public Cliente findById(Long idCliente) {

        return em.find(Cliente.class, idCliente);

    }

    @Override
    public void delete(Long idCliente) {

        em.remove(em.merge(findById(idCliente)));

    }

}
