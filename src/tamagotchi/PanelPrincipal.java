package tamagotchi;

import fonts.Fuentes;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.*;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.Timer;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.temporal.ChronoUnit;
import java.util.Random;
import main.Juego;
import org.json.JSONObject;

public class PanelPrincipal extends javax.swing.JFrame {

    public Clip clip = null;
    int xMouse,yMouse;
    Connection conn = null;
    Fuentes tipoFuente;
    private static final String DB_URL = "jdbc:sqlite:./sqlite-tools-win32-x86-3410200/sqlite-tools-win32-x86-3410200/tamagotchi.db";
    boolean descansando;
    int contador=0;
    private static final String API_KEY = "1f2d0277994dc5b3b1ad32f99521a26a";

    
    public PanelPrincipal() throws SQLException {
    initComponents();
    try{
    setTitle("Tamagotchi");
    setResizable(false);
    tipoFuente = new Fuentes();
    jLabel1.setFont(tipoFuente.fuente(tipoFuente.ARCADE, 1, 45));
    jLabel2.setFont(tipoFuente.fuente(tipoFuente.ARCADE, 1, 45));
    jLabel3.setFont(tipoFuente.fuente(tipoFuente.ARCADE, 1, 45));
    jLabel11.setFont(tipoFuente.fuente(tipoFuente.ARCADE, 1, 40));
    
    try {
        Class.forName("org.sqlite.JDBC");
        conn = DriverManager.getConnection("jdbc:sqlite:tamagotchi.db");
    } catch (Exception e) {
        System.err.println(e.getClass().getName() + ": " + e.getMessage());
        System.exit(0);
    }
    
    File dbFile = new File("./sqlite-tools-win32-x86-3410200/sqlite-tools-win32-x86-3410200/tamagotchi.db");

    if (!dbFile.exists()) {
        
        createDatabase();
        
    } 

        try (Connection conn = DriverManager.getConnection(DB_URL)) {
        LocalDate fechaActual = LocalDate.now();

        PreparedStatement stmtVerif = conn.prepareStatement("SELECT COUNT(*) FROM Tamagotchi WHERE id = 1");
        ResultSet rs = stmtVerif.executeQuery();
        int count = rs.getInt(1);

        if (count == 0) {

            PreparedStatement stmtInsertTamagotchi = conn.prepareStatement("INSERT INTO Tamagotchi (id, primera_vez, nombre_mascota, comida, salud, higiene, descanso, dinero, volumen, fecha, manchado, caca, equipado) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            stmtInsertTamagotchi.setInt(1, 1);
            stmtInsertTamagotchi.setInt(2, 1);
            stmtInsertTamagotchi.setNull(3, Types.VARCHAR);
            stmtInsertTamagotchi.setInt(4, 100);
            stmtInsertTamagotchi.setInt(5, 100);
            stmtInsertTamagotchi.setInt(6, 100);
            stmtInsertTamagotchi.setInt(7, 100);
            stmtInsertTamagotchi.setInt(8, 800);
            stmtInsertTamagotchi.setInt(9, 1);
            stmtInsertTamagotchi.setTimestamp(10, Timestamp.valueOf(LocalDateTime.now()));
            stmtInsertTamagotchi.setBoolean(11, false);
            stmtInsertTamagotchi.setBoolean(12, false);
            stmtInsertTamagotchi.setString(13, "");
            stmtInsertTamagotchi.executeUpdate();
            stmtInsertTamagotchi.close();
            
            PreparedStatement stmtInsertComida = conn.prepareStatement("INSERT INTO Comida (mascota_id, agua, pancakes, tostadas, burguer, croissant, huevos, pizza) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
            stmtInsertComida.setInt(1, 1);
            stmtInsertComida.setInt(2, 0);
            stmtInsertComida.setInt(3, 0);
            stmtInsertComida.setInt(4, 0);
            stmtInsertComida.setInt(5, 0);
            stmtInsertComida.setInt(6, 0);
            stmtInsertComida.setInt(7, 0);
            stmtInsertComida.setInt(8, 0);
            stmtInsertComida.executeUpdate();
            stmtInsertComida.close();

            PreparedStatement stmtInsertInventario = conn.prepareStatement("INSERT INTO Inventario (mascota_id, bolsas, jabon, cama, cadena) VALUES (?, ?, ?, ?, ?)");
            stmtInsertInventario.setInt(1, 1);
            stmtInsertInventario.setInt(2, 0);
            stmtInsertInventario.setInt(3, 0);
            stmtInsertInventario.setBoolean(4, false);
            stmtInsertInventario.setBoolean(5, false);
            stmtInsertInventario.executeUpdate();
            stmtInsertInventario.close();

            PreparedStatement stmtInsertNutricion = conn.prepareStatement("INSERT INTO Nutricion (mascota_id, carbohidratos, proteinas, hidratado) VALUES (?, ?, ?, ?)");
            stmtInsertNutricion.setInt(1, 1);
            stmtInsertNutricion.setInt(2, 100);
            stmtInsertNutricion.setInt(3, 100);
            stmtInsertNutricion.setInt(4, 100);
            stmtInsertNutricion.executeUpdate();
            stmtInsertNutricion.close();

            PreparedStatement stmtInsertRopa = conn.prepareStatement("INSERT INTO Ropa (mascota_id, abrigo, sudadera, banador) VALUES (?, ?, ?, ?)");
            stmtInsertRopa.setInt(1, 1);
            stmtInsertRopa.setBoolean(2, false);
            stmtInsertRopa.setBoolean(3, false);
            stmtInsertRopa.setBoolean(4, false);
            stmtInsertRopa.executeUpdate();
            stmtInsertRopa.close();

            System.out.println("Datos de la mascota insertados correctamente");
        } else {
            System.out.println("Ya existe una mascota en la base de datos con id=1");
        }
        stmtVerif.close();
        rs.close();
    } catch (SQLException e) {
        System.err.println("Error al insertar los datos de la mascota: " + e.getMessage());
    }

        
      File audioFile = new File("src\\music\\cancion.wav");
        
        
        try {
            clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(audioFile));
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            
        } catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
            e.printStackTrace();
        }
       
       Connection conn = DriverManager.getConnection(DB_URL);
       imprimirBaseDeDatos(conn);
       comprobarNombre();
       actualizarVolumenDesdeBD();
       
       jLabel14.setVisible(false);
       jLabel15.setVisible(false);
       jLabel16.setVisible(false);
       
       String[] coordinates = getCoordinates();
       if(isRaining(coordinates[0], coordinates[1])==true)
       {    
           Random random = new Random();
            if (random.nextDouble() < 0.5 && !obtenerEstadoManchado()) {
                try {
                    Statement stmt = conn.createStatement();
                    String sql = "UPDATE Tamagotchi SET manchado = 1 WHERE id = 1";
                    stmt.executeUpdate(sql);
                    stmt.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
       }
       
       restarVidaAlVisitar();
       
       Timer timer = new Timer(1000, e -> {
            jLabel11.setText(obtenerDinero());
        });
        timer.start();
        
        comprobarDieta();
        Timer timer2 = new Timer(86400000, e -> {
            comprobarDieta();
        });
        timer2.start();

        Timer timer3 = new Timer(10000, e -> {
            checkManchado();
            gestionarEstado(obtenerTemperatura(coordinates[0], coordinates[1]));
        });
        timer3.start();
    }catch(Exception oo){oo.printStackTrace();}
    }
    
    
    private void gestionarEstado(double valor) {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
                PreparedStatement stmtSelect = conn.prepareStatement("SELECT equipado FROM Tamagotchi WHERE id = 1");
                ResultSet rs = stmtSelect.executeQuery();

                if (rs.next()) {
                    String equipado = rs.getString("equipado");

                    if (valor < 10) {
                        if (!"abrigo".equals(equipado)) {
                            stmtSelect.close();
                            rs.close();

                            PreparedStatement stmtUpdateSalud = conn.prepareStatement("UPDATE Tamagotchi SET salud = salud - 5 WHERE id = 1");
                            stmtUpdateSalud.executeUpdate();
                            stmtUpdateSalud.close();
                        }
                    } else if (valor >= 10 && valor <= 30) {
                        if (!"sudadera".equals(equipado)) {
                            stmtSelect.close();
                            rs.close();

                            PreparedStatement stmtUpdateSalud = conn.prepareStatement("UPDATE Tamagotchi SET salud = salud - 5 WHERE id = 1");
                            stmtUpdateSalud.executeUpdate();
                            stmtUpdateSalud.close();
                        }
                    } else if (valor > 30) {
                        if (!"banador".equals(equipado)) {
                            stmtSelect.close();
                            rs.close();

                            PreparedStatement stmtUpdateSalud = conn.prepareStatement("UPDATE Tamagotchi SET salud = salud - 5 WHERE id = 1");
                            stmtUpdateSalud.executeUpdate();
                            stmtUpdateSalud.close();
                        }
                    }
                }
                
                stmtSelect.close();
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
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
    
    public boolean obtenerEstadoManchado() {
        boolean manchado = false;
        try {
            Connection conexion = DriverManager.getConnection(DB_URL);

            String sql = "SELECT manchado FROM Tamagotchi WHERE id = 1";
            PreparedStatement statement = conexion.prepareStatement(sql);
            ResultSet resultado = statement.executeQuery();

            if (resultado.next()) {
                manchado = resultado.getBoolean("manchado");
            }

            resultado.close();
            statement.close();
            conexion.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return manchado;
    }

    public void aumentarDescanso() {
        if(contador<6 && descansando==true){
            System.out.println("Entra " + descansando);
            jLabel16.setVisible(true);
            System.out.println("Descansando");
            System.out.println(contador);
            contador++;
            System.out.println(contador);
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            String sql = "SELECT t.descanso, i.cama FROM Tamagotchi t, Inventario i WHERE id = 1";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            LocalTime horaActual = LocalTime.now();
            if (rs.next()) {
                int descanso = rs.getInt("descanso");
                boolean tieneCama = rs.getBoolean("cama");

                if (descanso < 100) {
                  if(tieneCama)
                  {if (horaActual.isAfter(LocalTime.of(0, 0)) && horaActual.isBefore(LocalTime.of(8, 0))) {
                        
                            descanso += 4;
                      
                    }else{
                            descanso += 2;
                  }
                  }else{
                     if (horaActual.isAfter(LocalTime.of(0, 0)) && horaActual.isBefore(LocalTime.of(8, 0))) {
                        
                            descanso += 2;
                      
                    }else{
                            descanso += 1;
                  } 
                  }
                    String updateSql = "UPDATE Tamagotchi SET descanso = ? WHERE id = 1";
                    PreparedStatement updateStmt = conn.prepareStatement(updateSql);
                    updateStmt.setInt(1, descanso);
                    updateStmt.executeUpdate();
                    updateStmt.close();
                }
            }

            rs.close();
            pstmt.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
        else{System.out.println("no entra " + descansando);
            contador=0;
        descansando=false;
        jLabel16.setVisible(false);}
    }
    
    public void comprobarDieta() {
            LocalDate fechaActual = LocalDate.now();
            try (Connection conn = DriverManager.getConnection(DB_URL);
                 PreparedStatement stmt = conn.prepareStatement("SELECT t.fecha, n.carbohidratos, n.proteinas, n.hidratado, t.salud FROM Tamagotchi t, Nutricion n WHERE id = 1")) {
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        LocalDate fechaGuardada = rs.getDate("fecha").toLocalDate();
                        if (!fechaActual.equals(fechaGuardada)) {
                            try (PreparedStatement stmtUpdate = conn.prepareStatement("UPDATE Tamagotchi SET fecha = ? WHERE id = 1")) {
                                stmtUpdate.setDate(1, Date.valueOf(fechaActual));
                                stmtUpdate.executeUpdate();
                            }
                            int carbohidratos = rs.getInt("carbohidratos");
                            int proteinas = rs.getInt("proteinas");
                            int hidratado = rs.getInt("hidratado");
                            int salud = rs.getInt("salud");

                            double media = (carbohidratos + proteinas + hidratado) / 3.0;
                            if (carbohidratos > media * 2 || proteinas > media * 2 || hidratado > media * 2) {
                                try (PreparedStatement stmtUpdateSalud = conn.prepareStatement("UPDATE Tamagotchi SET salud = ? WHERE id = 1")) {
                                    stmtUpdateSalud.setInt(1, salud - 5);
                                    stmtUpdateSalud.executeUpdate();
                                    System.out.println("Se ha restado 5 puntos de salud.");
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    public void checkManchado() {
            //System.out.println("checkManchado.panelPrincipal");
        try {
                Connection conn = DriverManager.getConnection(DB_URL);
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT manchado, caca FROM Tamagotchi WHERE id = 1");

                boolean manchado = rs.getBoolean("manchado");
                if (manchado) {
                    jLabel14.setVisible(true);
                } else {
                    jLabel14.setVisible(false);
                }
                boolean caca = rs.getBoolean("caca");
                if (caca) {
                    jLabel15.setVisible(true);
                } else {
                    jLabel15.setVisible(false);
                }
                
                rs.close();
                stmt.close();
                
                double probabilidad = Math.random();
                
                    try {
                        Statement stmt2 = conn.createStatement();
                        ResultSet rs2 = stmt2.executeQuery("SELECT caca FROM Tamagotchi WHERE id = 1");
                        boolean caca2 = rs2.getBoolean("caca");
                    if (!caca2) { 
                        if (probabilidad <= 0.05) {
                            try {
                                Statement stmt3 = conn.createStatement();
                                String sql = "UPDATE Tamagotchi SET caca = 1 WHERE id = 1";
                                stmt3.executeUpdate(sql);
                                stmt3.close();
                            } catch (SQLException ex) {
                                ex.printStackTrace();
                            }
                        }
                    }

                    rs.close();
                    rs2.close();
                    stmt.close();
                    stmt2.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }   
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
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
         
    public static boolean isRaining(String latitude, String longitude) {
        try {
            String urlString = "https://api.openweathermap.org/data/2.5/weather?lat=" +
                    latitude + "&lon=" + longitude + "&appid=" + API_KEY;

            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            boolean isRaining = response.toString().contains("\"rain\":");

            return isRaining;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public void restarVidaAlVisitar() {
        //System.out.println("restarVidaAlVisitar.panelPrincipal");
        LocalDate fechaActual = LocalDate.now();

    try (Connection conn = DriverManager.getConnection(DB_URL)) {
        String sqlSelect = "SELECT fecha FROM Tamagotchi WHERE id = 1";
        try (PreparedStatement statementSelect = conn.prepareStatement(sqlSelect);
             ResultSet resultSet = statementSelect.executeQuery()) {

            if (resultSet.next()) {
                long fechaAlmacenadaMillis = resultSet.getLong("fecha");
                LocalDate fechaAlmacenada = LocalDate.ofEpochDay(fechaAlmacenadaMillis / 86400000);

                long diferenciaEnDias = ChronoUnit.DAYS.between(fechaAlmacenada, fechaActual);

                if (diferenciaEnDias > 0) {
                    int saludARestar = (int) (diferenciaEnDias * 5);

                    String sqlUpdateSalud = "UPDATE Tamagotchi SET salud = salud - ? WHERE id = 1";
                    try (PreparedStatement statementSalud = conn.prepareStatement(sqlUpdateSalud)) {
                        statementSalud.setInt(1, saludARestar);
                        statementSalud.executeUpdate();
                    }

                    long fechaActualMillis = fechaActual.toEpochDay() * 86400000;
                    String sqlUpdateFecha = "UPDATE Tamagotchi SET fecha = ? WHERE id = 1";
                    try (PreparedStatement statementFecha = conn.prepareStatement(sqlUpdateFecha)) {
                        statementFecha.setLong(1, fechaActualMillis);
                        statementFecha.executeUpdate();
                    }
                }
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

    public String obtenerDinero() {
    String dinero = "";
    //System.out.println("obtenerDinero.panelPrincipal");
    try (Connection conn = DriverManager.getConnection(DB_URL)) {
        String sql = "SELECT dinero FROM Tamagotchi WHERE id = 1";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();

        if (rs.next()) {
            dinero = rs.getString("dinero");
        }
        conn.close();
        pstmt.close();
        rs.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }
    
    return dinero;
    }

    private static void createDatabase() {
            LocalDate fechaActual = LocalDate.now();
            try (Connection conn = DriverManager.getConnection(DB_URL);
                Statement stmt = conn.createStatement()) {
                
                stmt.execute("CREATE TABLE Tamagotchi (id INTEGER PRIMARY KEY AUTOINCREMENT, primera_vez INTEGER DEFAULT 1, nombre_mascota TEXT NULL, "
                        + "comida INTEGER DEFAULT 100 CHECK (comida >= 0 AND comida <= 100), salud INTEGER DEFAULT 100 CHECK (salud >= 0 AND salud <= 100), "
                        + "higiene INTEGER DEFAULT 100 CHECK (higiene >= 0 AND higiene <= 100), descanso INTEGER DEFAULT 100 CHECK (descanso >= 0 AND descanso <= 100), "
                        + "dinero INTEGER DEFAULT 800 CHECK (dinero >= 0 AND dinero <= 999), volumen INTEGER DEFAULT 1 CHECK (volumen >= 0 AND volumen <= 100), fecha DATE NOT NULL DEFAULT '" + fechaActual + "', "
                        + "manchado BOOLEAN DEFAULT 0, caca BOOLEAN DEFAULT 0, "
                        + "equipado TEXT NULL)"
                );

                stmt.execute("CREATE TABLE Comida (mascota_id INTEGER, "
                        + "agua INTEGER DEFAULT 0 CHECK (agua >= 0), pancakes INTEGER DEFAULT 0 CHECK (pancakes >= 0), "
                        + "tostadas INTEGER DEFAULT 0 CHECK (tostadas >= 0), burguer INTEGER DEFAULT 0 CHECK (burguer >= 0), "
                        + "croissant INTEGER DEFAULT 0 CHECK (croissant >= 0), huevos INTEGER DEFAULT 0 CHECK (huevos >= 0), "
                        + "pizza INTEGER DEFAULT 0 CHECK (pizza >= 0), FOREIGN KEY (mascota_id) REFERENCES Tamagotchi(id))"
                );

                stmt.execute("CREATE TABLE Inventario (mascota_id INTEGER, "
                        + "bolsas INTEGER DEFAULT 0 CHECK (bolsas >= 0), jabon INTEGER DEFAULT 0 CHECK (jabon >= 0), "
                        + "cama BOOLEAN DEFAULT 0, cadena BOOLEAN DEFAULT 0, "
                        + "FOREIGN KEY (mascota_id) REFERENCES Tamagotchi(id))"
                );

                stmt.execute("CREATE TABLE Nutricion (mascota_id INTEGER, "
                        + "carbohidratos INTEGER DEFAULT 100 CHECK (carbohidratos >= 0), proteinas INTEGER DEFAULT 100 CHECK (proteinas >= 0),"
                        + "hidratado INTEGER DEFAULT 100 CHECK (hidratado >= 0), "
                        + "FOREIGN KEY (mascota_id) REFERENCES Tamagotchi(id))"
                );
                
                stmt.execute("CREATE TABLE Ropa (mascota_id INTEGER PRIMARY KEY, "
                        + "abrigo BOOLEAN DEFAULT 0, "
                        + "sudadera BOOLEAN DEFAULT 0, "
                        + "banador BOOLEAN DEFAULT 0, "
                        + "FOREIGN KEY (mascota_id) REFERENCES Tamagotchi(id))"
                );

                conn.close();
                stmt.close();
            } catch (SQLException e) {
                System.err.println("Error al crear la base de datos: " + e.getMessage());
            }
        }

    
    public void imprimirBaseDeDatos(Connection conn) {
            try {
                // Obtener los nombres de las tablas en la base de datos
                DatabaseMetaData metaData = conn.getMetaData();
                ResultSet tables = metaData.getTables(null, null, "%", null);

                while (tables.next()) {
                    String tableName = tables.getString("TABLE_NAME");
                    System.out.println("Tabla: " + tableName);

                    // Obtener los nombres de las columnas de la tabla
                    ResultSet columns = metaData.getColumns(null, null, tableName, null);
                    while (columns.next()) {
                        String columnName = columns.getString("COLUMN_NAME");
                        System.out.println("  - Columna: " + columnName);
                    }

                    // Obtener los registros de la tabla
                    Statement stmt = conn.createStatement();
                    ResultSet records = stmt.executeQuery("SELECT * FROM " + tableName);
                    while (records.next()) {
                        System.out.print("  - Registro: ");
                        for (int i = 1; i <= records.getMetaData().getColumnCount(); i++) {
                            String columnValue = records.getString(i);
                            System.out.print(columnValue + " ");
                        }
                        System.out.println();
                    }

                    System.out.println();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }


    private void comprobarNombre() {
        //System.out.println("comprobarNombre.panelPrincipal");
        try  {
            Connection conn = DriverManager.getConnection(DB_URL);
            String sql = "SELECT primera_vez FROM Tamagotchi WHERE id = 1";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            if (rs.getBoolean("primera_vez")) {
                    String nombre = JOptionPane.showInputDialog(null, "Hola, bienvenido!! Inserte el nombre que desee para su mascota:");
                    sql = "UPDATE Tamagotchi SET primera_vez = ? , nombre_mascota = ? WHERE id = 1";
                    pstmt = conn.prepareStatement(sql);
                    pstmt.setBoolean(1, false);
                    pstmt.setString(2, nombre);
                    pstmt.executeUpdate();
                    jLabel1.setText(nombre);
                }else
                    {
                        String nombreMascota = null;
                        try {
                            sql = "SELECT nombre_mascota FROM Tamagotchi WHERE id = 1";
                            pstmt = conn.prepareStatement(sql);
                            rs = pstmt.executeQuery();
                            if (rs.next()) {
                                nombreMascota = rs.getString("nombre_mascota");
                            }
                            jLabel1.setText(nombreMascota);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }conn.close();
                    pstmt.close();
                    rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void actualizarVolumenDesdeBD() {
        //System.out.println("actualizarVolumenDesdeBd.panelPrincipal");
        try {
        Connection conn = DriverManager.getConnection(DB_URL);
        String sql = "SELECT volumen FROM Tamagotchi WHERE id = 1";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        int volumen = 0;
        if (rs.next()) {
            volumen = rs.getInt("volumen");
        }
        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        float dB = (float) (Math.log(volumen / 100.0) / Math.log(10.0) * 20.0);
        gainControl.setValue(dB);
        conn.close();
        stmt.close();
        rs.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        jPanel4 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setLocationByPlatform(true);
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(204, 255, 204));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.setForeground(new java.awt.Color(204, 255, 204));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(204, 255, 204));

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel1.setText("Osito");

        jPanel3.setBackground(new java.awt.Color(204, 255, 204));
        jPanel3.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jPanel3MouseDragged(evt);
            }
        });
        jPanel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jPanel3MousePressed(evt);
            }
        });

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/help.png"))); // NOI18N
        jLabel4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel4MouseClicked(evt);
            }
        });

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/letra-x.png"))); // NOI18N
        jLabel3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel3MouseClicked(evt);
            }
        });

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/minimizar.png"))); // NOI18N
        jLabel2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel2MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGap(0, 4, Short.MAX_VALUE)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        jSeparator1.setForeground(new java.awt.Color(0, 0, 0));

        jSeparator2.setForeground(new java.awt.Color(0, 0, 0));

        jPanel4.setBackground(new java.awt.Color(204, 255, 204));
        jPanel4.setLayout(new java.awt.GridLayout(1, 5, 6, 0));

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/comida.png"))); // NOI18N
        jLabel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jLabel6.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel6MouseClicked(evt);
            }
        });
        jPanel4.add(jLabel6);

        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/jabon.png"))); // NOI18N
        jLabel7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jLabel7.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel7MouseClicked(evt);
            }
        });
        jPanel4.add(jLabel7);

        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/corazon.png"))); // NOI18N
        jLabel8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jLabel8.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel8MouseClicked(evt);
            }
        });
        jPanel4.add(jLabel8);

        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/mando.png"))); // NOI18N
        jLabel9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jLabel9.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel9MouseClicked(evt);
            }
        });
        jPanel4.add(jLabel9);

        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/zzz.png"))); // NOI18N
        jLabel10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jLabel10.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel10MouseClicked(evt);
            }
        });
        jPanel4.add(jLabel10);

        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("256");

        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/dollar (1).png"))); // NOI18N

        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/shop.png"))); // NOI18N
        jLabel13.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel13.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel13MouseClicked(evt);
            }
        });

        jPanel5.setBackground(new java.awt.Color(204, 255, 204));
        jPanel5.setLayout(null);

        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/suciedad.png"))); // NOI18N
        jPanel5.add(jLabel14);
        jLabel14.setBounds(170, 130, 40, 40);

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/husky.png"))); // NOI18N
        jLabel5.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel5MouseClicked(evt);
            }
        });
        jPanel5.add(jLabel5);
        jLabel5.setBounds(0, 6, 342, 244);

        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/caca.png"))); // NOI18N
        jPanel5.add(jLabel15);
        jLabel15.setBounds(50, 170, 50, 80);

        jLabel16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/zzz.png"))); // NOI18N
        jPanel5.add(jLabel16);
        jLabel16.setBounds(220, -4, 80, 50);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jSeparator1)
                    .addComponent(jSeparator2)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(8, 8, 8)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel12)))
                .addGap(2, 2, 2)
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 4, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(9, 9, 9))
        );

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 360, 460));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jPanel3MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel3MousePressed
        xMouse = evt.getX();
        yMouse = evt.getY();
    }//GEN-LAST:event_jPanel3MousePressed

    private void jPanel3MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel3MouseDragged
        int x = evt.getXOnScreen();
        int y = evt.getYOnScreen();
        this.setLocation(x - xMouse, y - yMouse);
    }//GEN-LAST:event_jPanel3MouseDragged

    private void jLabel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseClicked
        try {
            conn.close();
        } catch (SQLException ex) {
                ex.printStackTrace();        
        }
        System.exit(0);
    }//GEN-LAST:event_jLabel3MouseClicked

    private void jLabel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseClicked
        this.setState(JFrame.ICONIFIED);
    }//GEN-LAST:event_jLabel2MouseClicked

    private void jLabel4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseClicked
        try {
            AyudaVolumen a = new AyudaVolumen(clip);
            a.setVisible(true);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_jLabel4MouseClicked

    private void jLabel9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel9MouseClicked
        iniciarJuego();
        if(descansando==false){
            
                    try {
                Connection conn = DriverManager.getConnection(DB_URL);

                String query = "SELECT descanso FROM Tamagotchi WHERE id = 1";
                PreparedStatement statement = conn.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    int descanso = resultSet.getInt("descanso");
                    if (descanso >= 10) {
                        descanso -= 10;

                        String updateQuery = "UPDATE Tamagotchi SET descanso = ? WHERE id = 1";
                        PreparedStatement updateStatement = conn.prepareStatement(updateQuery);
                        updateStatement.setInt(1, descanso);
                        updateStatement.executeUpdate();

                        System.out.println("Se restaron 10 del campo 'descanso'.");
                        
                        iniciarJuego();
                    } else {
                        System.out.println("No hay suficiente en el campo 'descanso'.");
                    }
                }

                resultSet.close();
                statement.close();
                conn.close();
                
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
            
            
        
        
        
    }//GEN-LAST:event_jLabel9MouseClicked

    private void iniciarJuego() {
        Juego juego = new Juego();
        Thread juegoThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    juego.actualizar();
                    juego.repaint();
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        juegoThread.start();
    }
    
    private void jLabel13MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel13MouseClicked
        
        if(descansando==false){
        Tienda t = new Tienda();}
    }//GEN-LAST:event_jLabel13MouseClicked

    private void jLabel6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel6MouseClicked
        if(descansando==false){
        try {
            Comida c = new Comida();
            c.setVisible(true);
        } catch (SQLException ex) {ex.printStackTrace();
        }}
    }//GEN-LAST:event_jLabel6MouseClicked

    private void jLabel7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel7MouseClicked
        if(descansando==false){
        try {
            Higiene g = new Higiene();
            g.setVisible(true);
        } catch (SQLException ex) {
            Logger.getLogger(PanelPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }}
    }//GEN-LAST:event_jLabel7MouseClicked

    private void jLabel10MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel10MouseClicked
        
            descansando=true;
            //jLabel16.setVisible(true);
            aumentarDescanso();
            descansando=true;
            contador=0;
            Timer timer;
            timer = new Timer(60000, e -> {
                aumentarDescanso();
            });
            timer.start();
        
    }//GEN-LAST:event_jLabel10MouseClicked

    private void jLabel8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel8MouseClicked
        
        Salud r = new Salud(); r.setVisible(true);
        
    }//GEN-LAST:event_jLabel8MouseClicked

    private void jLabel5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel5MouseClicked
        
        Ropa r = new Ropa(); r.setVisible(true);
        
    }//GEN-LAST:event_jLabel5MouseClicked

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
            java.util.logging.Logger.getLogger(PanelPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PanelPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PanelPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PanelPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new PanelPrincipal().setVisible(true);
                } catch (SQLException ex) {
                    System.out.println("Error sql");
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
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    // End of variables declaration//GEN-END:variables
 
}
