import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class moduloNotas extends JFrame {

    private JPanel panel1;
    private JTextField nombre;
    private JTextField direccion;
    private JTextField telefono;
    private JTextField n1;
    private JTextField n2;
    private JButton ingresarButton;
    private JButton verButton;
    private JButton IRButton;
    private JTextField edad;
    private JTextField correo;
    private JTextArea ver;
    private JButton salirButton;

    public moduloNotas(){
        setTitle("Notas");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500,500);
        ver.setEditable(false);
        setLocationRelativeTo(null);
        setContentPane(panel1);

        ingresarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    IngresarDatos();
                } catch (SQLException ex){
                    throw new RuntimeException(ex);
                }
            }
        });
        verButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    vertodo();
                } catch (SQLException ex){
                    throw new RuntimeException(ex);
                }
            }
        });
        IRButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                busqueda ventana = new busqueda();
                ventana.setVisible(true);
                dispose();

            }
        });

        salirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                administrador a = new administrador();
                a.setVisible(true);
                dispose();
            }
        });


    }
    public void IngresarDatos()throws SQLException{
        String nombr = nombre.getText();
        String direccio = direccion.getText();
        String eda = edad.getText();
        String telefon = telefono.getText();
        String corre = correo.getText();
        String nota1 = n1.getText();
        String nota2 = n2.getText();

        Connection conecta = connection();
        String sql = "INSERT INTO estudiantes(nombre_apellido, direccion, edad, telefono, correo, nota1, nota2)" +
                "VALUES(?,?,?,?,?,?,?)";
        PreparedStatement pstmt = conecta.prepareStatement(sql);
        pstmt.setString(1, nombr);
        pstmt.setString(2, direccio);
        pstmt.setInt(3, Integer.parseInt(eda));
        pstmt.setInt(4, Integer.parseInt(telefon));
        pstmt.setString(5, corre);
        pstmt.setFloat(6, Float.parseFloat(nota1));
        pstmt.setFloat(7, Float.parseFloat(nota2));

        int rowAffected= pstmt.executeUpdate();
        if(rowAffected >0){
            JOptionPane.showMessageDialog(null, "Registro insertado correctamente");
        }
        pstmt.close();
        conecta.close();
    }

    public void vertodo()throws SQLException{
        Connection conecta = connection();
        String query = "SELECT * FROM estudiantes";
        Statement statement = conecta.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        StringBuilder resultText = new StringBuilder();
        while(resultSet.next()){
            resultText.append(resultSet.getString("codigo_matricula")).append(" ");
            resultText.append(resultSet.getString("nombre_apellido")).append(" ");
            resultText.append(resultSet.getString("direccion")).append(" ");
            resultText.append(resultSet.getString("edad")).append(" ");
            resultText.append(resultSet.getString("telefono")).append(" ");
            resultText.append(resultSet.getString("correo")).append(" ");
            resultText.append(resultSet.getString("nota1")).append(" ");
            resultText.append(resultSet.getString("nota2")).append("\n");
        }
        ver.setText(resultText.toString());
        conecta.close();



    }

    public Connection connection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/curso";
        String user = "root";
        String password = "123456";
        return DriverManager.getConnection(url,user,password);
    }

}
