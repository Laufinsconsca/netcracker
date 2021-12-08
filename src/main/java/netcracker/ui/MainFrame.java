package netcracker.ui;

import netcracker.buildings.Building;
import netcracker.buildings.Buildings;
import netcracker.buildings.Floor;
import netcracker.buildings.Space;
import netcracker.buildings.dwelling.Dwelling;
import netcracker.buildings.dwelling.hotel.Hotel;
import netcracker.buildings.factory.BuildingFactory;
import netcracker.buildings.factory.impl.DwellingFactory;
import netcracker.buildings.factory.impl.HotelFactory;
import netcracker.buildings.factory.impl.OfficeFactory;
import netcracker.buildings.office.OfficeBuilding;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainFrame extends JFrame {

    private static boolean IS_NIMBUS;
    private JTextArea buildingTotalAreaTextArea;
    private JPanel buildingViewPanel;
    private JTextArea floorNumberTextArea;
    private JTextArea floorTotalAreaTextArea;
    private JMenu lookAndFeelMenu;
    private JPanel mainPanel;
    private JTextArea numberOfFloorsTextArea;
    private JTextArea numberOfRoomsTextArea;
    private JTextArea numberOfSpacesTextArea;
    private JTextArea spaceNumberTextArea;
    private JTextArea spaceTotalAreaTextArea;
    private JTextArea typeOfBuildingTextArea;

    public MainFrame() {
        initComponents();
    }

    public static void main(String[] args) {
        Arrays.stream(UIManager.getInstalledLookAndFeels()).filter(lookAndFeelInfo -> lookAndFeelInfo.getName().equals("Nimbus")).forEach(lookAndFeelInfo -> {
            try {
                UIManager.setLookAndFeel(lookAndFeelInfo.getClassName());
                IS_NIMBUS = true;
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
                e.printStackTrace();
            }
        });
        EventQueue.invokeLater(() -> new MainFrame().setVisible(true));
    }

    private void initComponents() {
        // <создание 3 информационных панелей>
        typeOfBuildingTextArea = new JTextArea();
        numberOfFloorsTextArea = new JTextArea();
        buildingTotalAreaTextArea = new JTextArea();
        floorNumberTextArea = new JTextArea();
        numberOfSpacesTextArea = new JTextArea();
        floorTotalAreaTextArea = new JTextArea();
        spaceNumberTextArea = new JTextArea();
        numberOfRoomsTextArea = new JTextArea();
        spaceTotalAreaTextArea = new JTextArea();
        JPanel buildingInfoPanel = new JPanel();
        JPanel floorInfoPanel = new JPanel();
        JPanel spaceInfoPanel = new JPanel();
        createInfoPanel(buildingInfoPanel, typeOfBuildingTextArea, numberOfFloorsTextArea, buildingTotalAreaTextArea, "Информация о здании:", "Тип здания:", "Количество этажей:");
        createInfoPanel(floorInfoPanel, floorNumberTextArea, numberOfSpacesTextArea, floorTotalAreaTextArea, "Информация о этаже:", "Номер этажа:", "Количество помещений:");
        createInfoPanel(spaceInfoPanel, spaceNumberTextArea, numberOfRoomsTextArea, spaceTotalAreaTextArea, "Информация о помещении:", "Номер в здании:", "Количество комнат:");
        // </создание 3 информационных панелей>

        // <настройка меню>
        JMenuBar menuBar = new JMenuBar();
        JMenu openBuildingMenu = new JMenu();
        openBuildingMenu.setText("File");
        createOpenBuildingMenuItem(openBuildingMenu, "Open dwelling", new DwellingFactory());
        createOpenBuildingMenuItem(openBuildingMenu, "Open office building", new OfficeFactory());
        createOpenBuildingMenuItem(openBuildingMenu, "Open hotel", new HotelFactory());
        menuBar.add(openBuildingMenu);
        lookAndFeelMenu = new JMenu();
        lookAndFeelMenu.setText("Look&Feel");
        initLookAndFeelMenu();
        menuBar.add(lookAndFeelMenu);
        setJMenuBar(menuBar);
        // </настройка меню>

        // <размещение элементов на форме>
        mainPanel = new JPanel();
        buildingViewPanel = new JPanel();
        buildingViewPanel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        buildingViewPanel.setLayout(new BoxLayout(buildingViewPanel, BoxLayout.Y_AXIS));
        JScrollPane buildingViewScrollPane = new JScrollPane();
        buildingViewScrollPane.setViewportView(buildingViewPanel);
        GroupLayout mainPanelLayout = new GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
                mainPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(mainPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(buildingViewScrollPane, GroupLayout.PREFERRED_SIZE, 300, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(mainPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(buildingInfoPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(floorInfoPanel, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(spaceInfoPanel, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addContainerGap())
        );
        mainPanelLayout.setVerticalGroup(
                mainPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(mainPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(mainPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(buildingViewScrollPane, GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
                                        .addGroup(mainPanelLayout.createSequentialGroup()
                                                .addComponent(buildingInfoPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(floorInfoPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(spaceInfoPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                .addGap(0, 0, Short.MAX_VALUE)))
                                .addContainerGap())
        );
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(mainPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(mainPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        // </размещение элементов на форме>

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
    }

    private void createOpenBuildingMenuItem(JMenu openBuildingMenu, String title, BuildingFactory factory) {
        JMenuItem openBuildingMenuItem = new JMenuItem();
        openBuildingMenuItem.setText(title + "...");
        openBuildingMenuItem.addActionListener(e -> {
            Buildings.setBuildingFactory(factory);
            JFileChooser jFileChooser = new JFileChooser();
            jFileChooser.setCurrentDirectory(new File("src/main/resources"));
            jFileChooser.setDialogTitle(title);
            viewBuilding(jFileChooser, jFileChooser.showOpenDialog(mainPanel));
        });
        openBuildingMenu.add(openBuildingMenuItem);
    }

    private void initLookAndFeelMenu() {
        List<JRadioButtonMenuItem> jRadioButtonMenuItemList = new ArrayList<>();
        for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
            JRadioButtonMenuItem jRadioButtonMenuItem = new JRadioButtonMenuItem();
            jRadioButtonMenuItem.setSelected(false);
            jRadioButtonMenuItem.setText(info.getName());
            jRadioButtonMenuItemList.add(jRadioButtonMenuItem);
            jRadioButtonMenuItem.addActionListener(event -> {
                try {
                    jRadioButtonMenuItemList.forEach(jRadioButtonMenuItem1 -> jRadioButtonMenuItem1.setSelected(false));
                    UIManager.setLookAndFeel(info.getClassName());
                    SwingUtilities.updateComponentTreeUI(this);
                    jRadioButtonMenuItem.setSelected(true);
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                    java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                }
            });
            lookAndFeelMenu.add(jRadioButtonMenuItem);
        }
        if (IS_NIMBUS) {
            jRadioButtonMenuItemList.get(1).setSelected(true);
        }
    }

    private void createInfoPanel(JPanel infoPanel, JTextArea firstTextArea, JTextArea secondTextArea, JTextArea totalAreaTextArea, String jLabel1Text, String jLabel2Text, String jLabel3Text) {
        JLabel jLabel1 = new JLabel();
        JLabel jLabel2 = new JLabel();
        JLabel jLabel3 = new JLabel();
        JLabel jLabel4 = new JLabel();
        JScrollPane jScrollPane1 = new JScrollPane();
        JScrollPane jScrollPane2 = new JScrollPane();
        JScrollPane jScrollPane3 = new JScrollPane();

        jLabel1.setText(jLabel1Text);
        jLabel1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        adjustInfoField(firstTextArea, jScrollPane1, jLabel2, jLabel2Text);
        adjustInfoField(secondTextArea, jScrollPane2, jLabel3, jLabel3Text);
        adjustInfoField(totalAreaTextArea, jScrollPane3, jLabel4, "Общая площадь:");

        GroupLayout infoPanelLayout = new GroupLayout(infoPanel);
        infoPanel.setLayout(infoPanelLayout);
        infoPanelLayout.setHorizontalGroup(
                infoPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(infoPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(infoPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(infoPanelLayout.createSequentialGroup()
                                                .addComponent(jLabel2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addGap(18, 18, 18)
                                                .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                        .addGroup(GroupLayout.Alignment.TRAILING, infoPanelLayout.createSequentialGroup()
                                                .addComponent(jLabel3, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addGap(18, 18, 18)
                                                .addComponent(jScrollPane2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                        .addGroup(infoPanelLayout.createSequentialGroup()
                                                .addComponent(jLabel4, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addGap(18, 18, 18)
                                                .addComponent(jScrollPane3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap())
        );
        infoPanelLayout.setVerticalGroup(
                infoPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(infoPanelLayout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(infoPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jScrollPane1)
                                        .addComponent(jLabel2, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(infoPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jScrollPane2)
                                        .addComponent(jLabel3, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(infoPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jScrollPane3)
                                        .addComponent(jLabel4, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)))
        );
    }

    private void adjustInfoField(JTextArea textArea, JScrollPane scrollPane, JLabel label, String labelText) {
        textArea.setEditable(false);
        textArea.setColumns(20);
        textArea.setRows(1);
        scrollPane.setViewportView(textArea);
        label.setText(labelText);
        label.setVerticalAlignment(SwingConstants.BOTTOM);
    }

    private void viewBuilding(JFileChooser jFileChooser, int result) {
        buildingViewPanel.removeAll();
        int i = 1, j = 1;
        Building building;
        if (result == JFileChooser.APPROVE_OPTION) {
            try (Reader stream = new FileReader(jFileChooser.getSelectedFile())) {
                building = Buildings.readBuilding(stream);
                if (building.getNumberOfFloors() == 0) {
                    throw new IOException("Файл содержит некорректные данные");
                }
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } else {
            return;
        }
        if (building instanceof Dwelling) {
            typeOfBuildingTextArea.setText("Dwelling");
        }
        if (building instanceof OfficeBuilding) {
            typeOfBuildingTextArea.setText("Office building");
        }
        if (building instanceof Hotel) {
            typeOfBuildingTextArea.setText("Hotel");
        }
//        switch (building) {
//            case Hotel hotel -> typeOfBuildingTextArea.setText("Hotel");
//            case Dwelling dwelling -> typeOfBuildingTextArea.setText("Dwelling");
//            case OfficeBuilding officeBuilding -> typeOfBuildingTextArea.setText("Office building");
//            case null -> {}
//        }
        numberOfFloorsTextArea.setText(Integer.toString(building.getNumberOfFloors()));
        buildingTotalAreaTextArea.setText(Double.toString(building.getTotalArea()));
        for (Floor floor : building) {
            JPanel floorViewPanel = new JPanel();
            floorViewPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createTitledBorder(""), i + " этаж"));
            floorViewPanel.setMaximumSize(new Dimension(32767, 75));
            int finalI = i;
            floorViewPanel.addMouseListener(new MouseAdapter() {

                @Override
                public void mouseClicked(MouseEvent evt) {
                    floorNumberTextArea.setText(Integer.toString(finalI));
                    numberOfSpacesTextArea.setText(Integer.toString(floor.getNumberOfSpaces()));
                    floorTotalAreaTextArea.setText(Double.toString(floor.getTotalArea()));
                    spaceNumberTextArea.setText("");
                    numberOfRoomsTextArea.setText("");
                    spaceTotalAreaTextArea.setText("");
                }
            });
            floorViewPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 0, 0));

            for (Space space : floor) {
                JButton spaceViewButton = new JButton();
                spaceViewButton.setText(Integer.toString(j));
                spaceViewButton.setMinimumSize(new Dimension(0, 30));
                spaceViewButton.setMaximumSize(new Dimension(32767, 30));
                int finalJ = j;
                spaceViewButton.addActionListener(e -> {
                    floorNumberTextArea.setText(Integer.toString(finalI));
                    numberOfSpacesTextArea.setText(Integer.toString(floor.getNumberOfSpaces()));
                    floorTotalAreaTextArea.setText(Double.toString(floor.getTotalArea()));
                    spaceNumberTextArea.setText(Integer.toString(finalJ));
                    numberOfRoomsTextArea.setText(Integer.toString(space.getNumberOfRooms()));
                    spaceTotalAreaTextArea.setText(Double.toString(space.getArea()));
                });
                floorViewPanel.add(spaceViewButton);
                j++;
            }
            j = 1;
            i++;
            buildingViewPanel.add(floorViewPanel);
        }
        SwingUtilities.updateComponentTreeUI(this);
    }
}
