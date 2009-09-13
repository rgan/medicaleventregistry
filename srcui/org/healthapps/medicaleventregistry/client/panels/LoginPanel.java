package org.healthapps.medicaleventregistry.client.panels;

import com.google.gwt.i18n.client.Constants;
import com.google.gwt.user.client.ui.*;

public class LoginPanel extends DecoratorPanel {

    private TextBox userNameTextBox;
    private PasswordTextBox passwordTextBox;
    private Button loginSubmitBtn;
    private TextBox registerUserNameTextBox;
    private PasswordTextBox registerPasswordTextBox;
    private Button registerSubmitBtn;

    public static interface UIConstants extends Constants {
        @DefaultStringValue("Login")
        String loginPanelLoginFormName();

        @DefaultStringValue("Username:")
        String loginPanelUserName();

        @DefaultStringValue("Password:")
        String loginPanelPwd();

        @DefaultStringValue("Login")
        String loginPanelLoginSubmitButtonText();

        @DefaultStringValue("Register")
        String loginPanelRegisterSubmitButtonText();

        @DefaultStringValue("If you don't have a login, register below.")
        String loginPanelRegisterText();
    }

    public LoginPanel(UIConstants constants) {
        FlexTable layout = new FlexTable();
        layout.setCellSpacing(6);
        addLoginForm(constants, layout);
        addRegisterForm(constants, layout);
        setWidget(layout);
    }

    private void addLoginForm(UIConstants constants, FlexTable layout) {
        FlexTable.FlexCellFormatter cellFormatter = layout.getFlexCellFormatter();

        layout.setHTML(0, 0, constants.loginPanelLoginFormName());
        cellFormatter.setColSpan(0, 0, 2);
        cellFormatter.setHorizontalAlignment(0, 0,
                HasHorizontalAlignment.ALIGN_CENTER);

        layout.setHTML(1, 0, constants.loginPanelUserName());
        userNameTextBox = new TextBox();
        layout.setWidget(1, 1, userNameTextBox);
        layout.setHTML(2, 0, constants.loginPanelPwd());
        passwordTextBox = new PasswordTextBox();
        layout.setWidget(2, 1, passwordTextBox);
        loginSubmitBtn = new Button();
        loginSubmitBtn.setText(constants.loginPanelLoginSubmitButtonText());
        layout.setWidget(3, 0, loginSubmitBtn);
        cellFormatter.setColSpan(3, 0, 2);
        cellFormatter.setHorizontalAlignment(3, 0,
                HasHorizontalAlignment.ALIGN_CENTER);
    }

    private void addRegisterForm(UIConstants constants, FlexTable layout) {
        FlexTable.FlexCellFormatter cellFormatter = layout.getFlexCellFormatter();

        layout.setHTML(4, 0, constants.loginPanelRegisterText());
        cellFormatter.setColSpan(4, 0, 2);
        cellFormatter.setHorizontalAlignment(4, 0,
                HasHorizontalAlignment.ALIGN_CENTER);

        layout.setHTML(5, 0, constants.loginPanelUserName());
        registerUserNameTextBox = new TextBox();
        layout.setWidget(5, 1, registerUserNameTextBox);
        layout.setHTML(6, 0, constants.loginPanelPwd());
        registerPasswordTextBox = new PasswordTextBox();
        layout.setWidget(6, 1, registerPasswordTextBox);
        registerSubmitBtn = new Button();
        registerSubmitBtn.setText(constants.loginPanelRegisterSubmitButtonText());
        layout.setWidget(7, 0, registerSubmitBtn);
        cellFormatter.setColSpan(7, 0, 2);
        cellFormatter.setHorizontalAlignment(7, 0,
                HasHorizontalAlignment.ALIGN_CENTER);
    }

}
