package br.unirio;

public class Main
{
	public static void main(String args[])
	{
		/*for(long i = 0; i < 999999999; i++)
		{
			long start = System.currentTimeMillis();*/
			OrdenacaoTopologica ord = new OrdenacaoTopologica();
			
			String nomeEntrada = "C:\\Users\\T-Gamer\\Documents\\Projetos\\Programming Projects\\Eclipse Projects\\Unirio Projects\\ED1\\TrabalhoFinalEd1\\src\\br\\unirio\\entrada.txt";
			
			ord.realizaLeitura(nomeEntrada);
			
			if(!ord.executa())
				System.out.println("O conjunto nao � parcialmente ordenado.");
			else
				System.out.println("O conjunto � parcialmente ordenado.");
			/*long delay = System.currentTimeMillis() - start;
			System.out.println("Demorou " + delay + " milissegundos");
		}*/
	}
}
