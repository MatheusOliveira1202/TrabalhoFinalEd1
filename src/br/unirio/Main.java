package br.unirio;

public class Main
{
	public static void main(String args[])
	{
		OrdenacaoTopologica ord = new OrdenacaoTopologica();
		
		String nomeEntrada = "C:\\Users\\T-Gamer\\Documents\\Projetos\\Programming Projects\\Eclipse Projects\\Unirio Projects\\ED1\\TrabalhoFinalEd1\\src\\br\\unirio\\entrada.txt";
		
		ord.realizaLeitura(nomeEntrada);

		//ord.imprimeLista();
		
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
