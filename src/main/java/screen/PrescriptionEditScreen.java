package screen;

import service.ServiceController;
import system.Patient;
import system.Prescription;
import system.Doctor;

import com.vaadin.annotations.Theme;
import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings("serial")
@Theme(ValoTheme.THEME_NAME)
public class PrescriptionEditScreen extends Window {
	public PrescriptionEditScreen(Prescription prescription, MainUI.OPTIONS opt){
        super();
        setHeight("490px");
        setWidth("410px");

        //Установка заголовка
        String caption;
        if(opt == MainUI.OPTIONS.ADD) caption = "Добавить";
        else caption = "Изменить";
        setCaption(caption);

        //Установка разметки содержимого
        AbsoluteLayout content = new AbsoluteLayout();
        content.setSizeFull();
        HorizontalLayout buttons = new HorizontalLayout();
        HorizontalLayout persons = new HorizontalLayout();
        HorizontalLayout dates = new HorizontalLayout();

        //Binder для переданного объекта
        Binder<Prescription> binder = new Binder<>(Prescription.class);

        //Поля редактирования
        TextArea desc = new TextArea();
        desc.setCaption("Описание");
        ComboBox<Patient> patient = new ComboBox();
        patient.setCaption("Пациент");
        ComboBox<Doctor> doctor = new ComboBox();
        doctor.setCaption("Врач");
        DateField dateOfCreation = new DateField();
        dateOfCreation.setCaption("Дата назначения");
        DateField validityPeriod = new DateField();
        dateOfCreation.setCaption("Дата окончания действия");
        ComboBox<String> priority = new ComboBox();
        priority.setCaption("Приоритет");

        desc.setWidth("96%");

        //Кнопки
        Button ok = new Button("OK");
        Button cancel = new Button("Отмена");

        //Списки элементов для всех ComboBox
        patient.setItems(ServiceController.getPatientList());
        patient.setItemCaptionGenerator(item -> item.getFullName());
        patient.setEmptySelectionAllowed(true);
        patient.setEmptySelectionCaption("---");
        doctor.setItems(ServiceController.getDoctorList());
        doctor.setItemCaptionGenerator(item -> item.getFullName());
        doctor.setEmptySelectionAllowed(true);
        doctor.setEmptySelectionCaption("---");
        priority.setItems("Нормальный", "Срочный", "Немедленный");
        priority.setEmptySelectionAllowed(false);
        priority.setValue("Нормальный");

        //Валидация полей для изменяемого объекта
        binder.forField(desc)
                .withValidator(str -> str.length() <= 256, "Максимальная длина - 256 знаков")
                .asRequired("Обязательное значение")
                .bind("description");

        binder.forField(patient)
                .asRequired("Обязательное значение")
                .bind("patient");

        binder.forField(doctor)
                .asRequired("Обязательное значение")
                .bind("doctor");

        binder.forField(dateOfCreation)
                .asRequired("Обязательное значение")
                .bind("dateOfCreation");

        binder.forField(validityPeriod)
                .withValidator(localDate -> localDate.isAfter(dateOfCreation.getValue()),
                        "Дата окончания действия не может быть раньше даты назначения")
                .asRequired("Обязательное значение")
                .bind("validityPeriod");

        binder.forField(priority)
                .asRequired("Обязательное значение")
                .bind("priority");

        ok.setEnabled(false);

        binder.addStatusChangeListener(statusChangeEvent -> {
            ok.setEnabled(!statusChangeEvent.hasValidationErrors());
        });

        //Установка значений переданного объекта в поля редактирования
        binder.readBean(prescription);

        //Логика для кнопок
        ok.addClickListener(clickEvent -> {
            try {
                binder.writeBean(prescription);
                System.out.println(prescription.getPriority());
                if(opt == MainUI.OPTIONS.UPDATE) 
                	ServiceController.updatePrescription(prescription);
                if(opt == MainUI.OPTIONS.ADD) 
                	ServiceController.addPrescription(prescription);
                PrescriptionScreen.RefreshList();
                this.close();
            }
            catch (ValidationException e){
                System.out.println(e);
            }
        });

        cancel.addClickListener(clickEvent -> {
            this.close();
        });

        //Добавление компонентов
        persons.addComponent(patient);
        persons.addComponent(doctor);

        dates.addComponent(dateOfCreation);
        dates.addComponent(validityPeriod);

        buttons.addComponent(ok);
        buttons.addComponent(cancel);

        content.addComponent(desc, "top: 5%; left: 2%;");
        content.addComponent(persons, "top: 40%; left: 2%;");
        content.addComponent(dates, "top: 55%; left: 2%;");
        content.addComponent(priority, "top: 75%; left: 2%;");
        content.addComponent(buttons, "top: 86%; left: 2%;");

        setModal(true);
        setResizable(false);
        setClosable(false);
        setContent(content);
        center();
    }
}
