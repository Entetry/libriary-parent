package com.vaadin.antonklintcevich.spring;

import java.math.BigDecimal;

import com.antonklintcevich.common.BookDto;
import com.antonklintcevich.common.UserDto;
import com.antonklintcevich.common.WalletDto;
import com.antonklintcevich.restclient.userclient.RestUserClient;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.converter.Converter;
import com.vaadin.flow.data.converter.StringToBigDecimalConverter;
import com.vaadin.flow.data.converter.StringToBooleanConverter;
import com.vaadin.flow.data.converter.StringToDateConverter;
import com.vaadin.flow.data.converter.StringToLongConverter;

public class UserForm extends FormLayout {
    private Binder<UserDto> binder = new Binder<>(UserDto.class);
    private TextField id = new TextField("Id");
    private TextField firstname = new TextField("Firstname");
    private TextField lastname = new TextField("Lastname");
    private TextField dob = new TextField("DOB");
    private TextField email = new TextField("Email");
    private TextField enabled = new TextField("Enabled");
    private TextField walletId = new TextField("Wallet id");
    private TextField balance = new TextField("Balance");
    private Grid<BookDto> bookGrid;
    private Button save = new Button("Save");
    private Button delete = new Button("Delete");

    private final RestUserClient restUserClient;

    public UserForm(RestUserClient restUserClient, UserView userView) {
        this.restUserClient = restUserClient;
        HorizontalLayout walletLayout = new HorizontalLayout();
        walletLayout.add(walletId, balance);
        walletLayout.setWidth("100px");
        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.add(firstname, lastname, dob, email, enabled, walletLayout);
        HorizontalLayout buttons = new HorizontalLayout();
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttons.add(save, delete);
        bookGrid = new Grid<>(BookDto.class);
        bookGrid.setWidth("400px");
        bookGrid.setHeight("400px");
        bookGrid.setColumns("bookname", "author", "price");
        bind();
        add(verticalLayout, bookGrid, buttons);

    }

    public void bind() {
        binder.bind(firstname, UserDto::getFirstname, UserDto::setLastname);
        binder.bind(lastname, UserDto::getLastname, UserDto::setLastname);
        binder.forField(dob).withConverter(new StringToDateConverter()).bind(UserDto::getDob, UserDto::setDob);
        binder.bind(email, UserDto::getEmail, UserDto::setEmail);
        binder.bind(walletId, user -> user.getWalletDto().getId().toString(), (user, id) -> {
            user.getWalletDto().setId(Long.parseLong(id));
        });
        binder.forField(enabled).withConverter(new StringToBooleanConverter("")).bind(UserDto::isEnabled,
                UserDto::setEnabled);
        binder.forField(balance).bind(user -> user.getWalletDto().getBalance().toString(), (user, balance) -> {
            user.getWalletDto().setBalance(new BigDecimal(balance));
        });
    }

    public void setUser(UserDto userDto) {
        binder.setBean(userDto);

        bookGrid.setItems(binder.getBean().getBooks());
        binder.addStatusChangeListener(event -> bookGrid.setItems(binder.getBean().getBooks()));
        if (userDto == null) {
            setVisible(false);
        } else {
            setVisible(true);
        }
    }
}
