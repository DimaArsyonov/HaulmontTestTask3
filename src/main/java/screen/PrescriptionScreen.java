package screen;

import service.ServiceController;
import system.Patient;
import system.Prescription;

import com.vaadin.annotations.Theme;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

@SuppressWarnings("serial")
@Theme(ValoTheme.THEME_NAME)
public class PrescriptionScreen extends Window {
	
	private static ListDataProvider<Prescription> prescriptionList;
    private Grid<Prescription> grid = new Grid<>();
    private static ArrayList<Grid<Prescription>> gridList = new ArrayList<>();

    public PrescriptionScreen(){
        //��������� � ������ ����
        super("�������");
        setHeight("500px");
        setWidth("1340px");

        //���������� ����: ������ � �������
        AbsoluteLayout content = new AbsoluteLayout();
        content.setSizeFull();
        HorizontalLayout buttons = new HorizontalLayout();

        Button add = new Button("��������");
        Button change = new Button("��������");
        Button delete = new Button("�������");

        //������� ��������� ������
        AtomicReference<Prescription> selectedPrescription = new AtomicReference<Prescription>();

        //������ ��������/������� ������������� �� ������ ��������
        change.setEnabled(false);
        delete.setEnabled(false);

        //������ � ��� ����
        HorizontalLayout filter = new HorizontalLayout();
        TextField desc = new TextField();
        desc.setCaption("��������");
        ComboBox<Patient> patient = new ComboBox();
        patient.setCaption("�������");
        ComboBox<String> priority = new ComboBox();
        priority.setCaption("���������");
        Button apply = new Button("���������");
        apply.setHeight("100%");

        //��������� ��������� �������� ����� �������
        patient.setItems(ServiceController.getPatientList());
        patient.setItemCaptionGenerator(item -> item.getFullName());
        patient.setEmptySelectionAllowed(true);
        patient.setEmptySelectionCaption("---");
        priority.setItems("����������", "�������", "�����������");
        priority.setEmptySelectionAllowed(true);
        priority.setEmptySelectionCaption("---");

        filter.addComponent(desc);
        filter.addComponent(patient);
        filter.addComponent(priority);
        filter.addComponent(apply);

        //������ �������
        apply.addClickListener(clickEvent -> {
            ListDataProvider<Prescription> p = DataProvider.ofCollection(prescriptionList.getItems());
            p.setFilter((prescription) -> {
                boolean descMatch = true;
                boolean patMatch = true;
                boolean prioMatch = true;
                if(desc.getValue() != null) descMatch = prescription.getDescription().contains(desc.getValue());
                if(patient.getValue() != null) patMatch = (prescription.getPatient().getId() == patient.getValue().getId());
                if(priority.getValue() != null) prioMatch = prescription.getPriority().equalsIgnoreCase(priority.getValue());
                return descMatch && patMatch && prioMatch;
            });
            grid.setDataProvider(p);
        });

        //��������� �������� � �������
        prescriptionList = DataProvider.ofCollection(ServiceController.getPrescriptionList());
        grid.setDataProvider(prescriptionList);

        //��������� ������� � �������
        grid.setHeight("75%");
        grid.setWidth("98%");
        grid.addColumn(Prescription::getDescription).setCaption("��������");
        grid.addColumn(Prescription::getFullPatientName).setCaption("�������");
        grid.addColumn(Prescription::getFullDoctorName).setCaption("����");
        grid.addColumn(Prescription::getDateOfCreation).setCaption("���� ����������");
        grid.addColumn(Prescription::getValidityPeriod).setCaption("���� ��������� ��������");
        grid.addColumn(Prescription::getPriority).setCaption("���������");

        //���������� ������� � ������ ����������� ������
        gridList.add(grid);

        //����� �������� �������: ���� ������ �����-���� �������
        //���� ����������� ��������������� ��� ������� ���
        grid.asSingleSelect().addValueChangeListener(valueChangeEvent -> {
            selectedPrescription.set(valueChangeEvent.getValue());
            if (valueChangeEvent.getValue() != null) {
                change.setEnabled(true);
                delete.setEnabled(true);
                System.out.println("Selected prescription: " + valueChangeEvent.getValue().getPriority());
            }
            else {
                change.setEnabled(false);
                delete.setEnabled(false);
            }
        });

        //������ ������ ����������/���������/��������
        add.addClickListener(clickEvent -> {
            selectedPrescription.set(new Prescription(-1, "", null, null, null, null, "����������"));
            getUI().addWindow(new PrescriptionEditScreen(selectedPrescription.get(), MainUI.OPTIONS.ADD));
        });

        change.addClickListener(clickEvent -> {
            getUI().addWindow(new PrescriptionEditScreen(selectedPrescription.get(), MainUI.OPTIONS.UPDATE));
        });

        delete.addClickListener(clickEvent -> {
            ServiceController.deletePrescription(selectedPrescription.get().getId());
            RefreshList();
        });

        //�������� ������� �� ������ ��� �������� ����
        addCloseListener(closeEvent -> {
            gridList.remove(grid);
        });

        //��������� �����������
        content.addComponent(filter, "top: 2%; left: 2%;");
        content.addComponent(grid,"top: 17%; left: 2%;");
        buttons.addComponent(add);
        buttons.addComponent(change);
        buttons.addComponent(delete);
        content.addComponent(buttons, "top: 86%; left: 2%;");

        center();
        setContent(content);
    }
    //�������� ����������
    public static void RefreshList(){
        //���������� DataProvider
        prescriptionList = DataProvider.ofCollection(ServiceController.getPrescriptionList());
        //��������� ������������ DataProvider ��� ������ �������
        gridList.forEach(grid -> {
            grid.setDataProvider(prescriptionList);
        });
    }

}
