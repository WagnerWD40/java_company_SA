package sistema;

import javax.swing.*;
import sistema.telas.Login;
import sistema.telas.Inicio;
import sistema.telas.CargosInserir;
import sistema.telas.CargosConsultar;

public class Main {

    public static JPanel tela;
    public static JFrame frame;

    public static void main(String[] args) {

        criarComponentes();

    }

    private static void criarComponentes() {

        frame = new JFrame("Sistema");
        frame.setSize(730, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        Navegador.login();
    }
}
