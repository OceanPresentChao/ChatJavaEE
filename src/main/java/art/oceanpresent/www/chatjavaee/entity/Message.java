package art.oceanpresent.www.chatjavaee.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "message", schema = "javaee")
public class Message {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;
    @Basic
    @Column(name = "user_from_id")
    private Integer userFromId;
    @Basic
    @Column(name = "user_to_id")
    private Integer userToId;
    @Basic
    @Column(name = "chat_id")
    private Integer chatId;

    @Basic
    @Column(name = "content")
    private String content;
    @Basic
    @Column(name = "createTime")
    private LocalDateTime createTime;

    @ManyToOne(cascade={CascadeType.MERGE,CascadeType.REFRESH})//可选属性optional=false,表示author不能为空。删除文章，不影响用户
    @JoinColumn(name="chat_id")//设置在article表中的关联字段(外键)
    private Chat chat;

    @ManyToOne(cascade={CascadeType.MERGE,CascadeType.REFRESH})//可选属性optional=false,表示author不能为空。删除文章，不影响用户
    @JoinColumn(name="user_to_id")//设置在article表中的关联字段(外键)
    private User userTo;

    @ManyToOne(cascade={CascadeType.MERGE,CascadeType.REFRESH})//可选属性optional=false,表示author不能为空。删除文章，不影响用户
    @JoinColumn(name="user_from_id")//设置在article表中的关联字段(外键)
    private User userFrom;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserFromId() {
        return userFromId;
    }

    public void setUserFromId(Integer userFromId) {
        this.userFromId = userFromId;
    }

    public Integer getUserToId() {
        return userToId;
    }

    public void setUserToId(Integer userToId) {
        this.userToId = userToId;
    }

    public Integer getChatId() {
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Message that = (Message) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (userFromId != null ? !userFromId.equals(that.userFromId) : that.userFromId != null) return false;
        if (userToId != null ? !userToId.equals(that.userToId) : that.userToId != null) return false;
        if (chatId != null ? !chatId.equals(that.chatId) : that.chatId != null) return false;
        if (content != null ? !content.equals(that.content) : that.content != null) return false;
        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (userFromId != null ? userFromId.hashCode() : 0);
        result = 31 * result + (userToId != null ? userToId.hashCode() : 0);
        result = 31 * result + (chatId != null ? chatId.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", userFromId=" + userFromId +
                ", userToId=" + userToId +
                ", chatId=" + chatId +
                ", content='" + content + '\'' +
                ", createTime=" + createTime +
                '}';
    }

    @PrePersist
    protected void onCreate() {
        createTime = LocalDateTime.now();
    }
}
