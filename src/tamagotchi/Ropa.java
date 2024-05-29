package tamagotchi;

import fonts.Fuentes;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import org.json.JSONObject;

public class Ropa extends javax.swing.JFrame {

    Fuentes tipoFuente;
    int xMouse,yMouse;
    Connection conn = null;
    private static final String DB_URL = "jdbc:sqlite:./sqlite-tools-win32-x86-3410200/sqlite-tools-win32-x86-3410200/tamagotchi.db";
    private static final String API_KEY = "1f2d0277994dc5b3b1ad32f99521a26a";
    private final ImageIcon equip = new ImageIcon("src/img/equip.png");
    private final ImageIcon equiped = new ImageIcon("src/img/equiped.png");
    private final ImageIcon buy = new ImageIcon("src/img/buy.png");
    
    public Ropa() {
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
        jLabel17.setFont(tipoFuente.fuente(tipoFuente.ARCADE, 1, 20));
        jLabel18.setFont(tipoFuente.fuente(tipoFuente.ARCADE, 1, 20));
        jLabel19.setFont(tipoFuente.fuente(tipoFuente.ARCADE, 1, 20));
        jLabel9.setFont(tipoFuente.fuente(tipoFuente.ARCADE, 1, 20));
        
        String[] coordinates = getCoordinates();
        double numeroDouble = obtenerTemperatura(coordinates[0], coordinates[1]);
        String numeroString = Double.toString(numeroDouble);

        jLabel9.setText("Temperatura: " + numeroString);
        
        cambiarImagenesDesdeBD();
    }
    
    
    public static String[] getCoordinates() {
        String[] values = null;

        try {
            URL url = new URL("http://ip-api.com/csv");
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            String response = reader.readLine();
            reader.close();

            values = response.split(",");
                if (values.length >= 10 && values[0].equals("success")) {
                    String latitude = values[7];
                    
                    String longitude = values[8];
                    return new String[]{latitude, longitude};
                }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
    
    public static double obtenerTemperatura(String latitud, String longitud) {
        double temperatura = 0.0;

        try {
            String urlStr = "http://api.openweathermap.org/data/2.5/weather?lat=" + latitud + "&lon=" + longitud + "&units=metric&appid=" + API_KEY;
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            JSONObject jsonResponse = new JSONObject(response.toString());
            temperatura = jsonResponse.getJSONObject("main").getDouble("temp");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return temperatura;
    }
    
    public void cambiarImagenesDesdeBD() {
    try {
        Connection conn = DriverManager.getConnection(DB_URL);

        String sql = "SELECT r.abrigo, r.sudadera, r.banador, t.equipado FROM Ropa r, Tamagotchi t WHERE r.mascota_id = t.id AND t.id = 1";
        PreparedStatement statement = conn.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            boolean abrigo = resultSet.getBoolean("abrigo");
            boolean sudadera = resultSet.getBoolean("sudadera");
            boolean banador = resultSet.getBoolean("banador");
            String equipado = resultSet.getString("equipado");

            if (equipado == null) {
                if (abrigo) {
                    jLabel4.setIcon(equip);
                }
                if (sudadera) {
                    jLabel8.setIcon(equip);
                }
                if (banador) {
                    jLabel6.setIcon(equip);
                }
            } else {
                if (abrigo) {
                    jLabel4.setIcon(equip);
                    if (equipado.equals("abrigo")) {
                        jLabel4.setIcon(equiped);
                    }
                }

                if (sudadera) {
                    jLabel8.setIcon(equip);
                    if (equipado.equals("sudadera")) {
                        jLabel8.setIcon(equiped);
                    }
                }

                if (banador) {
                    jLabel6.setIcon(equip);
                    if (equipado.equals("banador")) {
                        jLabel6.setIcon(equiped);
                    }
                }
            }
        }

        resultSet.close();
        statement.close();
        conn.close();

    } catch (SQLException e) {
        e.printStackTrace();
    }
}



    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel9 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(204, 255, 204));

        jPanel2.setBackground(new java.awt.Color(204, 255, 204));
        jPanel2.setLayout(new java.awt.GridBagLayout());

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/abrigo.png"))); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(9, 35, 9, 35);
        jPanel2.add(jLabel3, gridBagConstraints);

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/buy.png"))); // NOI18N
        jLabel4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel4MouseClicked(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.insets = new java.awt.Insets(4, 25, 4, 25);
        jPanel2.add(jLabel4, gridBagConstraints);

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/bañador.png"))); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(9, 35, 9, 35);
        jPanel2.add(jLabel5, gridBagConstraints);

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/buy.png"))); // NOI18N
        jLabel6.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel6MouseClicked(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.insets = new java.awt.Insets(4, 25, 4, 25);
        jPanel2.add(jLabel6, gridBagConstraints);

        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setText("Abrigo");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(2, 11, 2, 11);
        jPanel2.add(jLabel17, gridBagConstraints);

        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel18.setText("Sudadera");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(2, 11, 2, 11);
        jPanel2.add(jLabel18, gridBagConstraints);

        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel19.setText("Banador");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(2, 11, 2, 11);
        jPanel2.add(jLabel19, gridBagConstraints);

        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/sudadera.png"))); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(9, 35, 9, 35);
        jPanel2.add(jLabel7, gridBagConstraints);

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/buy.png"))); // NOI18N
        jLabel8.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel8MouseClicked(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.insets = new java.awt.Insets(4, 25, 4, 25);
        jPanel2.add(jLabel8, gridBagConstraints);

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Ropa");
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

        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

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
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 388, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseClicked
        if (jLabel4.getIcon() != null && jLabel4.getIcon().toString().endsWith("buy.png")) {
            try {
                Connection conn = DriverManager.getConnection(DB_URL);
                Statement stmt = conn.createStatement();

                String sql = "SELECT r.abrigo, t.dinero FROM Ropa r JOIN Tamagotchi t ON r.mascota_id = t.id WHERE r.mascota_id = 1";
                ResultSet rs = stmt.executeQuery(sql);

                if (rs.next()) {
                    boolean abrigo = rs.getBoolean("abrigo");
                    int dinero = rs.getInt("dinero");

                    if (!abrigo && dinero >= 100) {
                        sql = "UPDATE Ropa SET abrigo = 1 WHERE mascota_id = 1";
                        stmt.executeUpdate(sql);

                        sql = "UPDATE Tamagotchi SET dinero = dinero - 100 WHERE id = 1";
                        stmt.executeUpdate(sql);
                    }
                }

                rs.close();
                stmt.close();

                cambiarImagenesDesdeBD();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        if(jLabel4.getIcon()==equip)
        {
            try {
            PreparedStatement stmtUpdate = conn.prepareStatement("UPDATE Tamagotchi SET equipado = ? WHERE id = 1");
            stmtUpdate.setString(1, "abrigo");
            stmtUpdate.executeUpdate();
            stmtUpdate.close();
            jLabel4.setIcon(equiped);
            } catch (SQLException e) {
                System.err.println("Error al actualizar el equipo bañador: " + e.getMessage());
            }cambiarImagenesDesdeBD();
        }
    }//GEN-LAST:event_jLabel4MouseClicked

    private void jLabel6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel6MouseClicked
        if (jLabel6.getIcon() != null && jLabel6.getIcon().toString().endsWith("buy.png")) {
            try {
                Connection conn = DriverManager.getConnection(DB_URL);
                Statement stmt = conn.createStatement();

                String sql = "SELECT r.banador, t.dinero FROM Ropa r JOIN Tamagotchi t ON r.mascota_id = t.id WHERE r.mascota_id = 1";
                ResultSet rs = stmt.executeQuery(sql);

                if (rs.next()) {
                    boolean abrigo = rs.getBoolean("banador");
                    int dinero = rs.getInt("dinero");

                    if (!abrigo && dinero >= 100) {
                        sql = "UPDATE Ropa SET banador = 1 WHERE mascota_id = 1";
                        stmt.executeUpdate(sql);

                        sql = "UPDATE Tamagotchi SET dinero = dinero - 100 WHERE id = 1";
                        stmt.executeUpdate(sql);
                    }
                }

                rs.close();
                stmt.close();

                cambiarImagenesDesdeBD();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }


        if(jLabel6.getIcon()==equip)
        {
            try {
            PreparedStatement stmtUpdate = conn.prepareStatement("UPDATE Tamagotchi SET equipado = ? WHERE id = 1");
            stmtUpdate.setString(1, "banador");
            stmtUpdate.executeUpdate();
            stmtUpdate.close();
            jLabel6.setIcon(equiped);
            } catch (SQLException e) {
                System.err.println("Error al actualizar el equipo abrigo: " + e.getMessage());
            }cambiarImagenesDesdeBD();
        }
        
    }//GEN-LAST:event_jLabel6MouseClicked

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

    private void jLabel8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel8MouseClicked
        if (jLabel8.getIcon() != null && jLabel8.getIcon().toString().endsWith("buy.png")) {
            try {
                Connection conn = DriverManager.getConnection(DB_URL);
                Statement stmt = conn.createStatement();

                String sql = "SELECT r.sudadera, t.dinero FROM Ropa r JOIN Tamagotchi t ON r.mascota_id = t.id WHERE r.mascota_id = 1";
                ResultSet rs = stmt.executeQuery(sql);

                if (rs.next()) {
                    boolean abrigo = rs.getBoolean("sudadera");
                    int dinero = rs.getInt("dinero");

                    if (!abrigo && dinero >= 100) {
                        sql = "UPDATE Ropa SET sudadera = 1 WHERE mascota_id = 1";
                        stmt.executeUpdate(sql);

                        sql = "UPDATE Tamagotchi SET dinero = dinero - 100 WHERE id = 1";
                        stmt.executeUpdate(sql);
                    }
                }

                rs.close();
                stmt.close();

                cambiarImagenesDesdeBD();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }


        if(jLabel8.getIcon()==equip)
        {
            try {
            PreparedStatement stmtUpdate = conn.prepareStatement("UPDATE Tamagotchi SET equipado = ? WHERE id = 1");
            stmtUpdate.setString(1, "sudadera");
            stmtUpdate.executeUpdate();
            stmtUpdate.close();
            jLabel8.setIcon(equiped);
            } catch (SQLException e) {
                System.err.println("Error al actualizar el equipo abrigo: " + e.getMessage());
            }cambiarImagenesDesdeBD();
        }
    }//GEN-LAST:event_jLabel8MouseClicked

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
            java.util.logging.Logger.getLogger(Ropa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Ropa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Ropa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Ropa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Ropa().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JSeparator jSeparator1;
    // End of variables declaration//GEN-END:variables
}
