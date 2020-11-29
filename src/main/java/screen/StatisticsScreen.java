package screen;

import service.ServiceController;
import system.Statistics;

import com.vaadin.annotations.Theme;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;



@SuppressWarnings("serial")
@Theme(ValoTheme.THEME_NAME)
public class StatisticsScreen extends Window {
	
	public StatisticsScreen() {
        super("Статистика рецептов");
        setHeight("460px");
        setWidth("520px");

        AbsoluteLayout content = new AbsoluteLayout();
        content.setSizeFull();

        Grid<Statistics> grid = new Grid<>();
        ListDataProvider<Statistics> statList = DataProvider.ofCollection(ServiceController.getDoctorPrescriptionStat());

        grid.setDataProvider(statList);

        grid.addColumn(Statistics::getFullDoctorName).setCaption("Врач");
        grid.addColumn(Statistics::getNum).setCaption("Кол-во рецептов");

        content.addComponent(grid,"top: 2%; left: 2%;");

        center();
        setContent(content);
    }

}
