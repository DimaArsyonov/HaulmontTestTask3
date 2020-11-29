package screen;

import service.ServiceController;
import system.Doctor;

import com.vaadin.annotations.Theme;
import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings("serial")
@Theme(ValoTheme.THEME_NAME)
public class DoctorEditScreen extends Window {
	
	public DoctorEditScreen(Doctor doctor, MainUI.OPTIONS opt){
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
        Binder<Doctor> binder = new Binder<>(Doctor.class);

        //Поля редактирования
        TextField name = new TextField();
        name.setCaption("Имя");
        TextField surname = new TextField();
        surname.setCaption("Фамилия");
        TextField patronymic = new TextField();
        patronymic.setCaption("Отчество");
        TextField spec = new TextField();
        spec.setCaption("Специализация");

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

        binder.forField(spec)
                .withValidator(str -> str.length() <= 64, "Максимальная длина - 64 знака")
                .withValidator(str -> str.matches("[- a-zA-Zа-яА-Я]+"), "Специализация не может содержать цифр и спецсимволов")
                .asRequired("Обязательное значение")
                .bind("speciality");

        ok.setEnabled(false);

        binder.addStatusChangeListener(statusChangeEvent -> {
            ok.setEnabled(!statusChangeEvent.hasValidationErrors());
        });

        //Установка значений переданного объекта в поля редактирования
        binder.readBean(doctor);

        //Логика для кнопок
        ok.addClickListener(clickEvent -> {
            try {
                binder.writeBean(doctor);
                if(opt == MainUI.OPTIONS.UPDATE) ServiceController.updateDoctor(doctor);
                if(opt == MainUI.OPTIONS.ADD) ServiceController.addDoctor(doctor);
                DoctorScreen.RefreshList();
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
        content.addComponent(spec);
        content.addComponent(buttons);

        setModal(true);
        setResizable(false);
        setClosable(false);
        setContent(content);
        center();
    }
}
