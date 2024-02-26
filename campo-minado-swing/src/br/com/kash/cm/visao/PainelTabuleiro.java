package br.com.kash.cm.visao;

import java.awt.GridLayout;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import br.com.kash.cm.modelo.Tabuleiro;

@SuppressWarnings("serial")
public class PainelTabuleiro extends JPanel{

	public PainelTabuleiro(Tabuleiro tabuleiro) {
		setLayout(new GridLayout
				(tabuleiro.getLinhas(), tabuleiro.getColunas()));
		
		tabuleiro.paraCadaCampo(c -> add(new CampoBotao(c)));
		tabuleiro.registrarObservadores(e -> {
			
			SwingUtilities.invokeLater(() -> {
				
				if(e.isGanhou()) {
					JOptionPane.showMessageDialog(this, "YOU WIN!!");
				} else {
					JOptionPane.showMessageDialog(this,  "YOU LOSE!!");
				}
				
				tabuleiro.reiniciar();
			});
		});
 	}
}
