package main;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

public class Dino {
	
	private int x, y;
	private int ancho, alto;
	private Image imagen;
	private boolean saltando;
	private int limiteSalto;
	private int velocidad = 10;
	
	public Dino(int x, int y, int ancho, int alto) {
		this.x = x;
		this.y = y;
		this.ancho = ancho;
		this.alto = alto;
		this.limiteSalto = y - 200;
		this.imagen = new ImageIcon("src/data/Dino corriendo 1.png").getImage();
	}
	
	public void actualizar(Rectangle suelo, int n) {
		if(n % 2 == 0) {
			imagen = new ImageIcon("src/data/Dino corriendo 1.png").getImage();
		} else {
			imagen = new ImageIcon("src/data/Dino corriendo 2.png").getImage();
		}
		
		if(!estaEnElSuelo(suelo)) {
			imagen = new ImageIcon("src/data/Dino saltando.png").getImage();
		}
	}
	
	public void dibujar(Graphics g) {
		g.drawImage(imagen, x, y, ancho, alto, null);
	}
	
	public void saltar() {
		if(saltando && y > limiteSalto) {
			this.y -= velocidad;
		} else {
			saltando = false;
		}
	}
	
	public void caer(Rectangle suelo) {
		if(!estaEnElSuelo(suelo) && !saltando || y < limiteSalto) {
			this.y += velocidad;
		}
	}
	
	public boolean estaEnElSuelo(Rectangle suelo) {
		if(this.y + alto/2 >= suelo.y - suelo.height/2) {
			return true;
		}
		return false;
	}
	
	public boolean choco(Obstaculo obstaculo) {
		if(x + ancho - 20 >= obstaculo.getX() && x + ancho - 20 <= obstaculo.getX() + obstaculo.getAncho() &&
				y + alto/3 >= obstaculo.getY() + 10 && y + alto/3 <= obstaculo.getY() + obstaculo.getAlto() ||
				x + ancho - 20 >= obstaculo.getX() + 30 && x + ancho - 20 <= obstaculo.getX() + obstaculo.getX() &&
				y + alto - 20 >= obstaculo.getY() && y + alto - 20 <= obstaculo.getY() + obstaculo.getAlto()) {
			return true;
		}
		return false;
	}
	
	public void perder() {
		imagen = new ImageIcon("src/data/Dino chocado.png").getImage();
	}

	public void setSaltando(boolean saltando) {
		this.saltando = saltando;
	}

	public boolean getSaltando() {
		return saltando;
	}

	public Image getImagen() {
		return imagen;
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

	public int getVelocidad() {
		return velocidad;
	}

	public void setVelocidad(int velocidad) {
		this.velocidad = velocidad;
	}

}
