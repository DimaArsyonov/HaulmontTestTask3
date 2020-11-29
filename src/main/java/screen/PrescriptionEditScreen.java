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

        //��������� ���������
        String caption;
        if(opt == MainUI.OPTIONS.ADD) caption = "��������";
        else caption = "��������";
        setCaption(caption);

        //��������� �������� �����������
        AbsoluteLayout content = new AbsoluteLayout();
        content.setSizeFull();
        HorizontalLayout buttons = new HorizontalLayout();
        HorizontalLayout persons = new HorizontalLayout();
        HorizontalLayout dates = new HorizontalLayout();

        //Binder ��� ����������� �������
        Binder<Prescription> binder = new Binder<>(Prescription.class);

        //���� ��������������
        TextArea desc = new TextArea();
        desc.setCaption("��������");
        ComboBox<Patient> patient = new ComboBox();
        patient.setCaption("�������");
        ComboBox<Doctor> doctor = new ComboBox();
        doctor.setCaption("����");
        DateField dateOfCreation = new DateField();
        dateOfCreation.setCaption("���� ����������");
        DateField validityPeriod = new DateField();
        dateOfCreation.setCaption("���� ��������� ��������");
        ComboBox<String> priority = new ComboBox();
        priority.setCaption("���������");

        desc.setWidth("96%");

        //������
        Button ok = new Button("OK");
        Button cancel = new Button("������");

        //������ ��������� ��� ���� ComboBox
        patient.setItems(ServiceController.getPatientList());
        patient.setItemCaptionGenerator(item -> item.getFullName());
        patient.setEmptySelectionAllowed(true);
        patient.setEmptySelectionCaption("---");
        doctor.setItems(ServiceController.getDoctorList());
        doctor.setItemCaptionGenerator(item -> item.getFullName());
        doctor.setEmptySelectionAllowed(true);
        doctor.setEmptySelectionCaption("---");
        priority.setItems("����������", "�������", "�����������");
        priority.setEmptySelectionAllowed(false);
        priority.setValue("����������");

        //��������� ����� ��� ����������� �������
        binder.forField(desc)
                .withValidator(str -> str.length() <= 256, "������������ ����� - 256 ������")
                .asRequired("������������ ��������")
                .bind("description");

        binder.forField(patient)
                .asRequired("������������ ��������")
                .bind("patient");

        binder.forField(doctor)
                .asRequired("������������ ��������")
                .bind("doctor");

        binder.forField(dateOfCreation)
                .asRequired("������������ ��������")
                .bind("dateOfCreation");

        binder.forField(validityPeriod)
                .withValidator(localDate -> localDate.isAfter(dateOfCreation.getValue()),
                        "���� ��������� �������� �� ����� ���� ������ ���� ����������")
                .asRequired("������������ ��������")
                .bind("validityPeriod");

        binder.forField(priority)
                .asRequired("������������ ��������")
                .bind("priority");

        ok.setEnabled(false);

        binder.addStatusChangeListener(statusChangeEvent -> {
            ok.setEnabled(!statusChangeEvent.hasValidationErrors());
        });

        //��������� �������� ����������� ������� � ���� ��������������
        binder.readBean(prescription);

        //������ ��� ������
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

        //���������� �����������
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
