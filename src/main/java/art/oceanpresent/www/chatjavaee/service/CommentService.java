package art.oceanpresent.www.chatjavaee.service;


import art.oceanpresent.www.chatjavaee.entity.Chat;
import art.oceanpresent.www.chatjavaee.entity.Comment;
import art.oceanpresent.www.chatjavaee.entity.User;
import art.oceanpresent.www.chatjavaee.jpa.CommentJPA;
import art.oceanpresent.www.chatjavaee.util.CustomException;

import java.util.List;

public class CommentService {
    static CommentJPA commentJPA = new CommentJPA();

    public static Comment createComment(Integer userId, Integer chatId, Comment comment) {
        Chat chat = ChatService.getChat(chatId);
        comment.setChat(chat);
        comment.setUserId(userId);
        return commentJPA.create(comment);
    }

    public static Comment updateComment(Integer id, Comment user) {
        Comment u = getComment(id);
        user.setId(u.getId());
        return commentJPA.update(user);
    }


    public static Comment getComment(Integer id) {
        Comment u = commentJPA.findById(id);
        if (u == null) {
            throw new CustomException("User not found");
        }
        return u;
    }

    public static void deleteComment(Integer id) {
        commentJPA.delete(id);
    }

    public static List<Comment> getCommentListByUser(Integer userId) {
        return commentJPA.findByUser(userId);
    }

    public static List<Comment> getCommentListByChat(Integer chatId) {
        return commentJPA.findByChat(chatId);
    }

}
