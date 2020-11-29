package screen;

import service.ServiceController;
import system.Patient;

import com.vaadin.annotations.Theme;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

@SuppressWarnings("serial")
@Theme(ValoTheme.THEME_NAME)
public class PatientScreen extends Window {
	
	private static ListDataProvider<Patient> patientList;
    private Grid<Patient> grid = new Grid<>();
    private static ArrayList<Grid<Patient>> gridList = new ArrayList<>();
    
    public PatientScreen() {
        super("��������");
        setHeight("400px");
        setWidth("600px");

        //���������� ����: ������ � �������
        AbsoluteLayout content = new AbsoluteLayout();
        content.setSizeFull();
        HorizontalLayout buttons = new HorizontalLayout();

        Button add = new Button("��������");
        Button change = new Button("��������");
        Button delete = new Button("�������");

        //������� ��������� ������
        AtomicReference<Patient> selectedPatient = new AtomicReference<Patient>();

        //������ ��������/������� ������������� �� ������ ��������
        change.setEnabled(false);
        delete.setEnabled(false);

        //��������� �������� � �������
        patientList = DataProvider.ofCollection(ServiceController.getPatientList());
        grid.setDataProvider(patientList);

        //��������� ������� � �������
        grid.setHeight("80%");
        grid.setWidth("98%");
        grid.addColumn(Patient::getSurname).setCaption("�������");
        grid.addColumn(Patient::getName).setCaption("���");
        grid.addColumn(Patient::getPatronymic).setCaption("��������");
        grid.addColumn(Patient::getPhoneNumber).setCaption("����� ��������");

        //���������� ������� � ������ ����������� ������
        gridList.add(grid);

        //����� �������� �������: ���� ������ �����-���� �������
        //���� ����������� ��������������� ��� ������� ���
        grid.asSingleSelect().addValueChangeListener(valueChangeEvent -> {
            selectedPatient.set(valueChangeEvent.getValue());
            if (valueChangeEvent.getValue() != null) {
                change.setEnabled(true);
                delete.setEnabled(true);
                System.out.println("Selected patient: " + valueChangeEvent.getValue().getId());
            }
            else {
                change.setEnabled(false);
                delete.setEnabled(false);
            }
        });

        //������ ������ ����������/���������/��������
        add.addClickListener(clickEvent -> {
            selectedPatient.set(new Patient(-1,"", "", "", ""));
            getUI().addWindow(new PatientEditScreen(selectedPatient.get(), MainUI.OPTIONS.ADD));
        });

        change.addClickListener(clickEvent -> {
            getUI().addWindow(new PatientEditScreen(selectedPatient.get(), MainUI.OPTIONS.UPDATE));
        });

        delete.addClickListener(clickEvent -> {
            int errCode = ServiceController.deletePatient(selectedPatient.get().getId());
            if (errCode == 1){
                VerticalLayout errContent = new VerticalLayout();
                errContent.addComponent(new Label("������ ������� ��������, ��� �������� ���������� �������"));
                Window err = new Window("������");
                err.setContent(errContent);
                err.setModal(true);
                err.center();
                getUI().addWindow(err);
            }
            if(errCode == 0) RefreshList();
        });

        //�������� ������� �� ������ ��� �������� ����
        addCloseListener(closeEvent -> {
            gridList.remove(grid);
        });

        //��������� �����������
        content.addComponent(grid,"top: 2%; left: 2%;");
        buttons.addComponent(add);
        buttons.addComponent(change);
        buttons.addComponent(delete);
        content.addComponent(buttons, "top: 86%; left: 2%;");

        center();
        setContent(content);
    }
    //�������� ����������
    public static void RefreshList() {
        //���������� DataProvider
        patientList = DataProvider.ofCollection(ServiceController.getPatientList());
        //��������� ������������ DataProvider ��� ������ �������
        gridList.forEach(grid -> {grid.setDataProvider(patientList);});
    }
}
