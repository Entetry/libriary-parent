package com.vaadin.antonklintcevich.spring;

import org.springframework.beans.factory.annotation.Autowired;

import com.antonklintcevich.common.OrderDto;
import com.antonklintcevich.common.UserDto;
import com.antonklintcevich.restclient.orderclient.RestOrderClient;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("orders")
public class OrderView extends VerticalLayout {
    private final Grid<OrderDto> grid;
    private final Button refresh = new Button("Refresh");
    private final RestOrderClient restOrderClient;

    @Autowired
    public OrderView(RestOrderClient restOrderClient) {
        this.restOrderClient = restOrderClient;
        Image img = new Image("https://image.flaticon.com/icons/png/512/100/100104.png", "Vaadin Logo");
        img.setHeight("44px");
        img.addClickListener(event->UI.getCurrent().navigate(MainView.class));
        HorizontalLayout header = new HorizontalLayout();
        header.setMargin(true);
        Button books = new Button("Books", event -> UI.getCurrent().navigate(BookView.class));
        Button users = new Button("Users", event -> UI.getCurrent().navigate(UserView.class));
        Button orders = new Button("Orders",event->UI.getCurrent().navigate(OrderView.class));
        header.add(img,books, users, orders);
        
        VerticalLayout verticalLayout = new VerticalLayout();
        OrderForm orderForm = new OrderForm(restOrderClient,this);
        orderForm.setSizeFull();
        grid = new Grid<>(OrderDto.class);
        grid.setColumns("id","orderdate","price","orderStatus","userDto.username","userDto.walletDto.id","userDto.walletDto.balance","userDto.status");
        grid.getColumnByKey("userDto.walletDto.id").setHeader("walletId");
        grid.getColumnByKey("userDto.status").setHeader("User status");
        grid.addSelectionListener(event->orderForm.setOrder(event.getFirstSelectedItem().get()));
        grid.setVisible(true);
        grid.setWidthFull();
        grid.setHeight("400px");
        verticalLayout.setSizeFull();
        verticalLayout.add(grid,refresh,orderForm);
        setSizeFull();
        refresh.addClickListener(event -> updateList());
        refresh.setHeight("80px");
        add(header,verticalLayout);
    }

    public void updateList() {
        grid.setItems(restOrderClient.getAllOrders());
    }
}
