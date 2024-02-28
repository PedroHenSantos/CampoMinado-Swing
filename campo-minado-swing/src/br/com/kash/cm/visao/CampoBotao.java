package br.com.kash.cm.visao;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.SwingUtilities;

import br.com.kash.cm.modelo.Campo;
import br.com.kash.cm.modelo.CampoEvento;
import br.com.kash.cm.modelo.CampoObservador;

@SuppressWarnings("serial")
public class CampoBotao extends JButton 
	implements CampoObservador, MouseListener {

	private final Color BG_PADRAO = new Color(184, 184, 184);
	private final Color BG_MARCADO = new Color(8, 179, 247);
	private final Color BG_EXPLODIR = new Color(189, 66, 68);
	private final Color TEXTO_VERDE = new Color(0, 190, 0);
	
	private Campo campo;
	
	public CampoBotao(Campo campo) {
		this.campo = campo;
		setBackground(BG_PADRAO);
		setOpaque(true);
		setBorder(BorderFactory.createBevelBorder(0));
		
		
		addMouseListener(this);
		campo.registrarObservador(this);
	}
	
	@Override
	public void acaoRealizada(Campo campo, CampoEvento evento) {
		switch(evento) {
		case ABRIR:
			aplicarEstiloAbrir();
			break;
		case MARCAR:
			aplicarEstiloMarcar();
			break;
		case DESMARCAR:
			aplicarEstiloPadrao();
			break;
		case EXPLODIR:
			aplicarEstiloExplodir();
			break;
		default:
			aplicarEstiloPadrao();
		}
		
		SwingUtilities.invokeLater(() -> {
			repaint();
			validate();
			
		});
	}

	private void aplicarEstiloPadrao() {
		setBackground(BG_PADRAO);
		setBorder(BorderFactory.createBevelBorder(0));
		setText("");
	}

	private void aplicarEstiloExplodir() {
		setBackground(BG_EXPLODIR);
		setForeground(Color.white);
		setText("X");
	}

	private void aplicarEstiloMarcar() {
		setBackground(BG_MARCADO);
		setForeground(Color.black);
		setText("M");
	}
	

	private void aplicarEstiloAbrir() {
		
		setBorder(BorderFactory.createLineBorder(Color.GRAY));
		if(campo.isMinado()) {
			setBackground(BG_EXPLODIR);
			setText("*");
			return;
		}
		
		setBackground(BG_PADRAO);
		
		switch (campo.minasEmVolta()) {
		case 1:
			setForeground(TEXTO_VERDE);
			break;
		case 2:
			setForeground(Color.CYAN);
			break;
		case 3:
			setForeground(Color.YELLOW);
		case 4:
			setForeground(Color.RED);
			break;
		default:
			
		}
		
		String valor = !campo.vizinhancaSegura() ?
				campo.minasEmVolta() + "" : "";
		setText(valor);
	}
	
	//Interface açăo do mouse
	@Override
	public void mousePressed(MouseEvent e) {
		if(e.getButton() == 1) {
			campo.abrir();
		}else {
			campo.alternarMarcacao();
		}
	}

	
	// Setar outras funcoes de click mesmo sem utiliza-las pois irá dar erro
	public void mouseClicked(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
}
