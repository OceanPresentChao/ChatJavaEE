package art.oceanpresent.www.chatjavaee.entity;

import com.google.gson.annotations.Expose;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "chat", schema = "javaee")
public class Chat implements AbstractEntity<Integer> {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    @Expose
    private Integer id;
    @Column(name = "title")
    @Expose
    private String title;

    @Column(name = "createTime")
    @Expose
    private LocalDateTime createTime;
    @Expose
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH})//可选属性optional=false,表示author不能为空。删除文章，不影响用户
    @JoinColumn(name = "user_id")//设置在article表中的关联字段(外键)
    private User user;//所属作者

    @OneToMany(mappedBy = "chat", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Message> messagesList;

//    @OneToMany(mappedBy = "chat",cascade=CascadeType.ALL,fetch=FetchType.LAZY)
//    private List<Comment> commentList;//文章列表

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
//
//    public List<Comment> getCommentList() {
//        return commentList;
//    }
//
//    public void setCommentList(List<Comment> commentList) {
//        this.commentList = commentList;
//    }

    public List<Message> getMessagesList() {
        return messagesList;
    }

    public void setMessagesList(List<Message> messagesList) {
        this.messagesList = messagesList;
    }

    @Override
    public String toString() {
        return "Chat{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", userId=" + user.getId() +
                ", createTime=" + createTime +
                '}';
    }


    @PrePersist
    protected void onCreate() {
        createTime = LocalDateTime.now();
    }
}
