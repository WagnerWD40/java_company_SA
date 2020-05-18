package sistema.telas;

import sistema.entidades.Cargo;
import sistema.BancoDeDados;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CargosEditar extends JPanel {

    Cargo cargoAtual;
    JLabel labelTitulo, labelCargo;
    JTextField campoCargo;
    JButton botaoGravar;

    public CargosEditar(Cargo cargo) {

        cargoAtual = cargo;

        criarComponentes();
        criarEventos();

    }

    private void criarComponentes() {
        setLayout(null);

        labelTitulo = new JLabel("Editar Cargo", JLabel.CENTER);
        labelTitulo.setFont(new Font(labelTitulo.getFont().getName(), Font.PLAIN, 20));
        labelCargo = new JLabel("Nome do cargo", JLabel.LEFT);
        campoCargo = new JTextField(cargoAtual.getNome());
        botaoGravar = new JButton("Salvar");

        labelTitulo.setBounds(20, 20, 660, 40);
        labelCargo.setBounds(150, 120, 400, 20);
        campoCargo.setBounds(150, 140, 400, 40);
        botaoGravar.setBounds(250, 380, 200, 40);

        add(labelTitulo);
        add(labelCargo);
        add(campoCargo);
        add(botaoGravar);

        setVisible(true);
    }

    private void criarEventos() {

        botaoGravar.addActionListener((new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargoAtual.setNome(campoCargo.getText());;

                sqlAtualizarCargo();
            }
        }));

    }

    private void sqlAtualizarCargo() {

        if (campoCargo.getText().length() <= 3) {
            JOptionPane.showMessageDialog(null, "Por favor, preencha o nome corretamente");
            return;
        }

        Connection conexao;
        Statement instrucaoSQL;
        ResultSet resultados;

        try {

            conexao = DriverManager.getConnection(
                    BancoDeDados.stringDeConexao,
                    BancoDeDados.usuario,
                    BancoDeDados.senha
            );

            instrucaoSQL = conexao.createStatement(
                    ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_READ_ONLY
            );

            instrucaoSQL.executeUpdate(
                    "UPDATE cargos SET nome='" + campoCargo.getText() + "' " +
                        "WHERE id=" + cargoAtual.getId() + ""
            );

            JOptionPane.showMessageDialog(null, "Cargo atualizado com sucesso!");

            conexao.close();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Ocorreu um erro ao editar o cargo.");
            Logger.getLogger(CargosInserir.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
