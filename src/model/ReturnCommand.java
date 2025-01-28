package model;

import controller.EmpruntController;

public class ReturnCommand implements Command {
	
	private EmpruntController empruntController;
    private int empruntId;

    public ReturnCommand(EmpruntController empruntController, int empruntId) {
        this.empruntController = empruntController;
        this.empruntId = empruntId;
    }

    @Override
    public void execute() {
        empruntController.markAsReturned(empruntId);
        System.out.println("Retour effectué pour l'emprunt ID: " + empruntId);
    }

    @Override
    public void undo() {
        empruntController.markAsNotReturned(empruntId);
        System.out.println("Retour annulé pour l'emprunt ID: " + empruntId);
    }

}
