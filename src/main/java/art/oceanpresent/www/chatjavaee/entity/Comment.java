package art.oceanpresent.www.chatjavaee.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "comment", schema = "javaee")
public class Comment {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;
    @Basic
    @Column(name = "user_id")
    private Integer userId;
    @Basic
    @Column(name = "chat_id")
    private Integer chatId;
    @Basic
    @Column(name = "content")
    private String content;
    @Basic
    @Column(name = "createTime")
    private LocalDateTime createTime;

//    @ManyToOne(cascade={CascadeType.MERGE,CascadeType.REFRESH})//可选属性optional=false,表示author不能为空。删除文章，不影响用户
//    @JoinColumn(name="user_id")//设置在article表中的关联字段(外键)
//    private User user;
//
//    @ManyToOne(cascade={CascadeType.MERGE,CascadeType.REFRESH})//可选属性optional=false,表示author不能为空。删除文章，不影响用户
//    @JoinColumn(name="chat_id")
//    private Chat chat;

    public Object getId() {
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

    public Object getChatId() {
        return chatId;
    }

    public void setChatId(Integer chatId) {
        this.chatId = chatId;
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

//    public User getUser() {
//        return user;
//    }
//
//    public void setUser(User user) {
//        this.user = user;
//    }
//
//    public Chat getChat() {
//        return chat;
//    }
//
//    public void setChat(Chat chat) {
//        this.chat = chat;
//    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", userId=" + userId +
                ", chatId=" + chatId +
                ", content='" + content + '\'' +
                ", createTime=" + createTime +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Comment that = (Comment) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;
        if (chatId != null ? !chatId.equals(that.chatId) : that.chatId != null) return false;
        if (content != null ? !content.equals(that.content) : that.content != null) return false;
        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (chatId != null ? chatId.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        return result;
    }

    @PrePersist
    protected void onCreate() {
        createTime = LocalDateTime.now();
    }
}
