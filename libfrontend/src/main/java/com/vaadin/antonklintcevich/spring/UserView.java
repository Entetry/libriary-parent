package com.vaadin.antonklintcevich.spring;

import org.springframework.beans.factory.annotation.Autowired;

import com.antonklintcevich.common.BookDto;
import com.antonklintcevich.common.UserDto;
import com.antonklintcevich.restclient.userclient.RestUserClient;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
@Route("users")
public class UserView extends VerticalLayout {
    private final Grid<UserDto> grid;
    private final Button refresh = new Button("Refresh");
    private final TextField filterText=new TextField();
    private final RestUserClient restUserClient;
    @Autowired
    public UserView(RestUserClient restUserClient) {
        this.restUserClient=restUserClient;
        Image img = new Image("https://image.flaticon.com/icons/png/512/100/100104.png", "Vaadin Logo");
        img.setHeight("44px");
        HorizontalLayout header = new HorizontalLayout();
        header.setMargin(true);
        Button books = new Button("Books", event -> UI.getCurrent().navigate(BookView.class));
        Button users = new Button("Users", event -> UI.getCurrent().navigate(UserView.class));
        Button orders = new Button("Orders",event->UI.getCurrent().navigate(OrderView.class));
        header.add(img,books, users, orders);
        img.addClickListener(event->UI.getCurrent().navigate(MainView.class));
        VerticalLayout content = new VerticalLayout();
        HorizontalLayout horizontalLayout=new HorizontalLayout();
        grid = new Grid<>(UserDto.class);
        grid.setColumns("firstname","lastname","dob","email","status","enabled","walletDto.balance");
        grid.setVisible(true);
        UserForm userForm=new UserForm(restUserClient, this);
        grid.addSelectionListener(event->userForm.setUser(event.getFirstSelectedItem().get()));
       
        grid.setSizeFull();
        grid.setHeight("400px");
        userForm.setHeight("400px");
        userForm.setSizeFull();
        horizontalLayout.setSizeFull();
        horizontalLayout.add(grid);
        setSizeFull();
        refresh.addClickListener(event -> updateList());
        refresh.setHeight("80px");
        content.add(horizontalLayout,refresh,userForm);
        add(header,content);
        
    }
    public void updateList() {
        grid.setItems(restUserClient.getAllUsers());
    }
}
