package com.zolee.controller;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.zolee.authentication.PasswordHashing;
import com.zolee.domain.Person;
import com.zolee.service.PersonService;

@Theme("valo")
@SpringUI
public class MainUI extends UI {

	private PersonService personService;
	
	@Autowired
	public void setPersonService(PersonService personService) {
		this.personService = personService;
	}

	private PasswordHashing passwordHashing;
	
	@Autowired
	public void setPasswordHashing(PasswordHashing passwordHashing) {
		this.passwordHashing = passwordHashing;
	}

	private HorizontalLayout mainLayout;
	private VerticalLayout leftLayout;
	private HorizontalLayout inputLayout;
	private HorizontalLayout buttonLayout;
	private TextField inputNameTextField;
	private NativeSelect<String> inputRoleSelect;
	
	private PasswordField inputPasswordField;
	private Button saveButton;
	private Button searchButton;
	private Grid<Person> grid;
	private Label resultLabel;
	
	@Override
	protected void init(VaadinRequest request) {
		mainLayout = new HorizontalLayout();
		leftLayout = new VerticalLayout();
		inputLayout = new HorizontalLayout();
		buttonLayout = new HorizontalLayout();
		grid = new Grid<>(Person.class);
		saveButton = new Button("Save");
		searchButton = new Button("Search");
		inputPasswordField = new PasswordField("Password:");
		inputNameTextField = new TextField("Name:");
		inputRoleSelect = new NativeSelect<>("Role:");
		resultLabel = new Label("Még nincs találat!");
		grid.setColumns("name", "role", "password");
		grid.setSizeFull();
		
		setContent(mainLayout);
		mainLayout.addComponents(leftLayout, grid);
		leftLayout.addComponents(inputLayout, buttonLayout, resultLabel);
		inputLayout.addComponents(inputNameTextField, inputRoleSelect, inputPasswordField);
		buttonLayout.addComponents(saveButton, searchButton);
		inputRoleSelect.setItems("admin","user");
		mainLayout.setSpacing(true);
		mainLayout.setMargin(true);
		
		upDateGrid();
		
		saveButton.addClickListener(e -> savePerson());
		searchButton.addClickListener(e -> searchPerson());
	}

	private void savePerson() {
		if(personService.findByName(inputNameTextField.getValue())==null) {
			personService.save(new Person(inputNameTextField.getValue(),
					passwordHashing.generateHashCode(inputPasswordField.getValue()),
					inputRoleSelect.getValue()));
			upDateGrid();
		}else {
			resultLabel.setValue("Existing user!");
		}
	}

	private void searchPerson() {
		Person tempPerson = personService.findByName(inputNameTextField.getValue());
		if(tempPerson!=null) {
			if(tempPerson.getPassword().equals(
					passwordHashing.generateHashCode(inputPasswordField.getValue()))) {
				resultLabel.setValue("Passwords equals!");
			}else {
				resultLabel.setValue("Passwords not equals!");
			}
		}else {
			resultLabel.setValue("Not existing user!");
		}
	}
	
	private void upDateGrid() {
		grid.setItems(personService.findAll());
	}

}
