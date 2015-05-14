/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package warehouseejb;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.UserTransaction;
import warehouseejb.exceptions.NonexistentEntityException;
import warehouseejb.exceptions.PreexistingEntityException;
import warehouseejb.exceptions.RollbackFailureException;

/**
 *
 * @author tiago
 */
public class BookOrderJpaController implements Serializable {
    
    public BookOrderJpaController() {
        this.emf = Persistence.createEntityManagerFactory("warehouse-ejbPU");
        this.etx = emf.createEntityManager().getTransaction();
    }
    
    private EntityTransaction etx = null;
    private EntityManagerFactory emf = null;
    
    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    
    public void create(BookOrder bookOrder) throws PreexistingEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            etx.begin();
            em = getEntityManager();
            em.persist(bookOrder);
            etx.commit();
        } catch (Exception ex) {
            try {
                etx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findBookOrder(bookOrder.getId()) != null) {
                throw new PreexistingEntityException("BookOrder " + bookOrder + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    
    public void edit(BookOrder bookOrder) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            etx.begin();
            em = getEntityManager();
            bookOrder = em.merge(bookOrder);
            etx.commit();
        } catch (Exception ex) {
            try {
                etx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = bookOrder.getId();
                if (findBookOrder(id) == null) {
                    throw new NonexistentEntityException("The bookOrder with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    
    public void destroy(Integer id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            etx.begin();
            em = getEntityManager();
            BookOrder bookOrder;
            try {
                bookOrder = em.getReference(BookOrder.class, id);
                bookOrder.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The bookOrder with id " + id + " no longer exists.", enfe);
            }
            em.remove(bookOrder);
            etx.commit();
        } catch (Exception ex) {
            try {
                etx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    
    public List<BookOrder> findBookOrderEntities() {
        return findBookOrderEntities(true, -1, -1);
    }
    
    public List<BookOrder> findBookOrderEntities(int maxResults, int firstResult) {
        return findBookOrderEntities(false, maxResults, firstResult);
    }
    
    private List<BookOrder> findBookOrderEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(BookOrder.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }
    
    public BookOrder findBookOrder(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(BookOrder.class, id);
        } finally {
            em.close();
        }
    }
    
    public int getBookOrderCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<BookOrder> rt = cq.from(BookOrder.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}