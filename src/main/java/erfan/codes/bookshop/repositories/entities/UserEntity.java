package erfan.codes.bookshop.repositories.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "users", schema = "onlinebookstore")
public class UserEntity {
    private String username;
    private String password;
    private String firstname;
    private String lastname;
    private String address;
    private String phone;
    private String mailid;
    private Integer usertype;

    @Id
    @Column(name = "username")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Basic
    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "firstname")
    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    @Basic
    @Column(name = "lastname")
    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    @Basic
    @Column(name = "address")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Basic
    @Column(name = "phone")
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Basic
    @Column(name = "mailid")
    public String getMailid() {
        return mailid;
    }

    public void setMailid(String mailid) {
        this.mailid = mailid;
    }

    @Basic
    @Column(name = "usertype")
    public Integer getUsertype() {
        return usertype;
    }

    public void setUsertype(Integer usertype) {
        this.usertype = usertype;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserEntity that = (UserEntity) o;

        if (!Objects.equals(username, that.username)) return false;
        if (!Objects.equals(password, that.password)) return false;
        if (!Objects.equals(firstname, that.firstname)) return false;
        if (!Objects.equals(lastname, that.lastname)) return false;
        if (!Objects.equals(address, that.address)) return false;
        if (!Objects.equals(phone, that.phone)) return false;
        if (!Objects.equals(mailid, that.mailid)) return false;
        if (!Objects.equals(usertype, that.usertype)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = username != null ? username.hashCode() : 0;
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (firstname != null ? firstname.hashCode() : 0);
        result = 31 * result + (lastname != null ? lastname.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (mailid != null ? mailid.hashCode() : 0);
        result = 31 * result + (usertype != null ? usertype.hashCode() : 0);
        return result;
    }
}
