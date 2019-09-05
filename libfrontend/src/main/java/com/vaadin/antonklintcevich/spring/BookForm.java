package com.vaadin.antonklintcevich.spring;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.antonklintcevich.common.BookDto;
import com.antonklintcevich.common.GenreDto;
import com.antonklintcevich.common.SubgenreDto;
import com.antonklintcevich.restclient.bookclient.BookClient;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.listbox.ListBox;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.converter.StringToBigDecimalConverter;
import com.vaadin.flow.data.converter.StringToIntegerConverter;
import com.vaadin.flow.data.converter.StringToLongConverter;

public class BookForm extends FormLayout {
    private Binder<BookDto> binder = new Binder<>(BookDto.class);
    private TextField id = new TextField("Book Id");
    private TextField bookname = new TextField("Book name");
    private TextField author = new TextField("Author");
    private TextField price = new TextField("Price");
    private TextField numberOfPages = new TextField("Number of pages");
    private Button addSubgenre = new Button("Add subgenre");
    private Button update = new Button("Update");
    private Button delete = new Button("Delete");
    private Button create = new Button("Create");
    private List<SubgenresPickLayout> subgenresPickLayouts = new ArrayList<>();
    private final BookClient bookClient;

    public BookForm(BookClient bookClient, BookView bookView) {
        this.bookClient = bookClient;

        addSubgenre.addClickListener(listener -> {
            subgenresPickLayouts.add(new SubgenresPickLayout(bookClient));
            add(subgenresPickLayouts.get(subgenresPickLayouts.size() - 1));
        });
        HorizontalLayout buttons = new HorizontalLayout(update, delete,create);
        update.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addClickListener(event -> {
            BookDto bookDto = binder.getBean();
            bookClient.deleteBook(bookDto.getId());
            bookView.updateList();
            setBook(null);
        });
       
        bind();
        update.addClickListener(event -> {
            BookDto bookDto = binder.getBean();
            Set<SubgenreDto> subgenres=new HashSet<>();
            for(SubgenresPickLayout layout:subgenresPickLayouts) {
                subgenres.add(layout.getSubgenreBoxValue());
            }
            bookDto.setSubgenres(subgenres);
            bookClient.updateBook(bookDto);
            bookView.updateList();
            setBook(null);
        });
        create.addClickListener(event->{
           binder.setBean(new BookDto());
           BookDto bookDto = binder.getBean();
            Set<SubgenreDto> subgenres=new HashSet<>();
            for(SubgenresPickLayout layout:subgenresPickLayouts) {
                subgenres.add(layout.getSubgenreBoxValue());
            }
            bookDto.setSubgenres(subgenres);
            bookClient.createBook(bookDto);
            bookView.updateList();
            setBook(null);
        });
        add(id,bookname, author, price, numberOfPages, addSubgenre, buttons);

    }

    public void bind() {
        binder.forField(bookname).bind(BookDto::getBookname, BookDto::setBookname);
        binder.bind(author, BookDto::getAuthor, BookDto::setAuthor);
        binder.forField(numberOfPages).withConverter(new StringToIntegerConverter("")).bind(BookDto::getNumberOfPages,
                BookDto::setNumberOfPages);
        binder.forField(price).withConverter(new StringToBigDecimalConverter("")).bind(BookDto::getPrice,
                BookDto::setPrice);
        binder.forField(id).withConverter(new StringToLongConverter("")).bind(BookDto::getId,BookDto::setId);

    }

    public void setBook(BookDto bookDto) {
        binder.setBean(bookDto);
        binder.addStatusChangeListener(event -> {
            for (SubgenresPickLayout subgenresPickLayout : subgenresPickLayouts) {
                remove(subgenresPickLayout);
            }
            subgenresPickLayouts.clear();
        });

        if (!binder.getBean().getSubgenres().isEmpty()) {
            List<SubgenreDto> bookSubgenres = binder.getBean().getSubgenres().stream().collect(Collectors.toList());
            for (SubgenreDto subgenre : bookSubgenres) {
                subgenresPickLayouts.add(new SubgenresPickLayout(bookClient, subgenre));
                add(subgenresPickLayouts.get(subgenresPickLayouts.size() - 1));
            }
        }
        if (bookDto == null) {
            setVisible(false);
        } else {
            setVisible(true);
        }
    }

}
