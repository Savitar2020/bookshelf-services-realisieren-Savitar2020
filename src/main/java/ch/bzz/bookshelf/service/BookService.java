package ch.bzz.bookshelf.service;

import ch.bzz.bookshelf.data.DataHandler;
import ch.bzz.bookshelf.model.Book;
import jdk.nashorn.internal.objects.annotations.Getter;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.awt.*;
import java.util.Map;
import java.util.UUID;

@Path("book")
public class BookService {

    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listBooks() {
        Map<String, Book> bookMap = DataHandler.getBookMap();

        Response response = Response
                .status(200)
                .entity(bookMap)
                .build();
        return response;
    }

    @GET
    @Path("read")
    @Produces(MediaType.APPLICATION_JSON)
    public Response readBook(
            @QueryParam("uuid") String bookUUID
    ) {
        Book book = null;
        int httpStatus;

        try {
            UUID.fromString(bookUUID);
            book = DataHandler.readBook(bookUUID);
            if (book.getTitle() == null) {
                httpStatus = 484;
            } else {
                httpStatus = 200;
            }
        } catch (IllegalArgumentException argEx) {
            httpStatus = 400;
        }

        Response response = Response
                .status(200)
                .entity(book)
                .build();
        return response;
    }
}
