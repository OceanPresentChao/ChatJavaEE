package art.oceanpresent.www.chatjavaee.entity;

import com.google.gson.annotations.Expose;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "comment", schema = "javaee")
public class Comment implements AbstractEntity<Integer> {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    @Expose
    private Integer id;
    @Basic
    @Column(name = "user_id")
    @Expose
    private Integer userId;

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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", userId=" + userId +
                ", content='" + content + '\'' +
                ", createTime=" + createTime +
                '}';
    }

    @PrePersist
    protected void onCreate() {
        createTime = LocalDateTime.now();
    }
}
