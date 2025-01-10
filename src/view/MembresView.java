package view;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;

public class MembresView extends JPanel {

	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */
	public MembresView() {
		setLayout(null);
		
		JLabel lblGestionDesMembres = new JLabel("Gestion des Membres");
		lblGestionDesMembres.setHorizontalAlignment(SwingConstants.CENTER);
		lblGestionDesMembres.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblGestionDesMembres.setBounds(278, 11, 182, 25);
		add(lblGestionDesMembres);

	}

}
