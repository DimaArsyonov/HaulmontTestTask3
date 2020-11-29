package screen;

import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;


@SuppressWarnings("serial")
@Theme(ValoTheme.THEME_NAME)
public class MainUI extends UI {
	public enum OPTIONS{
		UPDATE,
	    ADD
	    }
	    
	@Override
	protected void init(VaadinRequest request) {
		AbsoluteLayout layout = new AbsoluteLayout();
	    layout.setSizeFull();

	    Label MedicalBase = new Label("Медицинская база данных");
	    MedicalBase.addStyleName(ValoTheme.LABEL_H1);

	    HorizontalLayout Buttons = new HorizontalLayout();
	    Button Patients = new Button("Пациенты");
	    Button Doctors = new Button("Врачи");
	    Button Prescriptions = new Button("Рецепты");

	    //Логика кнопок
	    Patients.addClickListener(clickEvent -> {
	    	addWindow(new PatientScreen());
	        }
	    );
	    Doctors.addClickListener(clickEvent -> {
	        addWindow(new DoctorScreen());
	        }
	    );
	    Prescriptions.addClickListener(clickEvent -> {
	        addWindow(new PrescriptionScreen());
	        }
	    );

	    //Добавление компонентов
	    layout.addComponent(MedicalBase, "left: 50px");
	    Buttons.addComponent(Patients);
	    Buttons.addComponent(Doctors);
	    Buttons.addComponent(Prescriptions);
	    layout.addComponent(Buttons, "left: 50px; top: 130px");

	    setContent(layout);
	}
}
