package erfan.codes.bookshop.repositories.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "books", schema = "onlinebookstore")
public class BookEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private String id;

    @Basic
    @Column(name = "name")
    private String name;

    @Basic
    @Column(name = "barcode")
    private String barcode;

    @Basic
    @Column(name = "author")
    private String author;

    @Basic
    @Column(name = "price")
    private Integer price;

    @Basic
    @Column(name = "quantity")
    private Integer quantity;

    public BookEntity() {
    }

    public BookEntity(String barcode, String name, String author, Integer price, Integer quantity) {
        this.barcode = barcode;
        this.name = name;
        this.author = author;
        this.price = price;
        this.quantity = quantity;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BookEntity that = (BookEntity) o;

        if (!Objects.equals(barcode, that.barcode)) return false;
        if (!Objects.equals(name, that.name)) return false;
        if (!Objects.equals(author, that.author)) return false;
        if (!Objects.equals(price, that.price)) return false;
        return Objects.equals(quantity, that.quantity);
    }

    @Override
    public int hashCode() {
        int result = barcode != null ? barcode.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (author != null ? author.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (quantity != null ? quantity.hashCode() : 0);
        return result;
    }
}
