package model;

import controller.EmpruntController;

public class BorrowCommand implements Command {

	private EmpruntController empruntController;
    private Emprunt emprunt;

    public BorrowCommand(EmpruntController empruntController, Emprunt emprunt) {
        this.empruntController = empruntController;
        this.emprunt = emprunt;
    }
    
	@Override
	public void execute() {
		//LÓGICA PARA CRIAR O EMPRÉSTIMO NO BANCO DE DADOS
		empruntController.addEmprunt(emprunt);
		System.out.println("Emprunt effectué: " + emprunt);
	}

	@Override
	public void undo() {
		//LÓGICA PARA DESFAZER O EMPRÉSTIMO NO BANCO DE DADOS
		empruntController.deleteEmprunt(emprunt.getIdEmprunt());
		System.out.println("Emprunt annulé: " + emprunt);
	}

}
