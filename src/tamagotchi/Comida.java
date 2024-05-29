/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package tamagotchi;

import fonts.Fuentes;
import java.awt.BorderLayout;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.border.TitledBorder;
import java.awt.Color;
import java.awt.Dimension;
import java.time.LocalTime;
import java.util.Random;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;

/**
 *
 * @author marco
 */
public class Comida extends javax.swing.JFrame {

    Fuentes tipoFuente;
    int xMouse,yMouse;
    Connection conn = null;
    private static final String DB_URL = "jdbc:sqlite:./sqlite-tools-win32-x86-3410200/sqlite-tools-win32-x86-3410200/tamagotchi.db";
    
    
    public Comida() throws SQLException {
        initComponents();
        
        try {
        Class.forName("org.sqlite.JDBC");
        conn = DriverManager.getConnection(DB_URL);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }   
        catch (SQLException ex) {
                Logger.getLogger(Tienda.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        tipoFuente = new Fuentes();
        jLabel1.setFont(tipoFuente.fuente(tipoFuente.ARCADE, 1, 45));
        
        TitledBorder titledBorder = (TitledBorder)jPanel2.getBorder();
        titledBorder.setTitleFont(tipoFuente.fuente(tipoFuente.ARCADE, 1, 25));
        TitledBorder titledBorder2 = (TitledBorder)jPanel3.getBorder();
        titledBorder2.setTitleFont(tipoFuente.fuente(tipoFuente.ARCADE, 1, 25));
        
        jLabel17.setFont(tipoFuente.fuente(tipoFuente.ARCADE, 1, 14));
        jLabel18.setFont(tipoFuente.fuente(tipoFuente.ARCADE, 1, 14));
        jLabel19.setFont(tipoFuente.fuente(tipoFuente.ARCADE, 1, 14));
        jLabel20.setFont(tipoFuente.fuente(tipoFuente.ARCADE, 1, 14));
        jLabel21.setFont(tipoFuente.fuente(tipoFuente.ARCADE, 1, 14));
        jLabel22.setFont(tipoFuente.fuente(tipoFuente.ARCADE, 1, 14));
        jLabel23.setFont(tipoFuente.fuente(tipoFuente.ARCADE, 1, 14));
        
        jLabel24.setFont(tipoFuente.fuente(tipoFuente.ARCADE, 1, 14));
        jLabel25.setFont(tipoFuente.fuente(tipoFuente.ARCADE, 1, 14));
        jLabel26.setFont(tipoFuente.fuente(tipoFuente.ARCADE, 1, 14));
        jLabel27.setFont(tipoFuente.fuente(tipoFuente.ARCADE, 1, 14));
        jLabel28.setFont(tipoFuente.fuente(tipoFuente.ARCADE, 1, 14));
        jLabel29.setFont(tipoFuente.fuente(tipoFuente.ARCADE, 1, 14));
        jLabel30.setFont(tipoFuente.fuente(tipoFuente.ARCADE, 1, 14));
        jLabel31.setFont(tipoFuente.fuente(tipoFuente.ARCADE, 1, 14));
        
        obtenerValores(conn);
        crearGraficoCircular(conn,jPanel4);
        
    }

    public void obtenerValores(Connection conn) throws SQLException {
    Statement stmt = conn.createStatement();
    ResultSet rs = stmt.executeQuery("SELECT agua, pancakes, tostadas, burguer, croissant, huevos, pizza FROM Comida WHERE mascota_id = 1");

    if (rs.next()) {
        int agua = rs.getInt("agua");
        int pancakes = rs.getInt("pancakes");
        int tostadas = rs.getInt("tostadas");
        int burguer = rs.getInt("burguer");
        int croissant = rs.getInt("croissant");
        int huevos = rs.getInt("huevos");
        int pizza = rs.getInt("pizza");
        
        jLabel24.setText("<html><div style='text-align: center;'>Existencias: " + agua + "</div></html>");
        jLabel25.setText("<html><div style='text-align: center;'>Existencias: " + pancakes + "</div></html>");
        jLabel26.setText("<html><div style='text-align: center;'>Existencias: " + tostadas + "</div></html>");
        jLabel27.setText("<html><div style='text-align: center;'>Existencias: " + burguer + "</div></html>");
        jLabel28.setText("<html><div style='text-align: center;'>Existencias: " + croissant + "</div></html>");
        jLabel29.setText("<html><div style='text-align: center;'>Existencias: " + huevos + "</div></html>");
        jLabel30.setText("<html><div style='text-align: center;'>Existencias: " + pizza + "</div></html>");
    }
    rs.close();
    stmt.close();
}


    public void crearGraficoCircular(Connection conn, JPanel jPanel4) throws SQLException {
        
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT hidratado, carbohidratos, proteinas FROM Nutricion WHERE mascota_id = 1");
        int hidratado = rs.getInt("hidratado");
        int carbohidratos = rs.getInt("carbohidratos");
        int proteinas = rs.getInt("proteinas");

        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("Hidratado", hidratado);
        dataset.setValue("Carbohidratos", carbohidratos);
        dataset.setValue("Proteínas", proteinas);

        JFreeChart chart = ChartFactory.createPieChart("", dataset, true, true, false);
        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setBackgroundPaint(new Color(204,255,204, 255));
        chart.getTitle().setBackgroundPaint(new Color(204, 255, 204));

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(320, 327));

        jPanel4.removeAll();
        jPanel4.setLayout(new BorderLayout());
        jPanel4.add(chartPanel, BorderLayout.CENTER);
        jPanel4.validate();
        jPanel4.setBackground(new Color(204, 255, 204));
        
        rs.close();
        stmt.close();
    }
    
    



    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel44 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel31 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(204, 255, 204));

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Comida");
        jLabel1.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jLabel1.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jLabel1MouseDragged(evt);
            }
        });
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel1MousePressed(evt);
            }
        });

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/letra-x.png"))); // NOI18N
        jLabel2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel2MouseClicked(evt);
            }
        });

        jSeparator1.setForeground(new java.awt.Color(0, 0, 0));

        jPanel2.setBackground(new java.awt.Color(204, 255, 204));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Comidas"));
        jPanel2.setLayout(new java.awt.GridBagLayout());

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/water.png"))); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(9, 28, 9, 28);
        jPanel2.add(jLabel3, gridBagConstraints);

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/drink.png"))); // NOI18N
        jLabel4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel4MouseClicked(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.insets = new java.awt.Insets(4, 18, 4, 18);
        jPanel2.add(jLabel4, gridBagConstraints);

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/pancake.png"))); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(9, 28, 9, 28);
        jPanel2.add(jLabel5, gridBagConstraints);

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/eat.png"))); // NOI18N
        jLabel6.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel6MouseClicked(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.insets = new java.awt.Insets(4, 18, 4, 18);
        jPanel2.add(jLabel6, gridBagConstraints);

        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/omelette.png"))); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(9, 28, 9, 28);
        jPanel2.add(jLabel7, gridBagConstraints);

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/eat.png"))); // NOI18N
        jLabel8.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel8MouseClicked(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.insets = new java.awt.Insets(4, 18, 4, 18);
        jPanel2.add(jLabel8, gridBagConstraints);

        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/comida.png"))); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.insets = new java.awt.Insets(9, 28, 9, 28);
        jPanel2.add(jLabel9, gridBagConstraints);

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/eat.png"))); // NOI18N
        jLabel10.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel10MouseClicked(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.insets = new java.awt.Insets(4, 18, 4, 18);
        jPanel2.add(jLabel10, gridBagConstraints);

        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/croissant.png"))); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.insets = new java.awt.Insets(9, 28, 9, 28);
        jPanel2.add(jLabel11, gridBagConstraints);

        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/eat.png"))); // NOI18N
        jLabel12.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel12MouseClicked(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.insets = new java.awt.Insets(4, 18, 4, 18);
        jPanel2.add(jLabel12, gridBagConstraints);

        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/eggs.png"))); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.insets = new java.awt.Insets(9, 28, 9, 28);
        jPanel2.add(jLabel13, gridBagConstraints);

        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/eat.png"))); // NOI18N
        jLabel14.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel14.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel14MouseClicked(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.insets = new java.awt.Insets(4, 18, 4, 18);
        jPanel2.add(jLabel14, gridBagConstraints);

        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/pizza.png"))); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.insets = new java.awt.Insets(9, 28, 9, 28);
        jPanel2.add(jLabel15, gridBagConstraints);

        jLabel16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/eat.png"))); // NOI18N
        jLabel16.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel16.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel16MouseClicked(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.insets = new java.awt.Insets(4, 18, 4, 18);
        jPanel2.add(jLabel16, gridBagConstraints);

        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setText("<html><div style='text-align: center;'>Agua</div></html>");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(2, 4, 2, 4);
        jPanel2.add(jLabel17, gridBagConstraints);

        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel18.setText("<html><div style='text-align: center;'>Pancakes</div></html>");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(2, 4, 2, 4);
        jPanel2.add(jLabel18, gridBagConstraints);

        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel19.setText("<html><div style='text-align: center;'>Tostada</div></html>");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(2, 4, 2, 4);
        jPanel2.add(jLabel19, gridBagConstraints);

        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel20.setText("<html><div style='text-align: center;'>Burguer</div></html>");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.insets = new java.awt.Insets(2, 4, 2, 4);
        jPanel2.add(jLabel20, gridBagConstraints);

        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("<html><div style='text-align: center;'>Croissant</div></html>");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.insets = new java.awt.Insets(2, 4, 2, 4);
        jPanel2.add(jLabel21, gridBagConstraints);

        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel22.setText("<html><div style='text-align: center;'>Huevos</div></html>");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.insets = new java.awt.Insets(2, 4, 2, 4);
        jPanel2.add(jLabel22, gridBagConstraints);

        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel23.setText("<html><div style='text-align: center;'>Pizza</div></html>");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.insets = new java.awt.Insets(2, 4, 2, 4);
        jPanel2.add(jLabel23, gridBagConstraints);

        jLabel24.setText("jLabel24");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(2, 4, 2, 4);
        jPanel2.add(jLabel24, gridBagConstraints);

        jLabel25.setText("jLabel25");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(2, 4, 2, 4);
        jPanel2.add(jLabel25, gridBagConstraints);

        jLabel26.setText("jLabel26");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(2, 4, 2, 4);
        jPanel2.add(jLabel26, gridBagConstraints);

        jLabel27.setText("jLabel27");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.insets = new java.awt.Insets(2, 4, 2, 4);
        jPanel2.add(jLabel27, gridBagConstraints);

        jLabel28.setText("jLabel28");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.insets = new java.awt.Insets(2, 4, 2, 4);
        jPanel2.add(jLabel28, gridBagConstraints);

        jLabel29.setText("jLabel29");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.insets = new java.awt.Insets(2, 4, 2, 4);
        jPanel2.add(jLabel29, gridBagConstraints);

        jLabel30.setText("jLabel30");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.insets = new java.awt.Insets(2, 4, 2, 4);
        jPanel2.add(jLabel30, gridBagConstraints);

        jPanel3.setBackground(new java.awt.Color(204, 255, 204));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Balance de dieta"));

        jLabel44.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jPanel4.setBackground(new java.awt.Color(204, 255, 204));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 456, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 327, Short.MAX_VALUE)
        );

        jLabel31.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel31.setText("<html><div style='text-align: center;'>Consejo nutricional: </br> Solo puedes conseguir hidratacion mediante agua, </br>carbohidratos mediante pancakes, tostadas, hamburguesas, croissants y pizza, </br> y proteinas mediante huevos, hamburguesas, tostadas y pizza</div></html>");
        jLabel31.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel44)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel44)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabel31)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 367, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 56, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 486, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel1MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseDragged
        int x = evt.getXOnScreen();
        int y = evt.getYOnScreen();
        this.setLocation(x - xMouse, y - yMouse);
    }//GEN-LAST:event_jLabel1MouseDragged

    private void jLabel1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MousePressed
        xMouse = evt.getX();
        yMouse = evt.getY();
    }//GEN-LAST:event_jLabel1MousePressed

    private void jLabel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseClicked
        this.dispose();
    }//GEN-LAST:event_jLabel2MouseClicked

    private void jLabel4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseClicked
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT agua FROM Comida WHERE mascota_id = 1");
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int agua = rs.getInt("agua");
                if (agua > 0) {
                    String sql = "UPDATE Comida SET agua = agua - 1 WHERE mascota_id = 1";
                    PreparedStatement updateStmt = conn.prepareStatement(sql);
                    updateStmt.executeUpdate();
                    updateStmt.close();

                    String tamagotchiSql = "UPDATE Nutricion SET hidratado = hidratado + 15 WHERE mascota_id = 1";
                    PreparedStatement tamagotchiUpdateStmt = conn.prepareStatement(tamagotchiSql);
                    tamagotchiUpdateStmt.executeUpdate();
                    tamagotchiUpdateStmt.close();
                }
            }

            rs.close();
            stmt.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        try {
            obtenerValores(conn);
            crearGraficoCircular(conn, jPanel4);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_jLabel4MouseClicked

    private void jLabel6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel6MouseClicked
        try {
            LocalTime horaActual = LocalTime.now();
            Statement stmt = conn.createStatement();
            if (horaActual.isAfter(LocalTime.of(6, 0)) && horaActual.isBefore(LocalTime.of(12, 0))) {
                ResultSet rs = stmt.executeQuery("SELECT pancakes FROM Comida WHERE mascota_id = 1");
                if (rs.next()) {
                    int pancakes = rs.getInt("pancakes");
                    if (pancakes > 0) {
                        String sql = "UPDATE Comida SET pancakes = pancakes - 1 WHERE mascota_id = 1";
                        stmt.executeUpdate(sql);

                        String nutricionSql = "UPDATE Nutricion SET carbohidratos = carbohidratos + 20 WHERE mascota_id = 1";
                        stmt.executeUpdate(nutricionSql);
                        String comidaSql = "UPDATE Tamagotchi SET comida = comida + 1 WHERE id = 1";
                        stmt.executeUpdate(comidaSql);
                        
                    }
                    rs.close();
                }
                stmt.close();
                System.out.println("Pancake mañanera");
            } else {
                ResultSet rs = stmt.executeQuery("SELECT pancakes FROM Comida WHERE mascota_id = 1");
                if (rs.next()) {
                    if (rs.getInt("pancakes") > 0) {
                        String sql = "UPDATE Comida SET pancakes = pancakes - 1 WHERE mascota_id = 1";
                        stmt.executeUpdate(sql);

                        String nutricionSql = "UPDATE Nutricion SET carbohidratos = carbohidratos + 20 WHERE mascota_id = 1";
                        stmt.executeUpdate(nutricionSql);

                        String tamagotchiSql = "UPDATE Tamagotchi SET comida = comida - 5, salud = salud - 1 WHERE id = 1";
                        stmt.executeUpdate(tamagotchiSql);

                        System.out.println("Pancake tardera");
                    }
                    rs.close();
                }
                stmt.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        try {
            obtenerValores(conn);
            crearGraficoCircular(conn, jPanel4);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        Random random = new Random();
        if (random.nextDouble() < 0.05) {
            try {
                Statement stmt = conn.createStatement();
                String sql = "UPDATE Tamagotchi SET manchado = 1 WHERE id = 1";
                stmt.executeUpdate(sql);
                stmt.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

    }//GEN-LAST:event_jLabel6MouseClicked

    private void jLabel8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel8MouseClicked
        try {
            LocalTime horaActual = LocalTime.now();
            Statement stmt = conn.createStatement();
            if (horaActual.isAfter(LocalTime.of(6, 0)) && horaActual.isBefore(LocalTime.of(12, 0))) {
                ResultSet rs = stmt.executeQuery("SELECT tostadas FROM Comida WHERE mascota_id = 1");
                if (rs.next()) {
                    int tostadas = rs.getInt("tostadas");
                    if (tostadas > 0) {
                        String sql = "UPDATE Comida SET tostadas = tostadas - 1 WHERE mascota_id = 1";
                        stmt.executeUpdate(sql);

                        String sqlNutricion = "UPDATE Nutricion SET carbohidratos = carbohidratos + 15, proteinas = proteinas + 5 WHERE mascota_id = 1";
                        stmt.executeUpdate(sqlNutricion);
                    }
                    rs.close();
                }
                stmt.close();
                System.out.println("Tostada mañanera");
            } else {
                ResultSet rs = stmt.executeQuery("SELECT tostadas FROM Comida WHERE mascota_id = 1");
                if (rs.next()) {
                    if (rs.getInt("tostadas") > 0) {
                        String sql = "UPDATE Comida SET tostadas = tostadas - 1 WHERE mascota_id = 1";
                        stmt.executeUpdate(sql);

                        String sqlNutricion = "UPDATE Nutricion SET carbohidratos = carbohidratos + 15, proteinas = proteinas + 5 WHERE mascota_id = 1";
                        stmt.executeUpdate(sqlNutricion);

                        String sqlTamagotchi = "UPDATE Tamagotchi SET comida = comida - 5, salud = salud - 1 WHERE id = 1";
                        stmt.executeUpdate(sqlTamagotchi);

                        System.out.println("Tostada tardera");
                    }
                    rs.close();
                }
                stmt.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        try {
            obtenerValores(conn);
            crearGraficoCircular(conn, jPanel4);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        Random random = new Random();
        if (random.nextDouble() < 0.05) {
            try {
                Statement stmt = conn.createStatement();
                String sql = "UPDATE Tamagotchi SET manchado = 1 WHERE id = 1";
                stmt.executeUpdate(sql);
                stmt.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

    }//GEN-LAST:event_jLabel8MouseClicked

    private void jLabel10MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel10MouseClicked
        Statement stmt = null;
        try {
            LocalTime horaActual = LocalTime.now();
            stmt = conn.createStatement();
            if (horaActual.isAfter(LocalTime.of(12, 0)) && horaActual.isBefore(LocalTime.of(16, 0))) {

                ResultSet rs = stmt.executeQuery("SELECT burguer FROM Comida WHERE mascota_id = 1");
                if (rs.next()) {
                    int burguer = rs.getInt("burguer");
                    if (burguer > 0) {
                        String sql = "UPDATE Comida SET burguer = burguer - 1 WHERE mascota_id = 1";
                        stmt.executeUpdate(sql);

                        String nutricionSql = "UPDATE Nutricion SET carbohidratos = carbohidratos + 5, proteinas = proteinas + 25 WHERE mascota_id = 1";
                        stmt.executeUpdate(nutricionSql);
                    }
                    stmt.executeUpdate("UPDATE Tamagotchi SET comida = comida + 1 WHERE id = 1 AND comida < 100");
                    rs.close();
                }
                stmt.close();
                System.out.println("Burguer en la mañana");
            } else {

                ResultSet rs = stmt.executeQuery("SELECT burguer FROM Comida WHERE mascota_id = 1");
                if (rs.next()) {
                    if (rs.getInt("burguer") > 0) {
                        String sql = "UPDATE Comida SET burguer = burguer - 1 WHERE mascota_id = 1";
                        stmt.executeUpdate(sql);

                        String nutricionSql = "UPDATE Nutricion SET carbohidratos = carbohidratos + 5, proteinas = proteinas + 25 WHERE mascota_id = 1";
                        stmt.executeUpdate(nutricionSql);

                        stmt.executeUpdate("UPDATE Tamagotchi SET comida = comida - 5, salud = salud - 1 WHERE id = 1");
                        System.out.println("Burguer en la tarde");
                    }
                    rs.close();
                }
                stmt.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        try {
            obtenerValores(conn);
            crearGraficoCircular(conn, jPanel4);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        Random random = new Random();
        if (random.nextDouble() < 0.05) {
            try {
                stmt = conn.createStatement();
                String sql = "UPDATE Tamagotchi SET manchado = 1 WHERE id = 1";
                stmt.executeUpdate(sql);
                stmt.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

    }//GEN-LAST:event_jLabel10MouseClicked

    private void jLabel12MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel12MouseClicked
        try {
            LocalTime horaActual = LocalTime.now();
            Statement stmt = conn.createStatement();
            if (horaActual.isAfter(LocalTime.of(16, 0)) && horaActual.isBefore(LocalTime.of(20, 0))) {
                ResultSet rs = stmt.executeQuery("SELECT croissant FROM Comida WHERE mascota_id = 1");
                if (rs.next()) {
                    int croissant = rs.getInt("croissant");
                    if (croissant > 0) {
                        String sql = "UPDATE Comida SET croissant = croissant - 1 WHERE mascota_id = 1";
                        stmt.executeUpdate(sql);

                        String sqlNutricion = "UPDATE Nutricion SET carbohidratos = carbohidratos + 10 WHERE mascota_id = 1";
                        stmt.executeUpdate(sqlNutricion);
                    }
                    stmt.executeUpdate("UPDATE Tamagotchi SET comida = comida + 1 WHERE id = 1 AND comida < 100");
                    rs.close();
                }
                stmt.close();
                System.out.println("Croissant mañanera");
            } else {
                ResultSet rs = stmt.executeQuery("SELECT croissant FROM Comida WHERE mascota_id = 1");
                if (rs.next()) {
                    if (rs.getInt("croissant") > 0) {
                        String sql = "UPDATE Comida SET croissant = croissant - 1 WHERE mascota_id = 1";
                        stmt.executeUpdate(sql);

                        String sqlNutricion = "UPDATE Nutricion SET carbohidratos = carbohidratos + 10 WHERE mascota_id = 1";
                        stmt.executeUpdate(sqlNutricion);

                        String sqlTamagotchi = "UPDATE Tamagotchi SET comida = comida - 5, salud = salud - 1 WHERE id = 1";
                        stmt.executeUpdate(sqlTamagotchi);

                        System.out.println("Croissant tardera");
                    }
                    rs.close();
                }
                stmt.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        try {
            obtenerValores(conn);
            crearGraficoCircular(conn, jPanel4);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        Random random = new Random();
        if (random.nextDouble() < 0.05) {
            try {
                Statement stmt = conn.createStatement();
                String sql = "UPDATE Tamagotchi SET manchado = 1 WHERE id = 1";
                stmt.executeUpdate(sql);
                stmt.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

    }//GEN-LAST:event_jLabel12MouseClicked

    private void jLabel14MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel14MouseClicked
        Statement stmt = null;
        try {
            LocalTime horaActual = LocalTime.now();
            stmt = conn.createStatement();
            if (horaActual.isAfter(LocalTime.of(16, 0)) && horaActual.isBefore(LocalTime.of(20, 0))) {

                ResultSet rs = stmt.executeQuery("SELECT huevos FROM Comida WHERE mascota_id = 1");
                if (rs.next()) {
                    int huevos = rs.getInt("huevos");
                    if (huevos > 0) {
                        String sql = "UPDATE Comida SET huevos = huevos - 1 WHERE mascota_id = 1";
                        stmt.executeUpdate(sql);

                        String nutricionSql = "UPDATE Nutricion SET proteinas = proteinas + 35 WHERE mascota_id = 1";
                        stmt.executeUpdate(nutricionSql);
                    }
                    stmt.executeUpdate("UPDATE Tamagotchi SET comida = comida + 1 WHERE id = 1 AND comida < 100");
                    rs.close();
                }
                stmt.close();
                System.out.println("Huevos en la mañana");
            } else {

                ResultSet rs = stmt.executeQuery("SELECT huevos FROM Comida WHERE mascota_id = 1");
                if (rs.next()) {
                    if (rs.getInt("huevos") > 0) {
                        String sql = "UPDATE Comida SET huevos = huevos - 1 WHERE mascota_id = 1";
                        stmt.executeUpdate(sql);

                        String nutricionSql = "UPDATE Nutricion SET proteinas = proteinas + 35 WHERE mascota_id = 1";
                        stmt.executeUpdate(nutricionSql);

                        stmt.executeUpdate("UPDATE Tamagotchi SET comida = comida - 5, salud = salud - 1 WHERE id = 1");
                        System.out.println("Huevos en la tarde");
                    }
                    rs.close();
                }
                stmt.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        try {
            obtenerValores(conn);
            crearGraficoCircular(conn, jPanel4);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        Random random = new Random();
        if (random.nextDouble() < 0.05) {
            try {
                stmt = conn.createStatement();
                String sql = "UPDATE Tamagotchi SET manchado = 1 WHERE id = 1";
                stmt.executeUpdate(sql);
                stmt.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

    }//GEN-LAST:event_jLabel14MouseClicked

    private void jLabel16MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel16MouseClicked
        Statement stmt = null;
        try {
            LocalTime horaActual = LocalTime.now();
            stmt = conn.createStatement();
            if (horaActual.isAfter(LocalTime.of(20, 0)) && horaActual.isBefore(LocalTime.of(4, 0))) {

                ResultSet rs = stmt.executeQuery("SELECT pizza FROM Comida WHERE mascota_id = 1");
                if (rs.next()) {
                    int pizza = rs.getInt("pizza");
                    if (pizza > 0) {
                        String sql = "UPDATE Comida SET pizza = pizza - 1 WHERE mascota_id = 1";
                        stmt.executeUpdate(sql);

                        String nutricionSql = "UPDATE Nutricion SET proteinas = proteinas + 5, carbohidratos = carbohidratos + 10 WHERE mascota_id = 1";
                        stmt.executeUpdate(nutricionSql);
                    }
                    stmt.executeUpdate("UPDATE Tamagotchi SET comida = comida + 1 WHERE id = 1 AND comida < 100");
                    rs.close();
                }
                stmt.close();
                System.out.println("Pizza en la mañana");
            } else {

                ResultSet rs = stmt.executeQuery("SELECT pizza FROM Comida WHERE mascota_id = 1");
                if (rs.next()) {
                    if (rs.getInt("pizza") > 0) {
                        String sql = "UPDATE Comida SET pizza = pizza - 1 WHERE mascota_id = 1";
                        stmt.executeUpdate(sql);

                        String nutricionSql = "UPDATE Nutricion SET proteinas = proteinas + 5, carbohidratos = carbohidratos + 10 WHERE mascota_id = 1";
                        stmt.executeUpdate(nutricionSql);

                        stmt.executeUpdate("UPDATE Tamagotchi SET comida = comida - 5, salud = salud - 1 WHERE id = 1");
                        System.out.println("Pizza en la tarde");
                    }
                    rs.close();
                }
                stmt.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        try {
            obtenerValores(conn);
            crearGraficoCircular(conn, jPanel4);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        Random random = new Random();
        if (random.nextDouble() < 0.05) {
            try {
                stmt = conn.createStatement();
                String sql = "UPDATE Tamagotchi SET manchado = 1 WHERE id = 1";
                stmt.executeUpdate(sql);
                stmt.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

    }//GEN-LAST:event_jLabel16MouseClicked

    
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Comida.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Comida.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Comida.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Comida.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new Comida().setVisible(true);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JSeparator jSeparator1;
    // End of variables declaration//GEN-END:variables
}
