package screen;

import service.ServiceController;
import system.Doctor;

import com.vaadin.annotations.Theme;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

@SuppressWarnings("serial")
@Theme(ValoTheme.THEME_NAME)
public class DoctorScreen extends Window {

	 private static ListDataProvider<Doctor> docList;
	 private Grid<Doctor> grid = new Grid<>();
	 private static ArrayList<Grid<Doctor>> gridList = new ArrayList<>();
	 
	 public DoctorScreen(){
	        super("�����");
	        setHeight("400px");
	        setWidth("600px");
	        
	        //���������� ����: ������ � �������
	        AbsoluteLayout content = new AbsoluteLayout();
	        content.setSizeFull();
	        HorizontalLayout buttons = new HorizontalLayout();

	        Button add = new Button("��������");
	        Button change = new Button("��������");
	        Button delete = new Button("�������");
	        Button stat = new Button("����������");

	        //������� ��������� ������
	        AtomicReference<Doctor> selectedDoctor = new AtomicReference<Doctor>();

	        //������ ��������/������� ������������� �� ������ ��������
	        change.setEnabled(false);
	        delete.setEnabled(false);

	        //��������� �������� � �������
	        docList = DataProvider.ofCollection(ServiceController.getDoctorList());
	        grid.setDataProvider(docList);

	        //��������� ������� � �������
	        grid.setHeight("80%");
	        grid.setWidth("98%");
	        grid.addColumn(Doctor::getSurname).setCaption("�������");
	        grid.addColumn(Doctor::getName).setCaption("���");
	        grid.addColumn(Doctor::getPatronymic).setCaption("��������");
	        grid.addColumn(Doctor::getSpeciality).setCaption("�������������");

	        //���������� ������� � ������ ����������� ������
	        gridList.add(grid);

	        //����� �������� �������: ���� ������ �����-���� �������
	        //���� ����������� ��������������� ��� ������� ���
	        grid.asSingleSelect().addValueChangeListener(valueChangeEvent -> {
	            selectedDoctor.set(valueChangeEvent.getValue());
	            if (valueChangeEvent.getValue() != null) {
	                change.setEnabled(true);
	                delete.setEnabled(true);
	                System.out.println("Selected doctor: " + valueChangeEvent.getValue().getId());
	            }
	            else {
	                change.setEnabled(false);
	                delete.setEnabled(false);
	            }
	        	}
	        );

	        //������ ������ ����������/���������/��������
	        add.addClickListener(clickEvent -> {
	            selectedDoctor.set(new Doctor(-1,"", "", "", ""));
	            getUI().addWindow(new DoctorEditScreen(selectedDoctor.get(), MainUI.OPTIONS.ADD));
	        	}
	        );

	        change.addClickListener(clickEvent -> {
	            getUI().addWindow(new DoctorEditScreen(selectedDoctor.get(), MainUI.OPTIONS.UPDATE));
	        	}
	        );

	        delete.addClickListener(clickEvent -> {
	            int errCode = ServiceController.deleteDoctor(selectedDoctor.get().getId());
	            if (errCode == 1){
	                VerticalLayout errContent = new VerticalLayout();
	                errContent.addComponent(new Label("������ ������� �����, ��� �������� ���������� �������"));
	                Window err = new Window("������");
	                err.setContent(errContent);
	                err.setModal(true);
	                err.center();
	                getUI().addWindow(err);
	            }
	            if(errCode == 0) 
	            	RefreshList();
	        	}
	        );

	        stat.addClickListener(clickEvent -> {
	            getUI().addWindow(new StatisticsScreen());
	        	}
	        );

	        //�������� ������� �� ������ ��� �������� ����
	        addCloseListener(closeEvent -> {
	            gridList.remove(grid);
	        	}
	        );

	        //��������� �����������
	        content.addComponent(grid,"top: 2%; left: 2%;");
	        buttons.addComponent(add);
	        buttons.addComponent(change);
	        buttons.addComponent(delete);
	        buttons.addComponent(stat);
	        content.addComponent(buttons, "top: 86%; left: 2%;");

	        center();
	        setContent(content);
	    }
	    //�������� ����������
	    public static void RefreshList() {
	        //���������� DataProvider
	        docList = DataProvider.ofCollection(ServiceController.getDoctorList());
	        //��������� ������������ DataProvider ��� ������ �������
	        gridList.forEach(grid -> {grid.setDataProvider(docList);});
	    }
}