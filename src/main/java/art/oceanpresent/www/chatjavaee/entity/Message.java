package art.oceanpresent.www.chatjavaee.entity;

import com.google.gson.annotations.Expose;

import javax.persistence.*;
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

    @Expose
    @Column(name = "chat_id")
    private Integer chatId;

    @Expose
    @Column(name = "user_to_id")
    private Integer userToId;

    @Expose
    @Column(name = "user_from_id")
    private Integer userFromId;

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

    public Integer getChatId() {
        return chatId;
    }

    public void setChatId(Integer chatId) {
        this.chatId = chatId;
    }

    public Integer getUserToId() {
        return userToId;
    }

    public void setUserToId(Integer userToId) {
        this.userToId = userToId;
    }

    public Integer getUserFromId() {
        return userFromId;
    }

    public void setUserFromId(Integer userFromId) {
        this.userFromId = userFromId;
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
