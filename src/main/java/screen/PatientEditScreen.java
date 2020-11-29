package screen;

import service.ServiceController;
import system.Patient;

import com.vaadin.annotations.Theme;
import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings("serial")
@Theme(ValoTheme.THEME_NAME)
public class PatientEditScreen extends Window {
	
	public PatientEditScreen(Patient patient, MainUI.OPTIONS opt){
        super();
        setHeight("390px");
        setWidth("290px");

        //Установка заголовка
        String caption;
        if(opt == MainUI.OPTIONS.ADD) caption = "Добавить";
        else caption = "Изменить";
        setCaption(caption);

        //Установка разметки содержимого
        VerticalLayout content = new VerticalLayout();
        content.setSizeFull();
        HorizontalLayout buttons = new HorizontalLayout();

        //Binder для переданного объекта
        Binder<Patient> binder = new Binder<>(Patient.class);

        //Поля редактирования
        TextField name = new TextField();
        name.setCaption("Имя");
        TextField surname = new TextField();
        surname.setCaption("Фамилия");
        TextField patronymic = new TextField();
        patronymic.setCaption("Отчество");
        TextField num = new TextField();
        num.setCaption("Номер телефона");

        //Кнопки
        Button ok = new Button("OK");
        Button cancel = new Button("Отмена");

        //Валидация полей для изменяемого объекта
        binder.forField(name)
                .withValidator(str -> str.length() <= 64, "Максимальная длина - 64 знака")
                .withValidator(str -> str.matches("[-a-zA-Zа-яА-Я]+"),
                        "ФИО не может содержать цифр, пробелов и спецсимволов")
                .asRequired("Обязательное значение")
                .bind("name");

        binder.forField(surname)
                .withValidator(str -> str.length() <= 64, "Максимальная длина - 64 знака")
                .withValidator(str -> str.matches("[-a-zA-Zа-яА-Я]+"),
                        "ФИО не может содержать цифр, пробелов и спецсимволов")
                .asRequired("Обязательное значение")
                .bind("surname");

        binder.forField(patronymic)
                .withValidator(str -> str.length() <= 64, "Максимальная длина - 64 знака")
                .withValidator(str -> str.matches("[-a-zA-Zа-яА-Я]+"),
                        "ФИО не может содержать цифр, пробелов и спецсимволов")
                .asRequired("Обязательное значение")
                .bind("patronymic");

        binder.forField(num)
                .withValidator(str -> str.matches("[+]?\\d{1}[(]{1}\\d{3}[)]{1}\\d{3}[-]?\\d{2}[-]?\\d{2}")
                        || str.matches("\\d{3}[-]?\\d{2}[-]?\\d{2}"),
                        "Телефонный номер не может содержать букв и пробелов и должен быть формата ХХХ-ХХ-ХХ или +Х(ХХХ)ХХХ-ХХ-ХХ")
                .asRequired("Обязательное значение")
                .bind("phoneNumber");

        ok.setEnabled(false);

        binder.addStatusChangeListener(statusChangeEvent -> {
            ok.setEnabled(!statusChangeEvent.hasValidationErrors());
        });

        //Установка значений переданного объекта в поля редактирования
        binder.readBean(patient);

        //Логика для кнопок
        ok.addClickListener(clickEvent -> {
            try {
                binder.writeBean(patient);
                if(opt == MainUI.OPTIONS.UPDATE) 
                	ServiceController.updatePatient(patient);
                if(opt == MainUI.OPTIONS.ADD) 
                	ServiceController.addPatient(patient);
                PatientScreen.RefreshList();
                this.close();
            }
            catch (ValidationException e){
                e.printStackTrace();
            }
        });

        cancel.addClickListener(clickEvent -> {
            this.close();
        });

        //Добавление компонентов
        buttons.addComponent(ok);
        buttons.addComponent(cancel);

        content.addComponent(surname);
        content.addComponent(name);
        content.addComponent(patronymic);
        content.addComponent(num);
        content.addComponent(buttons);

        setModal(true);
        setResizable(false);
        setClosable(false);
        setContent(content);
        center();
    }
}
