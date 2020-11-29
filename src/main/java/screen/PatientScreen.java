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
        super("Пациенты");
        setHeight("400px");
        setWidth("600px");

        //Содержимое окна: кнопки и таблица
        AbsoluteLayout content = new AbsoluteLayout();
        content.setSizeFull();
        HorizontalLayout buttons = new HorizontalLayout();

        Button add = new Button("Добавить");
        Button change = new Button("Изменить");
        Button delete = new Button("Удалить");

        //Текущий выбранный объект
        AtomicReference<Patient> selectedPatient = new AtomicReference<Patient>();

        //Кнопки изменить/удалить заблокированы до выбора значения
        change.setEnabled(false);
        delete.setEnabled(false);

        //Установка значений в таблице
        patientList = DataProvider.ofCollection(ServiceController.getPatientList());
        grid.setDataProvider(patientList);

        //Установка колонок в таблице
        grid.setHeight("80%");
        grid.setWidth("98%");
        grid.addColumn(Patient::getSurname).setCaption("Фамилия");
        grid.addColumn(Patient::getName).setCaption("Имя");
        grid.addColumn(Patient::getPatronymic).setCaption("Отчество");
        grid.addColumn(Patient::getPhoneNumber).setCaption("Номер телефона");

        //Добавление таблицы в список обновляемых таблиц
        gridList.add(grid);

        //Выбор элемента таблицы: если выбран какой-либо элемент
        //Есть возможность отредактировать или удалить его
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

        //Логика кнопок добавления/изменения/удаления
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
                errContent.addComponent(new Label("Нельзя удалить пациента, для которого существуют рецепты"));
                Window err = new Window("Ошибка");
                err.setContent(errContent);
                err.setModal(true);
                err.center();
                getUI().addWindow(err);
            }
            if(errCode == 0) RefreshList();
        });

        //Удаление таблицы из списка при закрытии окна
        addCloseListener(closeEvent -> {
            gridList.remove(grid);
        });

        //Установка содержимого
        content.addComponent(grid,"top: 2%; left: 2%;");
        buttons.addComponent(add);
        buttons.addComponent(change);
        buttons.addComponent(delete);
        content.addComponent(buttons, "top: 86%; left: 2%;");

        center();
        setContent(content);
    }
    //Обновить содержимое
    public static void RefreshList() {
        //Обновление DataProvider
        patientList = DataProvider.ofCollection(ServiceController.getPatientList());
        //Установка обновленного DataProvider для каждой таблицы
        gridList.forEach(grid -> {grid.setDataProvider(patientList);});
    }
}
