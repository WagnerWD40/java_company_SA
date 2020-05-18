package sistema.telas;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import sistema.BancoDeDados;
import sistema.Navegador;
import sistema.entidades.Cargo;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CargosConsultar extends JPanel {

    Cargo cargoAtual;
    JLabel labelTitulo, labelCargo;
    JTextField campoCargo;
    JButton botaoPesquisar, botaoEditar, botaoExcluir;
    DefaultListModel<Cargo> listasCargoModelo = new DefaultListModel<Cargo>();
    JList<Cargo> listaCargos;

    public CargosConsultar() {
        criarComponentes();
        criarEventos();
        sqlPesquisarCargos("");
    }

    private void criarComponentes() {
        setLayout(null);

        labelTitulo = new JLabel("Consulta de Cargos", JLabel.CENTER);
        labelTitulo.setFont(new Font(labelTitulo.getFont().getName(), Font.PLAIN, 20));
        labelCargo = new JLabel(("Nome do cargo"), JLabel.LEFT);
        campoCargo = new JTextField();
        botaoPesquisar = new JButton(("Pesquisar Cargo"));
        botaoEditar = new JButton("Editar Cargo");
        botaoEditar.setEnabled(false);
        botaoExcluir = new JButton("Excluir Cargo");
        botaoExcluir.setEnabled(false);
        listaCargos = new JList();
        listaCargos.setModel(listasCargoModelo);
        listaCargos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        labelTitulo.setBounds(20, 20, 660, 40);
        labelCargo.setBounds(150, 120, 400, 20);
        campoCargo.setBounds(150, 140, 400, 40);
        botaoPesquisar.setBounds(560, 140, 130, 40);
        listaCargos.setBounds(150, 200, 400, 240);
        botaoEditar.setBounds(560, 360, 130, 40);
        botaoExcluir.setBounds(560, 400, 130, 40);

        add(labelTitulo);
        add(labelCargo);
        add(campoCargo);
        add(listaCargos);
        add(botaoPesquisar);
        add(botaoEditar);
        add(botaoExcluir);

        setVisible(true);
    }

    private void criarEventos() {

        botaoPesquisar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sqlPesquisarCargos(campoCargo.getText());
            }
        });

        botaoEditar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Navegador.cargosEditar(cargoAtual);
            }
        });

        botaoExcluir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sqlDeletarCargo();
            }
        });

        listaCargos.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                cargoAtual = listaCargos.getSelectedValue();

                if(cargoAtual == null) {
                    botaoEditar.setEnabled(false);
                    botaoExcluir.setEnabled(false);
                } else {
                    botaoEditar.setEnabled(true);
                    botaoExcluir.setEnabled(true);
                }
            }
        });

    }

    private void sqlPesquisarCargos(String nome) {

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

            resultados = instrucaoSQL.executeQuery(
                    "SELECT * FROM cargos WHERE nome LIKE '%" + nome + "%'"
            );

            listasCargoModelo.clear();

            while (resultados.next()) {
                Cargo cargo = new Cargo();
                cargo.setId((resultados.getInt("id")));;
                cargo.setNome((resultados.getString("Nome")));

                listasCargoModelo.addElement(cargo);
            }

        } catch (SQLException ex) {

            JOptionPane.showMessageDialog(null, "Ocorreu um erro ao consultar os Cargos");
            Logger.getLogger(CargosInserir.class.getName()).log(Level.SEVERE, null, ex);

        }

    }

    private void sqlDeletarCargo() {

        int confirmacao = JOptionPane.showConfirmDialog(
                null,
                "Deseja realmente excluir o Cargo" + cargoAtual.getNome() + "?",
                "Excluir",
                JOptionPane.YES_NO_OPTION
        );

        if (confirmacao == JOptionPane.YES_OPTION) {

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
                    "DELETE cargos WHERE id=" + cargoAtual.getId() + ""
                );

                JOptionPane.showMessageDialog(null, "Cargo deletado com sucesso!");


            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Ocorreu um erro ao excluir o Cargo.");
                Logger.getLogger(CargosInserir.class.getSimpleName()).log(Level.SEVERE, null, ex);
            }
        }

    }
}
