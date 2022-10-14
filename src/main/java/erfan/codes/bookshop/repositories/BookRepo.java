package erfan.codes.bookshop.repositories;

import erfan.codes.bookshop.models.UpdateBookModel;
import erfan.codes.bookshop.proto.holder.BookGlobalV1;
import erfan.codes.bookshop.repositories.entities.BookEntity;
import erfan.codes.bookshop.repositories.hibernate.Criteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;
import java.util.Optional;

@Repository
public class BookRepo extends ObjectRepo<BookEntity> {

    @Autowired
    IBookRepo iBookRepo;

    public BookRepo(EntityManager em) {
        super(em);
    }


    @Transactional
    public <S extends BookEntity> S save(S entity) {
        return iBookRepo.save(entity);
    }

    @Transactional
    public List<BookEntity> findByBarcode(String barcode) {
        this.criteria = new Criteria<>(BookEntity.class, this.getSession());
        this.criteria.cQuery().select(criteria.root()).
                where(criteria.cBuilder().equal(criteria.root().get(Fields.BARCODE.getValue()), barcode));

        return criteria.query().getResultList();
    }

    public void deleteById(Long valueOf) {
        this.iBookRepo.deleteById(valueOf);
    }

    @Transactional
    public List<BookEntity> booksList() {
        return this.iBookRepo.findAll();
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

    public Optional<BookEntity> findById(Long id) {

        return this.iBookRepo.findById(id);
    }

    public BookGlobalV1.BookList.Builder createBookList(List<BookEntity> bookEntities) {

        BookGlobalV1.BookList.Builder bookDTO = BookGlobalV1.BookList.newBuilder();
        for (BookEntity book : bookEntities) {
            BookGlobalV1.Book.Builder book1 = createBook(book);
            bookDTO.addData(book1);
        }

        return bookDTO;
    }

    @Transactional
    public BookEntity updateBookInfo(UpdateBookModel updateBookModel) {

        BookEntity book = new BookEntity();
        book.setAuthor(updateBookModel.getAuthor());
        book.setBarcode(updateBookModel.getBarcode());
        book.setName(updateBookModel.getName());
        book.setQuantity(updateBookModel.getQuantity());
        book.setPrice(updateBookModel.getPrice());
        book.setId(Long.valueOf(updateBookModel.getId()));
        return this.iBookRepo.save(book);
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
