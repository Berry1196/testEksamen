package facades;

import dtos.HarbourDTO;
import entities.Harbour;
import utils.EMF_Creator;
import entities.Boats;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

public class HarbourFacade {
    private static HarbourFacade instance;
    private static EntityManagerFactory emf;

    private HarbourFacade() {
    }

    public static HarbourFacade getHarbourFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new HarbourFacade();
        }
        return instance;
    }

    //Create a harbour
    public HarbourDTO createHarbour(HarbourDTO harbourDTO) {
        EntityManager em = emf.createEntityManager();
        Harbour harbour = new Harbour(harbourDTO.getName(), harbourDTO.getAddress(), harbourDTO.getCapacity());
        try {
            em.getTransaction().begin();
            em.persist(harbour);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new HarbourDTO(harbour);
    }

    //Get all harbours
    public List<HarbourDTO> getAllHabours() {
        EntityManager em = emf.createEntityManager();
        List<Harbour> harbours;
        try {
            harbours = em.createQuery("SELECT h FROM Harbour h", Harbour.class).getResultList();
        } finally {
            em.close();
        }
        return HarbourDTO.getDTOs(harbours);
    }

    //Delete a harbour
    public HarbourDTO deleteHarbour(Long id) {
        EntityManager em = emf.createEntityManager();
        Harbour harbour = em.find(Harbour.class, id);
        try {
            em.getTransaction().begin();
            for (Boats boats: harbour.getBoat()) {
                boats.setHarbour(null);
                em.remove(boats);
            }
            em.remove(harbour);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new HarbourDTO(harbour);
    }

    //Get all boats in a harbour
    public List<HarbourDTO> getAllBoatsInHarbour(Long id) {
        EntityManager em = emf.createEntityManager();
        List<Harbour> harbours;
        try {
            harbours = em.createQuery("SELECT h FROM Harbour h WHERE h.id = :id", Harbour.class).setParameter("id", id).getResultList();
        } finally {
            em.close();
        }
        return HarbourDTO.getDTOs(harbours);
    }

    public static void main(String[] args) {
        HarbourFacade hf = getHarbourFacade(EMF_Creator.createEntityManagerFactory());
        System.out.println(hf.getAllBoatsInHarbour(1L));
    }





}
