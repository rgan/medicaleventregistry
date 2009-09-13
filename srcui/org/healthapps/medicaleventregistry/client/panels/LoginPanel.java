package org.healthapps.medicaleventregistry.client.panels;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.http.client.*;
import com.google.gwt.i18n.client.Constants;
import com.google.gwt.user.client.ui.*;
import org.healthapps.medicaleventregistry.client.BaseRequestCallback;

public class LoginPanel extends DecoratorPanel {

    private TextBox userNameTextBox;
    private PasswordTextBox passwordTextBox;
    private Button loginSubmitBtn;
    private TextBox registerUserNameTextBox;
    private PasswordTextBox registerPasswordTextBox;
    private Button registerSubmitBtn;
    private EventPanel eventPanel;
    private SearchPanel searchPanel;
    private static final String URL_LOGIN = "/rest/sessions/";
    private TextBox registerEmailTextBox;
    private static final String URL_REGISTER = "/rest/users/";

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

        @DefaultStringValue("Email:")
        String loginPanelEmail();
    }

    public LoginPanel(UIConstants constants, EventPanel eventPanel, SearchPanel searchPanel) {
        this.eventPanel = eventPanel;
        this.searchPanel = searchPanel;
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
        loginSubmitBtn.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                doLogin();
            }
        });
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
        layout.setHTML(7, 0, constants.loginPanelEmail());
        registerEmailTextBox = new TextBox();
        layout.setWidget(7, 1, registerEmailTextBox);
        registerSubmitBtn = new Button();
        registerSubmitBtn.setText(constants.loginPanelRegisterSubmitButtonText());
        layout.setWidget(8, 0, registerSubmitBtn);
        cellFormatter.setColSpan(8, 0, 2);
        cellFormatter.setHorizontalAlignment(8, 0,
                HasHorizontalAlignment.ALIGN_CENTER);
        registerSubmitBtn.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                doRegister();
            }
        });
    }

    private void doRegister() {
        String username = registerUserNameTextBox.getText();
        String pwd = registerPasswordTextBox.getText();
        String email = registerEmailTextBox.getText();
        if (!validate(username, pwd, email)) {
            return;
        }
        RequestBuilder requestBuilder = new RequestBuilder(RequestBuilder.POST, URL.encode(URL_REGISTER));
        requestBuilder.setRequestData("username=" + username
                + "&password=" + pwd + "&email=" + email);
        requestBuilder.setCallback(new BaseRequestCallback() {

            public void onResponseReceived(Request request, Response response) {
                registerUserNameTextBox.setText("");
                registerPasswordTextBox.setText("");
                registerEmailTextBox.setText("");
            }
        });
        try {
            requestBuilder.send();
        } catch (RequestException e) {
            GWT.log(e.toString(), null);
        }
    }

    private void doLogin() {
        String username = userNameTextBox.getText();
        String pwd = passwordTextBox.getText();
        if (!validate(username, pwd)) {
            return;
        }
        RequestBuilder requestBuilder = new RequestBuilder(RequestBuilder.POST, URL.encode(URL_LOGIN));
        requestBuilder.setRequestData("username=" + username + "&password=" + pwd);
        requestBuilder.setCallback(new BaseRequestCallback() {

            public void onResponseReceived(Request request, Response response) {
                searchPanel.enable();
                searchPanel.setUserPwd(userNameTextBox.getText(), passwordTextBox.getText());
                eventPanel.enable();
                eventPanel.setUserPwd(userNameTextBox.getText(), passwordTextBox.getText());
                userNameTextBox.setText("");
                passwordTextBox.setText("");
            }
        });
        try {
            requestBuilder.send();
        } catch (RequestException e) {
            GWT.log(e.toString(), null);
        }
    }

    private boolean validate(String username, String pwd) {
        return validate(username, pwd, null);
    }

    private boolean validate(String username, String pwd, String email) {
        if (username == "" || pwd == "" || (email != null && email == "")) {
            return false;
        }
        return true;
    }

}
