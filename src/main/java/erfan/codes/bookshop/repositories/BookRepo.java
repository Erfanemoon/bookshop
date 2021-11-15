package erfan.codes.bookshop.repositories;

import erfan.codes.bookshop.proto.holder.BookGlobalV1;
import erfan.codes.bookshop.repositories.entities.BookEntity;
import erfan.codes.bookshop.repositories.hibernate.Criteria;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class BookRepo extends ObjectRepo<BookEntity> {

    public BookRepo(EntityManager em) {
        super(em);
        this.criteria = new Criteria<>(BookEntity.class, this.getSession());
    }

    @Override
    @Transactional
    public <S extends BookEntity> S save(S entity) {
        return super.save(entity);
    }

    @Transactional
    public List<BookEntity> findByBarcode(String barcode) {
        this.criteria.cQuery().select(criteria.root()).
                where(criteria.cBuilder().equal(criteria.root().get(Fields.BARCODE.getValue()), barcode));

        return criteria.query().getResultList();
    }

    public BookGlobalV1.Book.Builder createBook(BookEntity book) {

        BookGlobalV1.Book.Builder dto = BookGlobalV1.Book.newBuilder();
        dto.setId(book.getId());
        dto.setName(book.getName());
        dto.setBarcode(book.getBarcode());
        dto.setAuthor(book.getAuthor());
        dto.setPrice(book.getPrice());
        dto.setQuantity(book.getQuantity());
        return dto;
    }

    public enum Fields {
        ID("id"),
        NAME("name"),
        BARCODE("barcode"),
        AUTHOR("author"),
        PRICE("price"),
        QUANTITY("quantity");

        private String value;

        Fields(String value) {
            this.value = value;
        }

        public static Fields get(String value) {
            if (value == null)
                return null;
            Fields[] arr$ = values();
            for (Fields val : arr$) {
                if (val.value.equals(value.trim())) {
                    return val;
                }
            }

            return null;
        }

        public String getValue() {
            return value;
        }
    }

}
