package ImageHoster.controller;

import ImageHoster.model.Comment;
import ImageHoster.model.User;
import ImageHoster.service.CommentService;
import ImageHoster.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private ImageService imageService;

    //This controller method is called when the request pattern is of type '/image/{imageId}/{imageTitle}/comments'
    //The method returns '/image/{imageId}/{imageTitle}/comments'
    @RequestMapping(value = "/image/{imageId}/{imageTitle}/comments")
    public String newComment() {
        return "/image/{imageId}/{imageTitle}/comments";
    }

    //This controller method is called when the request pattern is of type '/image/{imageId}/{imageTitle}/comments' and also the incoming request is of POST type
    //The method receives all the details of the comment to be stored in the database, and now the comment will be sent to the business logic to be persisted in the database
    //After you get the comment, set the user of the comment by getting the logged in user from the Http Session
    //Convert the image to Base64 format and store it as a string in the 'imageFile' attribute
    //Set the date on which the comment is posted
    //After storing the comment, this method directs to the same page with newly posted comments
    @RequestMapping(value = "/image/{imageId}/{imageTitle}/comments", method = RequestMethod.POST)
    public String createComment(@RequestParam("comment") String comment ,@PathVariable("imageId") Integer imageId, @PathVariable("imageTitle") String imageTitle, Comment newComment, HttpSession session) throws IOException {
        User user = (User) session.getAttribute("loggeduser");
        newComment.setUser(user);
        newComment.setImage(imageService.getImage(imageId));
        newComment.setText(comment);
        newComment.setCreatedDate(new Date());
        commentService.uploadComment(newComment);
        return "redirect:/images/" + imageId+"/"+imageTitle;
    }
}
