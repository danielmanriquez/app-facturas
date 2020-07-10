package com.springbootjpa.app.modelos.daos;

import com.springbootjpa.app.modelos.entidades.Cliente;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository("clienteDAOImpl")
public class ClienteDAOImpl implements ClienteDAO {
    
    /** Se inyecta el EntityManager para hacer las operaciones sobre la base de datos **/
    @PersistenceContext
    private EntityManager em;

    /**
     * Metodo que retorna una lista de clientes desde la base de datos , creando una query con JPQL
     * @return List Cliente
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Cliente> findAll() {

        return em.createQuery("from Cliente").getResultList();

    }

    /**
     *Metodo para guardar una entidad Cliente en la base de datos ,
     * si este tiene tiene ya un id , hara un update al registro en la base de datos ,
     * si no lo tiene creara un nuevo registro.
     * 
     * @param cliente
     */
    @Override
    public void save(Cliente cliente) {

        if (cliente.getIdCliente() != null && cliente.getIdCliente() > 0) {

            em.merge(cliente);
        } else {

            em.persist(cliente);
        }

    }

    /**
     *  Metodo que retorna un cliente de la base de datos pasando como parametro el id . 
     * 
     * @param idCliente
     * @return Cliente
     */
    @Override
    public Cliente findById(Long idCliente) {

        return em.find(Cliente.class, idCliente);

    }
    
    /**
     * Metodo que elimina un cliente en la base de datos pasando como parametro el id.
     * @param idCliente
     */
    @Override
    public void delete(Long idCliente) {

        em.remove(em.merge(findById(idCliente)));

    }

}
