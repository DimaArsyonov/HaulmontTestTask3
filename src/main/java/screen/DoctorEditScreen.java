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
        Binder<Doctor> binder = new Binder<>(Doctor.class);

        //���� ��������������
        TextField name = new TextField();
        name.setCaption("���");
        TextField surname = new TextField();
        surname.setCaption("�������");
        TextField patronymic = new TextField();
        patronymic.setCaption("��������");
        TextField spec = new TextField();
        spec.setCaption("�������������");

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

        binder.forField(spec)
                .withValidator(str -> str.length() <= 64, "������������ ����� - 64 �����")
                .withValidator(str -> str.matches("[- a-zA-Z�-��-�]+"), "������������� �� ����� ��������� ���� � ������������")
                .asRequired("������������ ��������")
                .bind("speciality");

        ok.setEnabled(false);

        binder.addStatusChangeListener(statusChangeEvent -> {
            ok.setEnabled(!statusChangeEvent.hasValidationErrors());
        });

        //��������� �������� ����������� ������� � ���� ��������������
        binder.readBean(doctor);

        //������ ��� ������
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

        //���������� �����������
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
