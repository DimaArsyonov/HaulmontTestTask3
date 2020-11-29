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
        //Заголовки и размер окна
        super("Рецепты");
        setHeight("500px");
        setWidth("1340px");

        //Содержимое окна: кнопки и таблица
        AbsoluteLayout content = new AbsoluteLayout();
        content.setSizeFull();
        HorizontalLayout buttons = new HorizontalLayout();

        Button add = new Button("Добавить");
        Button change = new Button("Изменить");
        Button delete = new Button("Удалить");

        //Текущий выбранный объект
        AtomicReference<Prescription> selectedPrescription = new AtomicReference<Prescription>();

        //Кнопки изменить/удалить заблокированы до выбора значения
        change.setEnabled(false);
        delete.setEnabled(false);

        //Фильтр и его поля
        HorizontalLayout filter = new HorizontalLayout();
        TextField desc = new TextField();
        desc.setCaption("Описание");
        ComboBox<Patient> patient = new ComboBox();
        patient.setCaption("Пациент");
        ComboBox<String> priority = new ComboBox();
        priority.setCaption("Приоритет");
        Button apply = new Button("Применить");
        apply.setHeight("100%");

        //Установка возможных значений полям фильтра
        patient.setItems(ServiceController.getPatientList());
        patient.setItemCaptionGenerator(item -> item.getFullName());
        patient.setEmptySelectionAllowed(true);
        patient.setEmptySelectionCaption("---");
        priority.setItems("Нормальный", "Срочный", "Немедленный");
        priority.setEmptySelectionAllowed(true);
        priority.setEmptySelectionCaption("---");

        filter.addComponent(desc);
        filter.addComponent(patient);
        filter.addComponent(priority);
        filter.addComponent(apply);

        //Логика фильтра
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

        //Установка значений в таблице
        prescriptionList = DataProvider.ofCollection(ServiceController.getPrescriptionList());
        grid.setDataProvider(prescriptionList);

        //Установка колонок в таблице
        grid.setHeight("75%");
        grid.setWidth("98%");
        grid.addColumn(Prescription::getDescription).setCaption("Описание");
        grid.addColumn(Prescription::getFullPatientName).setCaption("Пациент");
        grid.addColumn(Prescription::getFullDoctorName).setCaption("Врач");
        grid.addColumn(Prescription::getDateOfCreation).setCaption("Дата назначения");
        grid.addColumn(Prescription::getValidityPeriod).setCaption("Дата окончания действия");
        grid.addColumn(Prescription::getPriority).setCaption("Приоритет");

        //Добавление таблицы в список обновляемых таблиц
        gridList.add(grid);

        //Выбор элемента таблицы: если выбран какой-либо элемент
        //Есть возможность отредактировать или удалить его
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

        //Логика кнопок добавления/изменения/удаления
        add.addClickListener(clickEvent -> {
            selectedPrescription.set(new Prescription(-1, "", null, null, null, null, "Нормальный"));
            getUI().addWindow(new PrescriptionEditScreen(selectedPrescription.get(), MainUI.OPTIONS.ADD));
        });

        change.addClickListener(clickEvent -> {
            getUI().addWindow(new PrescriptionEditScreen(selectedPrescription.get(), MainUI.OPTIONS.UPDATE));
        });

        delete.addClickListener(clickEvent -> {
            ServiceController.deletePrescription(selectedPrescription.get().getId());
            RefreshList();
        });

        //Удаление таблицы из списка при закрытии окна
        addCloseListener(closeEvent -> {
            gridList.remove(grid);
        });

        //Установка содержимого
        content.addComponent(filter, "top: 2%; left: 2%;");
        content.addComponent(grid,"top: 17%; left: 2%;");
        buttons.addComponent(add);
        buttons.addComponent(change);
        buttons.addComponent(delete);
        content.addComponent(buttons, "top: 86%; left: 2%;");

        center();
        setContent(content);
    }
    //Обновить содержимое
    public static void RefreshList(){
        //Обновление DataProvider
        prescriptionList = DataProvider.ofCollection(ServiceController.getPrescriptionList());
        //Установка обновленного DataProvider для каждой таблицы
        gridList.forEach(grid -> {
            grid.setDataProvider(prescriptionList);
        });
    }

}
