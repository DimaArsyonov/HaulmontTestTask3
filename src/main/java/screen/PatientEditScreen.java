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

        //��������� ���������
        String caption;
        if(opt == MainUI.OPTIONS.ADD) caption = "��������";
        else caption = "��������";
        setCaption(caption);

        //��������� �������� �����������
        VerticalLayout content = new VerticalLayout();
        content.setSizeFull();
        HorizontalLayout buttons = new HorizontalLayout();

        //Binder ��� ����������� �������
        Binder<Patient> binder = new Binder<>(Patient.class);

        //���� ��������������
        TextField name = new TextField();
        name.setCaption("���");
        TextField surname = new TextField();
        surname.setCaption("�������");
        TextField patronymic = new TextField();
        patronymic.setCaption("��������");
        TextField num = new TextField();
        num.setCaption("����� ��������");

        //������
        Button ok = new Button("OK");
        Button cancel = new Button("������");

        //��������� ����� ��� ����������� �������
        binder.forField(name)
                .withValidator(str -> str.length() <= 64, "������������ ����� - 64 �����")
                .withValidator(str -> str.matches("[-a-zA-Z�-��-�]+"),
                        "��� �� ����� ��������� ����, �������� � ������������")
                .asRequired("������������ ��������")
                .bind("name");

        binder.forField(surname)
                .withValidator(str -> str.length() <= 64, "������������ ����� - 64 �����")
                .withValidator(str -> str.matches("[-a-zA-Z�-��-�]+"),
                        "��� �� ����� ��������� ����, �������� � ������������")
                .asRequired("������������ ��������")
                .bind("surname");

        binder.forField(patronymic)
                .withValidator(str -> str.length() <= 64, "������������ ����� - 64 �����")
                .withValidator(str -> str.matches("[-a-zA-Z�-��-�]+"),
                        "��� �� ����� ��������� ����, �������� � ������������")
                .asRequired("������������ ��������")
                .bind("patronymic");

        binder.forField(num)
                .withValidator(str -> str.matches("[+]?\\d{1}[(]{1}\\d{3}[)]{1}\\d{3}[-]?\\d{2}[-]?\\d{2}")
                        || str.matches("\\d{3}[-]?\\d{2}[-]?\\d{2}"),
                        "���������� ����� �� ����� ��������� ���� � �������� � ������ ���� ������� ���-��-�� ��� +�(���)���-��-��")
                .asRequired("������������ ��������")
                .bind("phoneNumber");

        ok.setEnabled(false);

        binder.addStatusChangeListener(statusChangeEvent -> {
            ok.setEnabled(!statusChangeEvent.hasValidationErrors());
        });

        //��������� �������� ����������� ������� � ���� ��������������
        binder.readBean(patient);

        //������ ��� ������
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

        //���������� �����������
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
