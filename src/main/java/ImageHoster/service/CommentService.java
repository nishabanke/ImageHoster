package ImageHoster.service;

import ImageHoster.model.Comment;
import ImageHoster.model.Image;
import ImageHoster.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

    public Comment uploadComment(Comment newComment) { return commentRepository.uploadComment(newComment); }

    //Call the getAllComments() method in the Repository and obtain a List of all the comments in the database
    public List getAllComments() { return commentRepository.getAllComments();}

    //Call the getAllComments(Image image) method in the Repository and obtain a List of all the comments of specific image in the database
    public List getAllComments(Image image) { return commentRepository.getAllComments(image);}

    //The method calls the deleteComments(List comments) method in the Repository and passes the List of the comments to be deleted in the database
    public void deleteComments(List comments) { commentRepository.deleteComments(comments); }
}
