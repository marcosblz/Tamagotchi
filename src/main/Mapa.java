package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

public class Mapa {
	
	private int x, y;
	private int ancho, alto;
	private Image fondo;
	private Image nube;
	private List<Integer> xNubes;
	private List<Obstaculo> obstaculos;
	private List<Rectangle> lineasSuelo;
	private Rectangle suelo;
	
	public Mapa(int x, int y, int ancho, int alto) {
		this.x = x;
		this.y = y;
		this.ancho = ancho;
		this.alto = alto;
		
		xNubes = new ArrayList<>();
		xNubes.add(100);
		xNubes.add(500);
		xNubes.add(800);
		xNubes.add(1100);
		
		fondo = new ImageIcon("src/data/fondo.png").getImage();
		nube = new ImageIcon("src/data/nube.png").getImage();
		
		lineasSuelo = new ArrayList<>();
		for(int i = 0; i < 1300; i += 73) {
			int random = (int)(Math.random() * 40 + 50);
			lineasSuelo.add(new Rectangle(i, alto - random, 10, 1));
		}
		suelo = new Rectangle(-1, alto - 100, ancho, alto - 500);
		
		obstaculos = new ArrayList<>();
		Obstaculo o1 = new Obstaculo(ancho, alto - 200, 60, 100);
		Obstaculo o2 = new Obstaculo(ancho + 100, alto - 150, 30, 50);
		o2.setTipo(2);
		obstaculos.add(o1);
		obstaculos.add(o2);
	}
	
	public void actualizar(int puntaje) {
		// Simular el movimiento del dino
		for(int i = 0; i < obstaculos.size(); i++) {
			obstaculos.get(i).mover();
		}
		// Mover nubes
		for(int i = 0; i < xNubes.size(); i++) {
			if(xNubes.get(i) + 200 > 0) {
				xNubes.set(i, xNubes.get(i) - Juego.velocidadCam);
			} else {
				xNubes.set(i, ancho);
			}
		}
		// Mover lineas del suelo
		for(int i = 0; i < lineasSuelo.size(); i++) {
			if(lineasSuelo.get(i).x > 0) {
				lineasSuelo.get(i).x -= Juego.velocidadCam;
			} else {
				lineasSuelo.get(i).x = ancho;
			}
		}
	}
	
	public void dibujar(Graphics g) {
		g.drawImage(fondo, x, y, ancho, alto, null);
		dibujarNubes(g);
		dibujarSuelo(g);
		for(int i = 0; i < obstaculos.size(); i++) {
			obstaculos.get(i).dibujar(g);
		}
	}
	
	private void dibujarSuelo(Graphics g) {
		g.setColor(Color.BLACK);
		for(int i = 0; i < lineasSuelo.size(); i++) {
			g.drawRect(lineasSuelo.get(i).x, lineasSuelo.get(i).y, lineasSuelo.get(i).width, lineasSuelo.get(i).height);
		}
		g.drawRect(suelo.x, suelo.y, suelo.width, 1);
	}
	
	private void dibujarNubes(Graphics g) {
		g.drawImage(nube, xNubes.get(0), alto - 450, 125, 50, null);
		g.drawImage(nube, xNubes.get(1), alto - 520, 80, 25, null);
		g.drawImage(nube, xNubes.get(2), alto - 400, 100, 40, null);
		g.drawImage(nube, xNubes.get(3), alto - 450, 80, 25, null);
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getAncho() {
		return ancho;
	}

	public int getAlto() {
		return alto;
	}

	public Image getImagen() {
		return fondo;
	}
	
	public Image getNube() {
		return nube;
	}

	public List<Obstaculo> getObstaculos() {
		return obstaculos;
	}

	public Rectangle getSuelo() {
		return suelo;
	}

}
