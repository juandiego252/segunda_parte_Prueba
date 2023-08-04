import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;


public class root {
    private JTextField codigotextField1;
    private JTextField cedulatextField2;
    private JTextField NombretextField3;
    private JTextField fechatextField4;
    private JComboBox zodiacocomboBox1;
    private JButton borrar;
    private JButton limpiarFormularioButton;
    private JButton ingresarRegistroButton;
    private JButton actualizarRegistroButton;
    private JButton buscarPorCodigoButton;
    private JButton buscarPorNombreButton;
    private JButton buscarPorSignoButton;
    private JPanel root;

    static String DB_URL = "jdbc:mysql://localhost/PRUEBA2";
    static String USER = "root";
    static String PASS = "root_bas3";

    public root() {
        ingresarRegistroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //int codigo = Integer.parseInt(codigotextField1.getText());
                String cedula = cedulatextField2.getText();
                String Nombre = NombretextField3.getText();
                String fecha_naci = fechatextField4.getText();
                String zodiaco = zodiacocomboBox1.getSelectedItem().toString();

                try (Connection conn = DriverManager.getConnection(DB_URL,USER,PASS)){
                    String SQL_QUERY_INSERT = "INSERT INTO personas (cedula,nombre,fecha,signo) \n" +
                            "VALUES (?,?,?,?)";
                    try(PreparedStatement pstmt = conn.prepareStatement(SQL_QUERY_INSERT)){
                        pstmt.setString(1,cedula);
                        pstmt.setString(2,Nombre);
                        pstmt.setString(3,fecha_naci);
                        pstmt.setString(4,zodiaco);

                        int filasInsertadas = pstmt.executeUpdate();
                        if (filasInsertadas > 0){
                            JOptionPane.showMessageDialog(root,"Persona registrada con exito");
                        } else {
                            JOptionPane.showMessageDialog(root,"Error al registrar un estudiante !");
                        }
                    }
                } catch (SQLException ex){
                    ex.printStackTrace();
                }
            }
        });
        limpiarFormularioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                codigotextField1.setText("");
                cedulatextField2.setText("");
                NombretextField3.setText("");
                fechatextField4.setText("");
                zodiacocomboBox1.setSelectedItem("");
            }
        });
        actualizarRegistroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int codigo = Integer.parseInt(codigotextField1.getText());
                String cedula = cedulatextField2.getText();
                String Nombre = NombretextField3.getText();
                String fecha_naci = fechatextField4.getText();
                String zodiaco = zodiacocomboBox1.getSelectedItem().toString();

                try (Connection conn = DriverManager.getConnection(DB_URL,USER,PASS)){
                    String SQL_QUERY_UPDATE = "UPDATE personas SET cedula=?,nombre=?,fecha=?,signo=? WHERE codigo = ?";
                    try(PreparedStatement pstmt = conn.prepareStatement(SQL_QUERY_UPDATE)){
                        pstmt.setString(1,cedula);
                        pstmt.setString(2,Nombre);
                        pstmt.setString(3,fecha_naci);
                        pstmt.setString(4,zodiaco);
                        pstmt.setInt(5,codigo);

                        int filasActualizadas = pstmt.executeUpdate();
                        if (filasActualizadas > 0){
                            JOptionPane.showMessageDialog(root,"Actualizado !");
                        } else {
                            JOptionPane.showMessageDialog(root,"No actualizado");
                        }

                    }

                }catch (SQLException ex){
                    ex.printStackTrace();
                }
            }
        });
        borrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int codigo = Integer.parseInt(codigotextField1.getText());
                try (Connection conn = DriverManager.getConnection(DB_URL,USER,PASS)){
                    String SQL_QUERY_DELETE = "DELETE FROM personas WHERE codigo =?";
                    try (PreparedStatement pstmt = conn.prepareStatement(SQL_QUERY_DELETE)){
                        pstmt.setInt(1,codigo);
                        int filasEliminadas = pstmt.executeUpdate();
                        if (filasEliminadas > 0){
                            JOptionPane.showMessageDialog(root,"Personas eliminada con exito");
                        } else {
                            JOptionPane.showMessageDialog(root,"No se econtro el estudiante !");
                        }
                    }
                }catch (SQLException ex){
                    ex.printStackTrace();
                }
            }
        });
        buscarPorCodigoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int codigo = Integer.parseInt(codigotextField1.getText());
                try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
                    String SQL_QUERY_FIND_COD = "SELECT * FROM personas WHERE codigo = ?";
                    try (PreparedStatement pstmt = conn.prepareStatement(SQL_QUERY_FIND_COD)) {
                        pstmt.setInt(1, codigo);
                        ResultSet resultSet = pstmt.executeQuery();

                        if (resultSet.next()) {
                            // Si el ResultSet tiene al menos una fila, significa que se encontró una persona con el código proporcionado
                            cedulatextField2.setText(resultSet.getString("cedula"));
                            NombretextField3.setText(resultSet.getString("nombre"));
                            fechatextField4.setText(resultSet.getString("fecha"));
                            zodiacocomboBox1.setSelectedItem(resultSet.getString("signo"));
                            JOptionPane.showMessageDialog(root, "Persona encontrada");
                        } else {
                            // No se encontró una persona con el código proporcionado, limpiar los campos de texto
                            cedulatextField2.setText("");
                            NombretextField3.setText("");
                            fechatextField4.setText("");
                            zodiacocomboBox1.setSelectedItem("");
                            JOptionPane.showMessageDialog(root, "Persona no encontrada");
                        }
                    }

                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
        buscarPorNombreButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombre = NombretextField3.getText();
                try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
                    String SQL_QUERY_FIND_NAME = "SELECT * FROM personas WHERE nombre LIKE ?";
                    try (PreparedStatement pstmt = conn.prepareStatement(SQL_QUERY_FIND_NAME)) {
                        pstmt.setString(1, "%" + nombre + "%");
                        ResultSet resultSet = pstmt.executeQuery();

                        DefaultListModel<String> resultListModel = new DefaultListModel<>();
                        while (resultSet.next()) {
                            String cedula = resultSet.getString("cedula");
                            String fecha_naci = resultSet.getString("fecha");
                            String zodiaco = resultSet.getString("signo");
                            resultListModel.addElement("Cédula: " + cedula + ", Fecha de Nacimiento: " + fecha_naci + ", Signo: " + zodiaco);
                        }

                        if (resultListModel.isEmpty()) {
                            JOptionPane.showMessageDialog(root, "No se encontraron personas con ese nombre.");
                        } else {
                            JList<String> resultList = new JList<>(resultListModel);
                            JOptionPane.showMessageDialog(root, new JScrollPane(resultList), "Personas encontradas", JOptionPane.INFORMATION_MESSAGE);
                        }
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
        buscarPorSignoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String zodiaco = zodiacocomboBox1.getSelectedItem().toString();
                try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
                    String SQL_QUERY_FIND_NAME = "SELECT * FROM personas WHERE signo LIKE ?";
                    try (PreparedStatement pstmt = conn.prepareStatement(SQL_QUERY_FIND_NAME)) {
                        pstmt.setString(1, "%" + zodiaco + "%");
                        ResultSet resultSet = pstmt.executeQuery();

                        DefaultListModel<String> resultListModel = new DefaultListModel<>();
                        while (resultSet.next()) {
                            String nombre = resultSet.getString("nombre");
                            String cedula = resultSet.getString("cedula");
                            String fecha_naci = resultSet.getString("fecha");
                            String signo = resultSet.getString("signo");
                            resultListModel.addElement("Cédula: " + cedula + ", Fecha de Nacimiento: " + fecha_naci + "Nombre:" + nombre);
                        }

                        if (resultListModel.isEmpty()) {
                            JOptionPane.showMessageDialog(root, "No se encontraron personas con ese signo.");
                        } else {
                            JList<String> resultList = new JList<>(resultListModel);
                            JOptionPane.showMessageDialog(root, new JScrollPane(resultList), "Personas encontradas", JOptionPane.INFORMATION_MESSAGE);
                        }
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("root");
        frame.setContentPane(new root().root);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
