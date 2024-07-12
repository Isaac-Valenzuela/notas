import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class administrador extends JFrame{
    private JPanel panel1;
    private JTextField textField1;
    private JTextField textField2;
    private JButton iniciarSesionButton;
    private JLabel Cal;
    private JLabel correo;
    private JPanel acceso;
    private JLabel codigo;


    public administrador(){
        setTitle("Notas ESFOT");
        setSize(300,300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(acceso);




        iniciarSesionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    verificar();

                }catch (SQLException ex){
                    JOptionPane.showMessageDialog(null, "Informacion incorrecta");
                    throw new RuntimeException(ex);
                }

            }
        });
    }
    public void verificar()throws SQLException{
        String pass = textField1.getText();
        String correo = textField2.getText();

        Connection conecta = connection();
        String sql="SELECT * FROM usuariosadministrador WHERE codigo = ? AND correo=?";
        Connection connection;
        PreparedStatement pstmt = conecta.prepareStatement(sql);
        pstmt.setInt(1, Integer.parseInt(pass));
        pstmt.setString(2, correo);

        ResultSet rs = pstmt.executeQuery();

        if (rs.next()){
            JOptionPane.showMessageDialog(null, "Informacion correcta");

            moduloNotas ventana = new moduloNotas();
            ventana.setVisible(true);
            dispose();

        }
        rs.close();
        pstmt.close();
        conecta.close();


    }
    public Connection connection() throws SQLException{
        String url = "jdbc:mysql://localhost:3306/curso";
        String user = "root";
        String password = "123456";
        return DriverManager.getConnection(url,user,password);
    }


}
