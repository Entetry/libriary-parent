package com.vaadin.antonklintcevich.spring;

import java.lang.module.FindException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;

import com.antonklintcevich.common.BookDto;
import com.antonklintcevich.common.FilterData;
import com.antonklintcevich.common.FilterType;
import com.antonklintcevich.common.SearchParameters;
import com.antonklintcevich.common.SortData;
import com.antonklintcevich.restclient.bookclient.BookClient;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;

import net.bytebuddy.description.modifier.EnumerationState;

@Route("")
@PWA(name = "Project Base for Vaadin Flow with Spring", shortName = "Project Base")

public class MainView extends VerticalLayout {
    private Button order = new Button("Order");
    public MainView(BookClient bookClient) {
        Image img = new Image("https://image.flaticon.com/icons/png/512/100/100104.png", "Vaadin Logo");
        img.setHeight("44px");
        img.addClickListener(event->UI.getCurrent().navigate(MainView.class));
        HorizontalLayout header = new HorizontalLayout();
        header.setMargin(true);
        Button books = new Button("Books", event -> UI.getCurrent().navigate(BookView.class));
        Button users = new Button("Users", event -> UI.getCurrent().navigate(UserView.class));
        Button orders = new Button("Orders",event->UI.getCurrent().navigate(OrderView.class));
        header.add(img,books, users, orders);
        add(header);
    }
}
