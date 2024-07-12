import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class busqueda extends JFrame {
    private JPanel panel1;
    private JTextField bus;
    private JButton buscarButton;
    private JButton volverButton;

    public busqueda(){
        setTitle("Notas ESFOT");
        setSize(300,300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(panel1);

        buscarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    buscarINFO();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        volverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                moduloNotas m = new moduloNotas();
                m.setVisible(true);
            }
        });
    }

    public void buscarINFO()throws SQLException{
        int id = Integer.parseInt(bus.getText());
        Connection conecta = connection();
        String sql = "Select * from estudiantes where codigo_matricula = ?";
        PreparedStatement pstmt = conecta.prepareStatement(sql);
        pstmt.setInt(1, id);
        ResultSet rs = pstmt.executeQuery();
        if(rs.next()){
            String nombre = rs.getString("nombre_apellido");
            String direccion = rs.getString("direccion");
            String edad = rs.getString("edad");
            String telefono = rs.getString("telefono");
            String correo = rs.getString("correo");
            String nota1 = rs.getString("nota1");
            String nota2 = rs.getString("nota2");
            JOptionPane.showMessageDialog(null, "| Codigo: " +id +" | Nombre: " +nombre
            +" | Direccion: " +direccion+" | Edad: " +edad+" | Telefono: " +telefono+" | Correo: "+ correo+
                    " | Nota 1: " +nota1+ " |Nota 2: " + nota2);

        }
        rs.close();
        pstmt.close();
        conecta.close();
    }

    public Connection connection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/curso";
        String user = "root";
        String password = "123456";
        return DriverManager.getConnection(url,user,password);
    }
}
