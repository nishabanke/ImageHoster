package ImageHoster.repository;

import ImageHoster.model.Comment;
import ImageHoster.model.Image;
import org.springframework.stereotype.Repository;
import javax.persistence.*;
import java.util.Iterator;
import java.util.List;

//The annotation is a special type of @Component annotation which describes that the class defines a data repository
@Repository
public class CommentRepository {

    //Get an instance of EntityManagerFactory from persistence unit with name as 'imageHoster'
    @PersistenceUnit(unitName = "imageHoster")
    private EntityManagerFactory emf;

    //The method receives the Comment object to be persisted in the database
    //Creates an instance of EntityManager
    //Starts a transaction
    //The transaction is committed if it is successful
    //The transaction is rolled back in case of unsuccessful transaction
    public Comment uploadComment(Comment newComment) {

        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.persist(newComment);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        }
        return newComment;
    }

    //The method creates an instance of EntityManager
    //Executes JPQL query to fetch all the comments from the database
    //Returns the list of all the comments fetched from the database
    public List<Comment> getAllComments() {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Comment> query = em.createQuery("SELECT i from Comment i", Comment.class);
        List<Comment> resultList = query.getResultList();
        return resultList;
    }
    //The method creates an instance of EntityManager
    //Executes JPQL query to fetch the comments of specific image from the database with corresponding title
    //Returns the comments in case the image is found in the database
    //Returns null if no comments is found in the database
    public List<Comment> getAllComments(Image image) {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Comment> query = em.createQuery("SELECT i from Comment i where i.image =:image", Comment.class).setParameter("image", image);
        List<Comment> resultList = query.getResultList();
        return resultList;
    }

    //The method receives the List of Comments of specific Image to be deleted in the database
    //Creates an instance of EntityManager
    //Starts a transaction
    //Get the comment with corresponding comment id from the database
    //This changes the state of the comment model from detached state to persistent state, which is very essential to use the remove() method
    //If you use remove() method on the object which is not in persistent state, an exception is thrown
    //The transaction is committed if it is successful
    //The transaction is rolled back in case of unsuccessful transaction
    public void deleteComments(List comments){
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            Iterator<Comment> itr = comments.iterator();
            while(itr.hasNext()){
                Comment comment = itr.next();
                comment = em.find(Comment.class, comment.getId());
                em.remove(comment);
                System.out.println("----------------222222222-----------delete");
            }
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        }
    }
}
