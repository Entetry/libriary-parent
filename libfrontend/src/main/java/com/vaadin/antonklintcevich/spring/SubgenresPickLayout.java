package com.vaadin.antonklintcevich.spring;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.antonklintcevich.common.BookDto;
import com.antonklintcevich.common.GenreDto;
import com.antonklintcevich.common.SubgenreDto;
import com.antonklintcevich.restclient.bookclient.BookClient;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.listbox.ListBox;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.binder.Binder;

public class SubgenresPickLayout extends HorizontalLayout {
    private ComboBox<GenreDto> genreBox = new ComboBox<>();
    private ComboBox<SubgenreDto> subgenreBox = new ComboBox<>();
    private Binder<SubgenreDto> binder;
    private SubgenreDto subGenreDto;
    private final BookClient bookClient;

    public SubgenresPickLayout(BookClient bookClient) {
        List<GenreDto> genres = bookClient.getAllGenres();
        this.bookClient = bookClient;
//        this.subGenreDto = dto;
        subgenreBox.setLabel("Subgenre selection");
        subgenreBox.setItemLabelGenerator(SubgenreDto::getSubgenrename);
        genreBox.setLabel("Genre Selection");
        genreBox.setItemLabelGenerator(GenreDto::getGenrename);
        genreBox.setItems(bookClient.getAllGenres());
        genreBox.addValueChangeListener(e -> subgenreBox.setItems(e.getValue().getSubgenres()));
        add(genreBox);
        add(subgenreBox);

    }

    public SubgenresPickLayout(BookClient bookClient, SubgenreDto subgenreDto) {
        List<GenreDto> genres = bookClient.getAllGenres();
        this.bookClient = bookClient;
        subgenreBox.setLabel("Subgenre selection");
        subgenreBox.setItemLabelGenerator(SubgenreDto::getSubgenrename);
        genreBox.setLabel("Genre Selection");
        genreBox.setItemLabelGenerator(GenreDto::getGenrename);
        genreBox.setItems(genres);
        genreBox.addValueChangeListener(e -> {
            subgenreBox.setItems(e.getValue().getSubgenres());
            if (e.getValue().getSubgenres().stream().map(SubgenreDto::getSubgenrename)
                    .filter(name -> name.equals(subgenreDto.getSubgenrename())).findAny().isPresent()) {
                subgenreBox.setValue(subgenreDto);
            }
        });
        add(genreBox);
        add(subgenreBox);
        System.out.println(subgenreDto.getGenreDto().getGenrename());
        genres.forEach(genre -> {
            System.out.println(genre.getGenrename());
        });
        genreBox.setValue(genres.stream().filter(genre -> genre.equals(subgenreDto.getGenreDto())).findFirst().get());
    }

    private GenreDto getGenre() {
        return this.subGenreDto.getGenreDto();
    }
    public GenreDto getGenreBoxValue() {
        return genreBox.getValue();
    }
    public SubgenreDto getSubgenreBoxValue() {
        return subgenreBox.getValue();
    }
}
