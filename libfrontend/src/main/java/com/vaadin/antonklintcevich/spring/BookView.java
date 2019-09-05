package com.vaadin.antonklintcevich.spring;

import org.springframework.beans.factory.annotation.Autowired;

import com.antonklintcevich.common.BookDto;
import com.antonklintcevich.common.FilterData;
import com.antonklintcevich.common.FilterType;
import com.antonklintcevich.common.SearchParameters;
import com.antonklintcevich.common.SortData;
import com.antonklintcevich.restclient.bookclient.BookClient;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;

@Route("book")
public class BookView extends VerticalLayout {
    private final Grid<BookDto> grid;
    private final Button refresh = new Button("Refresh");
    private final TextField filterText = new TextField();
    private final BookClient bookClient;

    @Autowired
    public BookView(BookClient bookClient) {
        this.bookClient = bookClient;
        Image img = new Image("https://image.flaticon.com/icons/png/512/100/100104.png", "Vaadin Logo");
        img.setHeight("44px");
        HorizontalLayout header = new HorizontalLayout();
        header.setMargin(true);
        Button books = new Button("Books", event -> UI.getCurrent().navigate(BookView.class));
        Button users = new Button("Users", event -> UI.getCurrent().navigate(UserView.class));
        Button orders = new Button("Orders",event->UI.getCurrent().navigate(OrderView.class));
        header.add(img,books, users, orders);
        img.addClickListener(event->UI.getCurrent().navigate(MainView.class));
        filterText.setPlaceholder("Filter by bookname ");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.EAGER);
        filterText.addValueChangeListener(e -> updateList());
        grid = new Grid<>(BookDto.class);
        grid.setColumns("bookname", "author", "price");
        BookForm bookForm = new BookForm(bookClient, this);
        grid.addSelectionListener(event -> bookForm.setBook(event.getFirstSelectedItem().get()));
        HorizontalLayout horizontalLayout = new HorizontalLayout(grid, bookForm);
        bookForm.setSizeFull();
        grid.setSizeFull();
        setSizeFull();
        refresh.addClickListener(event -> updateList());
        add(header,filterText, horizontalLayout,refresh);
    }

    public void updateList() {
        grid.setItems(bookClient.getAllBooks(getSearchParameters()));
    }

    public SearchParameters getSearchParameters() {
        SearchParameters seParameters = new SearchParameters();
        SortData sortData = new SortData();
        sortData.setName("bookname");
        sortData.setSortOrder("DESC");
        seParameters.addSortData(sortData);
        if (!filterText.getValue().isEmpty()) {
            FilterData filterData = new FilterData();
            filterData.setField("bookname");
            filterData.setFilterType(FilterType.LIKE);
            filterData.setValue(filterText.getValue());
            seParameters.addFilterData(filterData);
        }
        return seParameters;

    }
}