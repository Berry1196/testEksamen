package facades;

import dtos.OwnerDTO;
import entities.Boats;
import entities.Owner;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

public class OwnerFacade {
    private static OwnerFacade instance;
    private static EntityManagerFactory emf;

    //Private Constructor to ensure Singleton
    private OwnerFacade() {
    }

    public static OwnerFacade getOwnerFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new OwnerFacade();
        }
        return instance;
    }

    public OwnerDTO createOwner(OwnerDTO ownerDTO) {
        EntityManager em = emf.createEntityManager();
        Owner owner = new Owner(ownerDTO.getName(), ownerDTO.getAddress(), ownerDTO.getPhone());
        try {
            em.getTransaction().begin();
            em.persist(owner);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new OwnerDTO(owner);
    }

    public List<OwnerDTO> getOwners() {
        EntityManager em = emf.createEntityManager();
        List<Owner> owners;
        try {
            owners = em.createQuery("SELECT o FROM Owner o", Owner.class).getResultList();
        } finally {
            em.close();
        }
        return OwnerDTO.getDTOs(owners);
    }

    //Remove an owner by id
    public OwnerDTO removeOwner(Long id) {
        EntityManager em = emf.createEntityManager();
        Owner owner = em.find(Owner.class, id);
        try {
            em.getTransaction().begin();
            em.remove(owner);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new OwnerDTO(owner);
    }

    //Add an owner to a boat
    public OwnerDTO addOwnerToBoat(Long id, Long boatId) {
        EntityManager em = emf.createEntityManager();
        Owner owner = em.find(Owner.class, id);
        try {
            em.getTransaction().begin();
            owner.addBoat(em.find(Boats.class, boatId));
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new OwnerDTO(owner);
    }

    //Edit an owner
    public OwnerDTO editOwner(OwnerDTO ownerDTO) {
        EntityManager em = emf.createEntityManager();
        Owner owner = em.find(Owner.class, ownerDTO.getId());
        try {
            em.getTransaction().begin();
            owner.setName(ownerDTO.getName());
            owner.setAddress(ownerDTO.getAddress());
            owner.setPhone(ownerDTO.getPhone());
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new OwnerDTO(owner);
    }

    public static void main(String[] args) {
        OwnerFacade of = getOwnerFacade(EMF_Creator.createEntityManagerFactory());
        of.editOwner(new OwnerDTO(5L, "Jens", "Jensvej 1", "12345678"));
    }


}