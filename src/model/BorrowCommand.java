package model;

public class BorrowCommand implements Command {

	private Book book;
    private Member member;
    
    public BorrowCommand(Book book, Member member) {
    	this.book = book;
    	this.member = member;
    }
    
	@Override
	public void execute() {

		System.out.println("Borrow of the book " + book.getTitre() + " para o membro " + member.getPrenom() + ".");
		
		//LÓGICA PARA CRIAR O EMPRÉSTIMO NO BANCO DE DADOS
	}

	@Override
	public void undo() {
		
		System.out.println("Undo of the borrow related to the book " + book.getTitre() + " borrowed by member " + member.getNom() + "'.");
		
		//LÓGICA PARA DESFAZER O EMPRÉSTIMO NO BANCO DE DADOS
	}

}
