package facades;

import dtos.BoatsDTO;
import entities.Boats;
import entities.Harbour;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

public class BoatFacade {

    private static BoatFacade instance;
    private static EntityManagerFactory emf;

    private BoatFacade() {
    }

    public static BoatFacade getBoatFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new BoatFacade();
        }
        return instance;
    }

    // create a boat

    public BoatsDTO createBoat(BoatsDTO boatDTO) {
        EntityManager em = emf.createEntityManager();
        Boats boat = new Boats(boatDTO.getBrand(), boatDTO.getMake(), boatDTO.getImage());
        try {
            em.getTransaction().begin();
            em.persist(boat);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new BoatsDTO(boat);
    }
    //get all boats
    public List<BoatsDTO> getBoats() {
        EntityManager em = emf.createEntityManager();
        List<Boats> boats;
        try {
            boats = em.createQuery("SELECT b FROM Boats b", Boats.class).getResultList();
        } finally {
            em.close();
        }
        return BoatsDTO.getDTOs(boats);
    }

    //Delete a boat
    public BoatsDTO deleteBoat(Long id) {
        EntityManager em = emf.createEntityManager();
        Boats boat = em.find(Boats.class, id);
        try {
            em.getTransaction().begin();
            em.remove(boat);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new BoatsDTO(boat);
    }

    //Add a boat to a harbour
    public BoatsDTO addBoatToHarbour(Long boatId, Long harbourId) {
        EntityManager em = emf.createEntityManager();
        Boats boat = em.find(Boats.class, boatId);
        try {
            em.getTransaction().begin();
            boat.setHarbour(em.find(Harbour.class, harbourId));
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new BoatsDTO(boat);
    }

    //Get all owners of a boat
   /* public BoatsDTO getOwnersOfBoat(Long id) {
        EntityManager em = emf.createEntityManager();
        Boats boat = em.find(Boats.class, id);
        try {
            em.getTransaction().begin();
            boat.getOwner();
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new BoatsDTO(boat);
    }*/
    //Edit a boat
    public BoatsDTO editBoat( BoatsDTO boatDTO) {
        EntityManager em = emf.createEntityManager();
        Boats boat = em.find(Boats.class, boatDTO.getId());
        try {
            em.getTransaction().begin();
            boat.setBrand(boatDTO.getBrand());
            boat.setMake(boatDTO.getMake());
            boat.setImage(boatDTO.getImage());
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new BoatsDTO(boat);
    }

//    public static void main(String[] args) {
//        BoatFacade boatFacade = BoatFacade.getBoatFacade(EMF_Creator.createEntityManagerFactory());
//        boatFacade.editBoat(new BoatsDTO(88L,"test","test","test"));
//    }
}
