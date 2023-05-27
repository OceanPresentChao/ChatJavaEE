package art.oceanpresent.www.chatjavaee.entity;

import com.google.gson.annotations.Expose;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "message", schema = "javaee")
public class Message implements AbstractEntity<Integer> {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    @Expose
    private Integer id;

    @Basic
    @Column(name = "content")
    @Expose
    private String content;
    @Basic
    @Column(name = "createTime")
    @Expose
    private LocalDateTime createTime;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH})//可选属性optional=false,表示author不能为空。删除文章，不影响用户
    @JoinColumn(name = "chat_id")
    @Expose
    private Chat chat;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH})//可选属性optional=false,表示author不能为空。删除文章，不影响用户
    @JoinColumn(name = "user_to_id")//设置在article表中的关联字段(外键)
    @Expose
    private User userTo;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH})//可选属性optional=false,表示author不能为空。删除文章，不影响用户
    @JoinColumn(name = "user_from_id")//设置在article表中的关联字段(外键)
    @Expose
    private User userFrom;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }

    public User getUserTo() {
        return userTo;
    }

    public void setUserTo(User userTo) {
        this.userTo = userTo;
    }

    public User getUserFrom() {
        return userFrom;
    }

    public void setUserFrom(User userFrom) {
        this.userFrom = userFrom;
    }


    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", createTime=" + createTime +
                '}';
    }

    @PrePersist
    protected void onCreate() {
        createTime = LocalDateTime.now();
    }
}
