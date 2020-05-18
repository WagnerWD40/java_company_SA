package sistema.telas;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import sistema.BancoDeDados;
import sistema.Navegador;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.text.DecimalFormat;

public class RelatoriosCargos extends JPanel {

    JLabel labelTitulo, labelDescricao;

    public RelatoriosCargos() {

        criarComponentes();
        criarEventos();
        Navegador.habilitarMenu();

    }

    private void criarComponentes() {

        labelTitulo = new JLabel("Relatórios", JLabel.CENTER);
        labelTitulo.setFont(new Font(labelTitulo.getFont().getName(), Font.PLAIN, 20));
        labelDescricao = new JLabel("Esse gráfico representa a quantidade de funcionários por cargo", JLabel.CENTER);

        DefaultPieDataset dadosGrafico = this.criarDadosGrafico();

        JFreeChart someChart = ChartFactory.createPieChart3D("", dadosGrafico, false, true, false);

        PiePlot plot = (PiePlot) someChart.getPlot();
        plot.setLabelBackgroundPaint(Color.WHITE);
        plot.setBackgroundPaint(null);
        plot.setOutlinePaint(null);
        someChart.setBackgroundPaint(null);

        PieSectionLabelGenerator gen = new StandardPieSectionLabelGenerator(
                "{0}: {1} ({2})",
                new DecimalFormat("0"),
                new DecimalFormat("0%")
        );

        plot.setLabelGenerator(gen);

        ChartPanel chartPanel = new ChartPanel(someChart) {
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(660, 340);
            }
        };

        labelTitulo.setBounds(20, 20, 660, 40);
        labelDescricao.setBounds(20, 70, 660, 40);
        chartPanel.setBounds(20, 100, 660, 340);

        add(labelTitulo);
        add(labelDescricao);
        add(chartPanel);

        setVisible(true);

    }

    private void criarEventos() {

    }

    private DefaultPieDataset criarDadosGrafico() {

        DefaultPieDataset dados = new DefaultPieDataset();

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

            String query = "SELECT cargos.nome, count(*) AS quantidade FROM cargos, funcionarios" +
                    " WHERE cargos.id = funcionarios.cargo GROUP BY cargos.nome" +
                    " ORDER BY nome ASC";

            resultados = instrucaoSQL.executeQuery(query);

            while (resultados.next()) {
                dados.setValue(resultados.getString("nome"), resultados.getInt("quantidade"));
            }

            return dados;

        } catch (SQLException ex) {

            JOptionPane.showMessageDialog(
                    null, "Ocorreu um erro ao criar relatório.\n\n" + ex.getMessage()
            );
            Navegador.inicio();

        }

        return null;

    }

}
