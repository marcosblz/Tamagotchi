package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

public class Obstaculo {
	
	private int x, y;
	private int ancho, alto;
	private Image imagen, imagen2;
	private int tipo;
	
	public Obstaculo(int x, int y, int ancho, int alto) {
		this.x = x;
		this.y = y;
		this.ancho = ancho;
		this.alto = alto;
		this.tipo = 1;
		this.imagen = new ImageIcon("src/data/obstaculo.png").getImage();
		this.imagen2 = new ImageIcon("src/data/obstaculo2.png").getImage();
	}
	
	public void dibujar(Graphics g) {
		g.setColor(Color.RED);
		if(tipo == 1) {
			g.drawImage(imagen, x, y, ancho, alto, null);
		} else {
			g.drawImage(imagen2, x, y, ancho, alto, null);
		}
	}
	
	public void mover() {
		int random = (int)(Math.random() * 2);
		if(x + ancho > 0) {
			x -= Juego.velocidadCam;
		} else {
			if(tipo != 1 && random == 0) {
				x += Juego.ANCHO + 200;
			} else {
				x += Juego.ANCHO;
			}
		}
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
	
	public void setTipo(int tipo) {
		this.tipo = tipo;
	}
	
}
