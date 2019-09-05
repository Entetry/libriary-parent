package com.vaadin.antonklintcevich.spring;

import com.antonklintcevich.common.BookDto;
import com.antonklintcevich.common.OrderDto;
import com.antonklintcevich.common.UserDto;
import com.antonklintcevich.restclient.orderclient.RestOrderClient;
import com.antonklintcevich.restclient.userclient.RestUserClient;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.converter.StringToBigDecimalConverter;
import com.vaadin.flow.data.converter.StringToDateConverter;
import com.vaadin.flow.data.converter.StringToLongConverter;

public class OrderForm extends FormLayout{
    private Binder<OrderDto> binder = new Binder<>(OrderDto.class);
    private TextField id = new TextField("Id");
    private TextField orderDate = new TextField("Order date");
    private TextField price = new TextField("Price");
    private Grid<BookDto> bookGrid;
    private Button save = new Button("Save");
    private Button delete = new Button("Delete");

    private final RestOrderClient restOrderClient;
    public OrderForm(RestOrderClient restOrderClient,OrderView orderView) {
        this.restOrderClient=restOrderClient;
        VerticalLayout verticalLayout = new VerticalLayout();
        HorizontalLayout buttons = new HorizontalLayout();
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttons.add(save,delete);
        verticalLayout.add(id,orderDate,price,buttons);
        bookGrid = new Grid<>(BookDto.class);
        bookGrid.setMinHeight("250px");
        bookGrid.setMinWidth("350px");
        bookGrid.setThemeName(GridVariant.LUMO_COMPACT.getVariantName());
        bookGrid.setWidth("350px");
        bookGrid.setHeight("250px");
        bookGrid.setColumns("bookname", "author", "price");
        HorizontalLayout formLayout = new HorizontalLayout();
        formLayout.setSizeFull();
        formLayout.add(verticalLayout,bookGrid);
        bind();
        add(formLayout);
    }
    private void bind() {
        binder.forField(id).withConverter(new StringToLongConverter("Use Long")).bind(OrderDto::getId,OrderDto::setId);
        binder.forField(orderDate).withConverter(new StringToDateConverter()).bind(OrderDto::getOrderdate,OrderDto::setOrderdate);
        binder.forField(price).withConverter(new StringToBigDecimalConverter("Use BigDecimal")).bind(OrderDto::getPrice,OrderDto::setPrice);
        
    }
    public void setOrder(OrderDto orderDto) {
        binder.setBean(orderDto);
        bookGrid.setItems(binder.getBean().getBookDtos());
        binder.addStatusChangeListener(event -> bookGrid.setItems(binder.getBean().getBookDtos()));
        if (orderDto == null) {
            setVisible(false);
        } else {
            setVisible(true);
        }
    }

}
