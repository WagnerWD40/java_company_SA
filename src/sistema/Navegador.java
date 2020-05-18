package sistema;

import sistema.entidades.Cargo;
import sistema.entidades.Funcionario;
import sistema.telas.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Navegador {

    private static boolean menuConstruido;
    private static boolean menuHabilitado;
    private static JMenuBar menuBar;
    private static JMenu menuArquivo, menuFuncionarios, menuCargos, menuRelatorios;
    private static JMenuItem miSair, miFuncionariosConsultar, miFuncionariosCadastrar;
    private static JMenuItem miCargosConsultar, miCargosCadastrar, miRelatoriosCargos, miRelatoriosSalarios;

    public static void login() {
        Main.tela = new Login();
        Main.frame.setTitle("Funcionários Company SA");
        Navegador.atualizarTela();
    }

    public static void inicio() {
        Main.tela = new Inicio();
        Main.frame.setTitle("Funcionários Company SA");
        Navegador.atualizarTela();
    }

    public static void cargosCadastrar() {
        Main.tela = new CargosInserir();
        Main.frame.setTitle("Funcionários Company SA - Cadastrar cargos");
        Navegador.atualizarTela();
    }

    public static void cargosConsultar() {
        Main.tela = new CargosConsultar();
        Main.frame.setTitle("Funcionários Company SA - Consultar cargos");
        Navegador.atualizarTela();
    }

    public static void funcionariosCadastrar() {
        Main.tela = new FuncionariosInserir();
        Main.frame.setTitle("Funcionários Company SA - Cadastrar funcionários");
        Navegador.atualizarTela();
    }

    public static void funcionariosConsultar() {
        Main.tela = new FuncionariosConsultar();
        Main.frame.setTitle("Funcionários Company SA - Cadastrar funcionários");
        Navegador.atualizarTela();
    }

    public static void funcionariosEditar(Funcionario funcionario) {
        Main.tela = new FuncionariosEditar(funcionario);
        Main.frame.setTitle("Funcionários Company SA - Cadastrar funcionários");
        Navegador.atualizarTela();
    }

    public static void cargosEditar(Cargo cargo) {
        Main.tela = new CargosEditar(cargo);
        Main.frame.setTitle("Funcionários Company SA");
        Navegador.atualizarTela();
    }

    public static void relatoriosCargos() {
        Main.tela = new RelatoriosCargos();
        Main.frame.setTitle("Funcionários Company SA");
        Navegador.atualizarTela();
    }

    public static void relatoriosSalarios() {
        Main.tela = new RelatoriosSalarios();
        Main.frame.setTitle("Funcionários Company SA");
        Navegador.atualizarTela();
    }

    private static void atualizarTela() {
        Main.frame.getContentPane().removeAll();
        Main.tela.setVisible(true);
        Main.frame.add(Main.tela);

        Main.frame.setVisible(true);
    }

    private static void construirMenu() {
        if (!menuConstruido) {
            menuConstruido = true;

            menuBar = new JMenuBar();

            // menu Arquivo
            menuArquivo = new JMenu("Arquivo");
            menuBar.add(menuArquivo);
            miSair = new JMenuItem("Sair");
            menuArquivo.add(miSair);

            // menu Funcionários
            menuFuncionarios = new JMenu("Funcionários");
            menuBar.add(menuFuncionarios);
            miFuncionariosCadastrar = new JMenuItem("Cadastrar");
            menuFuncionarios.add(miFuncionariosCadastrar);
            miFuncionariosConsultar = new JMenuItem("Consultar");
            menuFuncionarios.add(miFuncionariosConsultar);

            // menu Cargos
            menuCargos = new JMenu("Cargos");
            menuBar.add(menuCargos);
            miCargosCadastrar = new JMenuItem("Cadastrar");
            menuCargos.add(miCargosCadastrar);
            miCargosConsultar = new JMenuItem("Consultar");
            menuCargos.add(miCargosConsultar);

            // menu Relatórios
            menuRelatorios = new JMenu("Relatórios");
            menuBar.add(menuRelatorios);
            miRelatoriosCargos = new JMenuItem("Funcionários por cargos");
            menuRelatorios.add(miRelatoriosCargos);
            miRelatoriosSalarios = new JMenuItem("Salários dos funcionários");
            menuRelatorios.add(miRelatoriosSalarios);

            criarEventosMenu();
        }
    }

    public static void habilitarMenu() {
        if (!menuConstruido) {
            construirMenu();
        }

        if (!menuHabilitado) {
            menuHabilitado = true;
            Main.frame.setJMenuBar(menuBar);
        }
    }

    public static void desabilitarMenu() {
        if (menuHabilitado) {
            menuHabilitado = false;
            Main.frame.setJMenuBar(null);
        }
    }

    private static void criarEventosMenu() {

        miSair.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        // Funcionário
        miFuncionariosCadastrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                funcionariosCadastrar();
            }
        });

        miFuncionariosConsultar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                funcionariosConsultar();
            }
        });

        // Cargos
        miCargosCadastrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargosCadastrar();
            }
        });

        miCargosConsultar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargosConsultar();
            }
        });

        // Relatórios
        miRelatoriosCargos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                relatoriosCargos();
            }
        });

        miRelatoriosSalarios.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                relatoriosSalarios();
            }
        });
    }

}
