package model;

public class ReturnCommand implements Command {
	
	private Book book;
    private Member membre;

    public ReturnCommand(Book book, Member member) {
        this.book = book;
        this.membre = membre;
    }

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void undo() {
		// TODO Auto-generated method stub
		
	}

}
