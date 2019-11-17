package br.unirio;

public class Main
{
	public static void main(String args[])
	{
		OrdenacaoTopologica ord = new OrdenacaoTopologica();
		
		String nomeEntrada = "C:\\Users\\T-Gamer\\Documents\\Projetos\\Programming Projects\\Eclipse Projects\\Unirio Projects\\ED1\\TrabalhoFinalEd1\\src\\br\\unirio\\entrada.txt";
		
		/*O arquivo novo foi criado para mostrar o erro no pdf do professor quanto ao número 8 e como ele impacta
		 * nos sucessores e predecesores da linha dele se no documento de texto não tiver um '4 < 8'*/
		//String nomeEntradaCom4_8 = "C:\\Users\\T-Gamer\\Documents\\Projetos\\Programming Projects\\Eclipse Projects\\Unirio Projects\\ED1\\TrabalhoFinalEd1\\src\\br\\unirio\\entradaCom4_8.txt";
		
		ord.realizaLeitura(nomeEntrada);
		
		//ord.realizaLeitura(nomeEntradaCom4_8);
		
		System.out.println();
		System.out.println();
		
		/*
		if(!ord.executa())
			System.out.println("O conjunto nao é parcialmente ordenado.");
		else
			System.out.println("O conjunto é parcialmente ordenado.");
		*/
	}
}
