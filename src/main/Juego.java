package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.sql.*;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Juego extends JPanel {

	private JFrame ventana;
	protected final static int ANCHO = 1280;
	protected final static int ALTO = 600;
	protected static int velocidadCam;

	private boolean juegoTerminado;

	private int puntaje;
	private Integer record;
	private Dino dino;
	private Mapa mapa;
	private Image msjReiniciar, cartel;
	private String sonidoSalto, sonidoDerrota;
        
        private static final String DB_URL = "jdbc:sqlite:./sqlite-tools-win32-x86-3410200/sqlite-tools-win32-x86-3410200/tamagotchi.db";

	public Juego() {
		iniciarVentana();
		inicializarVariables();
	}

	private void iniciarVentana() {
		ventana = new JFrame("The Dog Game");
                
		ventana.setSize(ANCHO, ALTO);
		ventana.setLocationRelativeTo(null);
		ventana.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		ventana.setVisible(true);
		ventana.setResizable(false);

		ventana.add(this);
		KeyAdapter teclas;
            teclas = new KeyAdapter() {
                
                @Override
                public void keyPressed(KeyEvent e) {
                    if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                        
                        ventana.dispose();
                    } else if(e.getKeyCode() == KeyEvent.VK_SPACE) {
                        if(dino.estaEnElSuelo(mapa.getSuelo())) {
                            reproducirSonido(sonidoSalto);
                            dino.setSaltando(true);
                        }
                        if(juegoTerminado) {
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
                                    } else {
                                        System.out.println("No hay suficiente en el campo 'descanso'.");
                                        ventana.dispose();
                                    }
                                }
                                
                                resultSet.close();
                                statement.close();
                                conn.close();
                            } catch (SQLException ez) {
                                ez.printStackTrace();
                            }
                            inicializarVariables();
                        }
                    }
                }
                
            };
		ventana.addKeyListener(teclas);
	}

	private void inicializarVariables() {
		velocidadCam = 9;
		juegoTerminado = false;
		puntaje = 0;
		sonidoSalto = "src/data/sonido de salto.wav";
		sonidoDerrota = "src/data/sonido de derrota.wav";
		msjReiniciar = new ImageIcon("src/data/icono de reiniciar.png").getImage();
		cartel = new ImageIcon("src/data/cartel de game over.png").getImage();
		mapa = new Mapa(0, 0, ANCHO, ALTO);
		dino = new Dino(ANCHO - 1180, 400, 100, 100);
	}

	public void actualizar() {
		this.repaint();

		if(!juegoTerminado) {
			puntaje++;
			mapa.actualizar(puntaje);
			dino.actualizar(mapa.getSuelo(), puntaje / 10);
			dino.saltar();
			dino.caer(mapa.getSuelo());

			// Simular el aumento de la velocidad del dino
			if(puntaje % 500 == 0 && puntaje/3 > 0) {
				Juego.velocidadCam++;
			}

			// Parar el juego si el dino chocó
			for(int i = 0; i < mapa.getObstaculos().size(); i++) {
				if(dino.choco(mapa.getObstaculos().get(i))) {
					reproducirSonido(sonidoDerrota);
					dino.perder();
					juegoTerminado = true;
                                        System.out.println(getPuntaje());
                                        agregarDinero(getPuntaje()/30);
				}
			}
		} else {
			// Actualizar record
			if(record == null || puntaje / 3 >= record) {
				record = puntaje / 3;
			}
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if(mapa != null)
			mapa.dibujar(g);
		if(dino != null)
			dino.dibujar(g);
		if(juegoTerminado)
			mensajeGameOver(g); 
		mostrarPuntaje(g);
	}

	private void mostrarPuntaje(Graphics g) {
		g.setColor(Color.WHITE);
		g.setFont(new Font("Pixel Emulator", 0, 20));
		g.drawString("Puntaje: " + puntaje / 3, ANCHO - 220, 25);
		if(record != null) {
			g.setColor(Color.BLACK);
			g.drawString("Record: " + record, ANCHO - 420, 25);
		}
	}

	private void mensajeGameOver(Graphics g) {
		g.drawImage(cartel, ANCHO / 4 - 75, ALTO / 4, ANCHO - 465, 275, null);
		g.setColor(Color.BLACK);
		g.setFont(new Font("Pixel Emulator", 0, 25));
		g.drawString("Fin del juego", ANCHO / 2 - 120, ALTO / 3);
		g.setFont(new Font("Pixel Emulator", 0, 20));
		g.drawString("Si desea salir del juego presione la tecla ESC", ANCHO / 4 - 20, ALTO / 3 + 50);
		g.drawString("Y si desea jugar de nuevo presione la tecla ESPACIO", ANCHO / 4 - 55, ALTO / 3 + 75);
		g.drawImage(msjReiniciar, ANCHO / 2 - 50, ALTO / 2, 100, 100, null);
	}

	private void reproducirSonido(String nombreSonido) {
		try {
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(nombreSonido).getAbsoluteFile());
			Clip clip = AudioSystem.getClip();
			clip.open(audioInputStream);
			clip.start();
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
			System.out.println("Error al reproducir el sonido");
		}
	}

	public static void main(String[] args) {
		Juego juego = new Juego();

		while(true) {
			juego.actualizar();
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
        
    public int getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(int puntaje) {
        this.puntaje = puntaje;
    }
    
    public void agregarDinero(int cantidad) {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            String queryTamagotchi = "SELECT dinero FROM Tamagotchi WHERE id = 1";
            String queryInventario = "SELECT cadena FROM Inventario WHERE mascota_id = 1";

            PreparedStatement statementTamagotchi = conn.prepareStatement(queryTamagotchi);
            PreparedStatement statementInventario = conn.prepareStatement(queryInventario);

            ResultSet resultSetTamagotchi = statementTamagotchi.executeQuery();
            ResultSet resultSetInventario = statementInventario.executeQuery();

            int dineroActual = 0;
            boolean cadenaActiva = false;

            if (resultSetTamagotchi.next()) {
                dineroActual = resultSetTamagotchi.getInt("dinero");
            }

            if (resultSetInventario.next()) {
                cadenaActiva = resultSetInventario.getBoolean("cadena");
            }

            resultSetTamagotchi.close();
            resultSetInventario.close();
            statementTamagotchi.close();
            statementInventario.close();

            int suma = dineroActual + (cadenaActiva ? cantidad * 2 : cantidad);
            int nuevoDinero = Math.min(suma, 999);

            String updateQuery = "UPDATE Tamagotchi SET dinero = ? WHERE id = 1";
            PreparedStatement updateStatement = conn.prepareStatement(updateQuery);
            updateStatement.setInt(1, nuevoDinero);
            updateStatement.executeUpdate();

            System.out.println("Se ha agregado " + cantidad + " al campo 'dinero'. Nuevo valor: " + nuevoDinero);

            updateStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}


